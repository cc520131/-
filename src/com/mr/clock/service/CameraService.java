package com.mr.clock.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class CameraService {//摄像头服务
	private static final Webcam WEBCAM = Webcam.getDefault();//摄像头对象
	public static boolean startCamera() {//开启摄像头
		if(WEBCAM==null) {// 如果计算机没有连接摄像头
			return false;
		}
		WEBCAM.setViewSize(new Dimension(640, 480)); // 摄像头采用默认的640*480宽高
		return WEBCAM.open();// 开启摄像头，返回开启是否成功
	}
	public static boolean cameraIsOpen() {//摄像头是否开启
		if(WEBCAM==null) {// 如果计算机没有连接摄像头
			return false;
		}
		return WEBCAM.isOpen();
	}
	public static JPanel getCameraPanel() {//获取摄像头画面面板
		WebcamPanel panel = new WebcamPanel(WEBCAM); // 摄像头画面面板
		panel.setMirrored(true);// 开启镜像
		return panel;
	}
	public static BufferedImage getCameraFrame() {//获取摄像头捕获的帧画面
		return WEBCAM.getImage();// 获取当前帧画面
	}
	public static void releaseCamera() {//释放摄像头资源
		if(WEBCAM!=null) {
			WEBCAM.close();// 关闭摄像头
		}
	}
}
