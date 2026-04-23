package chat_ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 局域网聊天系统 - 登录界面
 * 纯原生Swing实现
 * 功能：昵称输入 + 进入聊天 + 取消退出
 */

public class ChatLoginFrame extends JFrame {
    // 核心组件
    private JTextField nicknameField;
    private JButton enterBtn;
    private JButton cancelBtn;

    public ChatLoginFrame() {
        // 初始化窗口
        initWindow();
        // 初始化组件与布局
        initContent();
        // 初始化按钮事件
        initAction();
    }

    /**
     * 窗口基础配置（原生Swing默认样式优化）
     */
    private void initWindow() {
        setTitle("局域网聊天室");
        setSize(400, 260);
        setLocationRelativeTo(null); // 窗口居中
        setResizable(false); // 禁止缩放
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // 纯白背景，更清爽
    }

    /**
     * 组件布局（美观居中，原生Swing最优布局）
     */
    private void initContent() {
        // 主面板：内边距，避免组件贴边
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        setContentPane(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8); // 组件间距
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. 标题
        JLabel titleLabel = new JLabel("欢迎进入局域网聊天室", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setForeground(new Color(219, 112, 147));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // 2. 昵称标签
        JLabel nameLabel = new JLabel("用户昵称：");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameLabel, gbc);

        // 3. 昵称输入框（原生美化）
        nicknameField = new JTextField();
        nicknameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        nicknameField.setPreferredSize(new Dimension(200, 32));
        nicknameField.setBorder(new LineBorder(new Color(220, 53, 69), 1, true)); // 圆角边框
        nicknameField.setToolTipText("请输入昵称");
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(nicknameField, gbc);

        // 4. 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        btnPanel.setBackground(Color.WHITE);

        // 进入按钮（主按钮）
        enterBtn = new JButton("进入聊天");
        enterBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        enterBtn.setPreferredSize(new Dimension(95, 35));
        enterBtn.setBackground(new Color(60, 179, 113));
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setFocusPainted(false); // 去掉按钮焦点框
        enterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 鼠标悬浮变手型

        // 取消按钮
        cancelBtn = new JButton("取    消");
        cancelBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        cancelBtn.setPreferredSize(new Dimension(95, 35));
        cancelBtn.setBackground(new Color(60, 179, 113));
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPanel.add(enterBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(btnPanel, gbc);
    }

    /**
     * 按钮点击事件
     */
    private void initAction() {
        // 进入按钮：昵称非空校验
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText().trim();
                if (nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "昵称不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 登录成功：这里可以写进入聊天主界面的逻辑
                JOptionPane.showMessageDialog(null, "欢迎，" + nickname + "！", "登录成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 取消按钮：退出程序
        cancelBtn.addActionListener(e -> System.exit(0));
    }
    public static void main(String[] args) {
        new ChatLoginFrame().setVisible(true);
    }

}
