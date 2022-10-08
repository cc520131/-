package com.mr.clock.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.mr.clock.session.Session;


public class ImageService {//图像文件服务
  private static final File FACE_DIR=new File("src/faces"); // 存放员工照片文件的文件夹
  private static final String SUFFIX= "png"; // 图像文件的默认格式
  public static Map<String, BufferedImage>loadAllImage(){//加载所有人脸图像文件
	  if(!FACE_DIR.exists()) {// 如果人脸图像的文件夹丢失
		  System.err.println("人脸图像文件夹丢失！");
		  return null;
	  }
	  File faces[]=FACE_DIR.listFiles();// 获取文件夹下的所有文件
	  for(File f:faces) {// 遍历每一个文件
		  try {
			BufferedImage img = ImageIO.read(f); // 创建该图像文件的BufferedImage对象
			String fileName = f.getName();// 文件名
			String code = fileName.substring(0,fileName.indexOf('.')); // 截取文件名，去掉后缀名
			Session.IMAGE_MAP.put(code, img); // 将人脸图像添加到全局会话中
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	  }
	  return null;
  }
  public static void saveFaceImage(BufferedImage image,String code) {//保存人脸图像文件
	  try {
		ImageIO.write(image, SUFFIX, new File(FACE_DIR,code+"."+SUFFIX));// 将图像按照SUFFIX格式写入到文件夹中
		Session.IMAGE_MAP.put(code, image); // 将人脸图像添加到全局会话中
	} catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
  }
  public static void deleteFaceImage(String code) {// 删除人脸图像文件
	  Session.IMAGE_MAP.remove(code);// 在全局会话中删除该员工的图像
	  File image = new File(FACE_DIR,code+="."+SUFFIX);// 创建该员工人脸图像文件对象
	  if(image.exists()) {// 如果此文件存在
		  image.delete();// 删除文件
		  System.out.println(image.getAbsolutePath()+"已删除");// 提示删除文件成功
	  }
  }
}
