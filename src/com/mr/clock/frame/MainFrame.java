package com.mr.clock.frame;

import java.awt.Container;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import com.mr.clock.session.Session;

public class MainFrame extends JFrame {//主窗体
	public MainFrame() {
		Session.init();// 全局会话初始化
		addListener();// 添加监听
		setSize(640, 480);// 窗体宽高
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 点击关闭按钮不触发任何事件
		Toolkit tool = Toolkit.getDefaultToolkit(); // 创建系统默认组件工具包
		Dimension d = tool.getScreenSize(); // 获取屏幕尺寸，赋给坐标对象
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);// 让主窗体在屏幕中间显示
	}
	private void addListener() {// 添加组件监听
		addWindowListener(new WindowAdapter() {// 添加窗体事件监听
			@Override
			public void windowClosing(WindowEvent e) {// 窗体关闭时
				 int closeCode = JOptionPane.showConfirmDialog(MainFrame.this, "是否退出程序？", "提示！",JOptionPane.YES_NO_OPTION);// 弹出选择对话框，并记录用户做出的选择
				 if (closeCode == JOptionPane.YES_OPTION) {// 如果用户选择确定
					 Session.dispose();// 释放全局资源
					 System.exit(0);// 关闭程序
				 }
			}
		});
	}
	 public void setPanel(JPanel panel) {// 更换主容器中的面板
		 Container c = getContentPane();// 获取主容器对象
		 c.removeAll();// 删除容器中所有组件
		 c.add(panel);// 容器添加面板
		 c.validate();// 容器重新验证所有组件
	 }
}