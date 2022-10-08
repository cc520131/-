package com.mr.clock.session;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.arcsoft.face.FaceFeature;
import com.mr.clock.pojo.Employee;
import com.mr.clock.pojo.User;
import com.mr.clock.pojo.WorkTime;
import com.mr.clock.service.CameraService;
import com.mr.clock.service.FaceEngineService;
import com.mr.clock.service.HRService;
import com.mr.clock.service.ImageService;
import com.mr.clock.util.JDBCUtil;
public class Session {// 全局会话，用来缓存全局数据
    public static User user = null;//当前登录管理员
    public static WorkTime worktime = null;//当前作息时间
    public static final HashSet<Employee> EMP_SET = new HashSet<>();// 全部员工
    public static final HashMap<String, FaceFeature> FACE_FEATURE_MAP = new HashMap<>();//全部人脸特征
    public static final HashMap<String, BufferedImage> IMAGE_MAP = new HashMap<>();//全部人脸图像
    public static final HashMap<Integer, Set<Date>> RECORD_MAP = new HashMap<>();//全部打卡记录
    public static void init() {//初始化全局资源
        ImageService.loadAllImage();//加载所有人脸图像文件
        HRService.loadWorkTime();//  加载作息时间
        HRService.loadAllEmp();//加载所有员工
        HRService.loadAllClockInRecord();// 加载所有打卡记录
        FaceEngineService.loadAllFaceFeature();//加载所有人脸特征
    }

    public static void dispose() {// 释放全局资源
        FaceEngineService.dispost();// 释放人脸识别引擎
        CameraService.releaseCamera();// 释放摄像头
        JDBCUtil.closeConnection();// 关闭数据库连接
    }
}
