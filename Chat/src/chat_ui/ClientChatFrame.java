package chat_ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 局域网群聊主界面
 * 布局：顶部消息展示区 | 底部消息输入区+发送按钮 | 右侧在线人数区
 * 纯原生Swing实现，无第三方依赖，美观简洁
 */
public class ClientChatFrame extends JFrame {
    // 核心组件
    private JTextArea msgDisplayArea;    // 消息展示框
    private JTextField msgInputField;    // 消息发送框
    private JButton sendBtn;             // 发送按钮
    private JList<String> onlineUserList;// 在线人数列表
    private DefaultListModel<String> onlineUserModel; // 在线人数数据模型

    // 构造方法（接收登录昵称）
    public ClientChatFrame(String nickname) {
        // 初始化窗口
        initFrame(nickname);
        // 初始化组件与布局
        initComponents();
        // 初始化事件
        initEvents();
    }

    /**
     * 窗口基础配置
     */
    private void initFrame(String nickname) {
        setTitle("局域网群聊 - " + nickname);
        setSize(800, 600); // 适配聊天界面的尺寸
        setLocationRelativeTo(null); // 窗口居中
        setResizable(true); // 允许调整窗口大小
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 点击关闭按钮，退出程序
        getContentPane().setBackground(Color.WHITE);
    }

    /**
     * 组件布局（核心：分区域设计）
     */
    private void initComponents() {
        // 1. 主面板：边界布局（北=消息区，南=输入区，东=在线人数区）
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // ---------------------- 顶部：消息展示框 ----------------------
        msgDisplayArea = new JTextArea();
        msgDisplayArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        msgDisplayArea.setEditable(false); // 禁止编辑
        msgDisplayArea.setLineWrap(true);  // 自动换行
        msgDisplayArea.setWrapStyleWord(true); // 按单词换行
        msgDisplayArea.setBackground(new Color(248, 249, 250)); // 浅灰背景
        msgDisplayArea.setBorder(new LineBorder(new Color(200, 200, 200), 1, true)); // 圆角边框
        // 消息区加滚动条
        JScrollPane msgScroll = new JScrollPane(msgDisplayArea);
        msgScroll.setBorder(null);
        msgScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(msgScroll, BorderLayout.NORTH);

        // ---------------------- 底部：消息输入+发送按钮 ----------------------
        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setBackground(Color.WHITE);

        // 消息输入框
        msgInputField = new JTextField();
        msgInputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        msgInputField.setPreferredSize(new Dimension(0, 35));
        msgInputField.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        msgInputField.setToolTipText("输入消息后按回车或点击发送");
        inputPanel.add(msgInputField, BorderLayout.CENTER);

        // 发送按钮
        sendBtn = new JButton("发送");
        sendBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        sendBtn.setPreferredSize(new Dimension(80, 35));
        sendBtn.setBackground(new Color(219, 112, 147)); // 主题蓝
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inputPanel.add(sendBtn, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // ---------------------- 右侧：在线人数展示框 ----------------------
        JPanel onlinePanel = new JPanel(new BorderLayout(5, 5));
        onlinePanel.setBackground(Color.WHITE);
        onlinePanel.setPreferredSize(new Dimension(180, 0)); // 固定宽度

        // 在线人数标题
        JLabel onlineTitle = new JLabel(" 在线人数", SwingConstants.LEFT);
        onlineTitle.setFont(new Font("微软雅黑", Font.BOLD, 15));
        onlineTitle.setForeground(new Color(219, 112, 147));
        onlineTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        onlinePanel.add(onlineTitle, BorderLayout.NORTH);

        // 在线人数列表
        onlineUserModel = new DefaultListModel<>();
        onlineUserList = new JList<>(onlineUserModel);
        onlineUserList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        onlineUserList.setBackground(new Color(248, 249, 250));
        onlineUserList.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        // 在线列表加滚动条
        JScrollPane onlineScroll = new JScrollPane(onlineUserList);
        onlineScroll.setBorder(null);
        onlinePanel.add(onlineScroll, BorderLayout.CENTER);

        mainPanel.add(onlinePanel, BorderLayout.EAST);
    }

    /**
     * 事件绑定（发送按钮+回车发送）
     */
    private void initEvents() {
        // 发送按钮点击事件
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 输入框回车发送
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    /**
     * 发送消息核心逻辑
     */
    private void sendMessage() {
        String msg = msgInputField.getText().trim();
        if (msg.isEmpty()) {
            JOptionPane.showMessageDialog(null, "消息不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 模拟显示消息（实际项目中替换为局域网发送逻辑）
        String showMsg = "我：" + msg + "\n";
        msgDisplayArea.append(showMsg);
        // 滚动到最新消息
        msgDisplayArea.setCaretPosition(msgDisplayArea.getText().length());
        // 清空输入框
        msgInputField.setText("");
    }

    /**
     * 测试入口（实际项目中从登录界面跳转）
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientChatFrame("测试用户").setVisible(true);
            }
        });
    }
}