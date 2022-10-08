package com.mr.clock.frame;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mr.clock.service.HRService;


public class LoginDialog extends JDialog {//登录对话框
    private JTextField usernameField = null;// 用户名文本框
    private JPasswordField passwordField = null;// 密码输入框
    private JButton loginBtn = null;// 登录按钮
    private JButton cancelBtn = null;// 取消按钮
    private final int WIDTH = 300, HEIGHT = 150;// 对话框的宽高

    public LoginDialog(Frame owner) {
        super(owner, "管理员登录", true);// 阻塞主窗体
        setSize(WIDTH, HEIGHT);// 设置宽高
        setLocation(owner.getX() + (owner.getWidth() - WIDTH) / 2, owner.getY() + (owner.getHeight() - HEIGHT) / 2);// 在主窗体中央显示
        init();// 组件初始化
        addListener();// 为组件添加监听
    }
    private void init() {//组件初始化
        JLabel usernameLabel = new JLabel("用户名", JLabel.CENTER);
        JLabel passwordLabel = new JLabel("密码", JLabel.CENTER);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginBtn = new JButton("登录");
        cancelBtn = new JButton("取消");

        Container c = getContentPane();
        c.setLayout(new GridLayout(3, 2));// 3行2列的网格布局
        c.add(usernameLabel);
        c.add(usernameField);
        c.add(passwordLabel);
        c.add(passwordField);
        c.add(loginBtn);
        c.add(cancelBtn);
    }
    private void addListener() {//为组件添加监听
        cancelBtn.addActionListener(new ActionListener() {// 取消按钮的事件
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog.this.dispose();// 销毁登录对话框
            }
        });
        loginBtn.addActionListener(new ActionListener() {// 登录按钮的事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();// 获取用户输入的用户名
                String password = new String(passwordField.getPassword());// 获取用户输入的密码
                boolean result = HRService.userLogin(username, password);// 检查用户名和密码是否正确
                if (result) {// 如果正确
                    LoginDialog.this.dispose();// 销毁登录对话框
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "用户名或者密码错误"); // 提示用户名、密码错误
                }
            }
        });
        passwordField.addActionListener(new ActionListener() { // 密码输入框敲击回车的事件
            @Override
            public void actionPerformed(ActionEvent e) {
                loginBtn.doClick();// 触发登录按钮点击事件
            }
        });
        usernameField.addActionListener(new ActionListener() {// 用户名文本框框敲击回车的事件
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.grabFocus();// 密码输入框获取光标
            }
        });
    }
}
