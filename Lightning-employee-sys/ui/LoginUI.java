package com.employee.ui;

import com.employee.bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// 自定义登录界面
public class LoginUI extends JFrame implements ActionListener {
    private JTextField loginNameField;   // 用户名输入框
    private JPasswordField passwordField; // 密码输入框
    private JButton loginButton;        // 登录按钮
    private JButton registerButton;     // 注册按钮（恢复原变量名）
    //定义一个静态的集合，存储系统中全部用户对象信息
    private static ArrayList<User> allUsers = new ArrayList<>();

    //初始化几个测试的用户对象信息，作为登录用
    static {
        allUsers.add(new User("马云", "123456", "admin"));
        allUsers.add(new User("Light", "131452", "light"));
    }

    public LoginUI() {
        super("闪电人事管理系统 - 登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        createAndShowGUI();

        this.setVisible(true);
    }

    private void createAndShowGUI() {
        // 1. 背景面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 400);
        panel.setBackground(new Color(245, 248, 255));
        this.add(panel);

        // 2. 大字标题
        JLabel title = new JLabel("闪电人事管理系统", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(255, 220, 23));
        title.setBounds(50, 30, 400, 40);
        panel.add(title);

        // 3. 用户名标签
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        usernameLabel.setBounds(100, 110, 100, 30);
        panel.add(usernameLabel);

        // 4. 用户名输入框
        loginNameField = new JTextField();
        loginNameField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        loginNameField.setBounds(200, 110, 200, 35);
        loginNameField.setBorder(BorderFactory.createLineBorder(new Color(255, 153, 0), 2));
        panel.add(loginNameField);

        // 5. 密码标签
        JLabel passwordLabel = new JLabel("密  码：");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passwordLabel.setBounds(100, 170, 100, 30);
        panel.add(passwordLabel);

        // 6. 密码输入框
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        passwordField.setBounds(200, 170, 200, 35);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 153, 0), 2));
        panel.add(passwordField);

        // 7. 登录按钮
        loginButton = new JButton("登  录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 18));
        loginButton.setBounds(100, 250, 130, 45);
        loginButton.setBackground(new Color(102, 204, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        panel.add(loginButton);
        loginButton.addActionListener(this);

        // 8. 注册按钮
        registerButton = new JButton("注  册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 18));
        registerButton.setBounds(270, 250, 130, 45);
        registerButton.setBackground(new Color(153, 51, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        panel.add(registerButton);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == loginButton) {
            login();
        } else {
            System.out.println("注册开始->\uD83D\uDCDD");
        }
    }

    private void login() {
        String loginName = loginNameField.getText();
        String password = new String(passwordField.getPassword());
        User user = getUserByLoginName(loginName);  //调用获取用户对象方法

        if (user != null) {
            //  密码判断
            if (user.getPassword().equals(password)) {
                System.out.println("登录成功！");
                new EmployeeManagerUI(user);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "登录名不存在!","登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  根据用户名去查询用户对象返回，查询到用户对象则正确，没查询到则返回null
    private User getUserByLoginName(String loginName) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }
        return null;
    }
}