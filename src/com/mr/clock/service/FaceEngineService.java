package com.mr.clock.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.ConfigurationException;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import com.mr.clock.session.Session;

public class FaceEngineService {//人脸识别引擎服务
    private static String appId = null;
    private static String sdkKey = null;
    private static FaceEngine faceEngine = null;//人脸识别引擎
    private static String ENGINE_PATH = "ArcFace/WIN64";// 算法库地址ַַ
    private static final String CONFIG_FILE = "src/com/mr/clock/config/ArcFace.properties";//配置文件地址
    static {
    	Properties pro = new Properties();// 配置文件解析类
    	File config = new File(CONFIG_FILE);// 配置文件的文件对象
    	try {
			if (!config.exists()) {// 如果配置文件不存在
				throw new FileNotFoundException("缺少文件："+ config.getAbsolutePath());
			}
			pro.load(new FileInputStream(config));// 加载配置文件
			appId = pro.getProperty("app_id");// 获取指定字段值
			sdkKey = pro.getProperty("sdk_key");
			if (appId == null || sdkKey == null) {// 如果配置文件中获取不到这俩字段
				throw new ConfigurationException("文件缺少配置信息");
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (ConfigurationException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	File path = new File(ENGINE_PATH);// 算法库文件夹
    	faceEngine = new FaceEngine(path.getAbsolutePath());// 人脸识别引擎
    	int errorCode = faceEngine.activeOnline(appId, sdkKey);// 激活引擎，首次激活需要联网
    	if (errorCode!=ErrorInfo.MOK.getValue()&&errorCode!=ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
			System.err.println("引擎激活失败");
		}
    	EngineConfiguration engineConfiguration = new EngineConfiguration();// 引擎配置
    	engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE); // 单张图像模式
    	engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT); // 检测所有角度
    	engineConfiguration.setDetectFaceMaxNum(1);// 检测最多人脸数
    	engineConfiguration.setDetectFaceScaleVal(16); // 设置人脸相对于所在图片的长边的占比
    	FunctionConfiguration functionConfiguration = new FunctionConfiguration();// 功能配置
    	functionConfiguration.setSupportFaceDetect(true);  // 支持人脸检测
    	functionConfiguration.setSupportFaceRecognition(true); // 支持人脸识别
    	engineConfiguration.setFunctionConfiguration(functionConfiguration); // 引擎使用此功能配置
    	errorCode = faceEngine.init(engineConfiguration); // 初始化引擎
    	if (errorCode!= ErrorInfo.MOK.getValue()) {
    		System.err.println("引擎初始化失败");
			
		}
    }
    public static FaceFeature getFaceFeature(BufferedImage img) {//获取一张人脸的面部特征
    	if (img == null) {
			throw new NullPointerException("无图像");
		}
    	BufferedImage face = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_BGR);  //创建一个和原图像一样大的临时图像，临时图像类型为普通BRG图像
    	 face.setData(img.getData());// 临时图像使用原图像中的数据
    		ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(face); // 采集图像信息
    		List<FaceInfo>faceInfoList = new ArrayList<FaceInfo>(); // 人脸信息列表
    		faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(),imageInfo.getHeight(),imageInfo.getImageFormat(),faceInfoList);// 从图像信息中采集人脸信息
    		if (faceInfoList.isEmpty()) {// 如果人脸信息是空的
				return null;
			}
    		FaceFeature faceFeature=new FaceFeature(); // 人脸特征
            faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),  // 从人脸信息中采集人脸特征
                    imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
            return faceFeature;// 采集之后的人脸特征
    }
    public static void loadAllFaceFeature() {// 加载所有面部特征
    	Set<String>keys = Session.IMAGE_MAP.keySet();// 获取所有人脸图片对应的特征码集合
    	for(String code:keys) {// 遍历所有特征码
    		BufferedImage image = Session.IMAGE_MAP.get(code);// 取出一张人脸图片
    		FaceFeature faceFeature = getFaceFeature(image); // 获取该人脸图片的人脸特征对象
    		Session.FACE_FEATURE_MAP.put(code, faceFeature);// 将人脸特征对象保存至全局会话中
    	}
    }
    public static String detectFace(FaceFeature targetFaceFeature) {// 从人脸特征库中检测人脸
		if(targetFaceFeature == null) {
			return null;
	}
		Set<String>keys = Session.FACE_FEATURE_MAP.keySet();// 获取所有人脸特征对应的特征码集合
		float score=0;// 匹配最高得分
		String resultCode = null;// 评分对应的特征码
		for(String code:keys) {// 遍历所有特征码
			FaceFeature sourceFaceFeature = Session.FACE_FEATURE_MAP.get(code);// 取出一个人脸特征对象
			FaceSimilar faceSimilar = new FaceSimilar();// 特征比对对象
			faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar); // 对比目标人脸特征和取出的人脸特征
			if(faceSimilar.getScore()>score) {// 如果得分大于当前最高得分
				score = faceSimilar.getScore();// 重新记录当前最高得分
				resultCode = code;// 记录最高得分的特征码
			}
		}
		if(score>0.9) {// 如果最高得分大于0.9，则认为找到匹配人脸
			return resultCode;// 返回人脸对应的特征码
		}
		return null;
}
    public static void dispost() {
    	faceEngine.unInit();
    }
    }
