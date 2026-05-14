package chat_ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

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
    private Socket socket;
    private DataOutputStream dos;

    // 构造方法（接收登录昵称）
    public ClientChatFrame() {
        // 初始化窗口
        initFrame();
        // 初始化组件与布局
        initComponents();
        // 初始化事件
        initEvents();
    }
    public ClientChatFrame(String nickname, Socket socket) {
        this(); // 调上面的构造器，初始化界面信息
        // 初始化数据
        // 立马展示昵称到窗口
        setTitle("局域网群聊 - " + nickname);
        this.socket = socket;

        try {
            // 获取输出流，用于发送消息
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 立即把客户端的这个Socket管道交给一个独立的线程专门负责读取客户端socket从服务端收到的在线人数更新数据或群聊数据
        new ClientReaderThread(socket, this).start();
    }

    /**
     * 窗口基础配置
     */
    private void initFrame() {
        setTitle("局域网群聊 - ");
        setSize(950, 650); // 设置合理的窗口大小
        setLocationRelativeTo(null); // 窗口居中
        setResizable(true); // 允许调整窗口大小
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 点击关闭按钮，退出程序
        getContentPane().setBackground(Color.WHITE);
    }

    /**
     * 组件布局（核心：分区域设计）
     */
    private void initComponents() {
        // 1. 主面板：边界布局（中=消息区，东=在线人数区，南=输入区）
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // ---------------------- 中间：消息展示框 ----------------------
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
        mainPanel.add(msgScroll, BorderLayout.CENTER);

        // ---------------------- 右侧：在线人数展示框 ----------------------
        JPanel onlinePanel = new JPanel(new BorderLayout(5, 5));
        onlinePanel.setBackground(Color.WHITE);
        onlinePanel.setPreferredSize(new Dimension(150, 0)); // 固定宽度150像素

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

        // ---------------------- 底部：消息输入+发送按钮 ----------------------
        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setPreferredSize(new Dimension(0, 45)); // 设置输入区域高度

        // 消息输入框
        msgInputField = new JTextField();
        msgInputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
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

    // 发送消息给服务端
    private void sendMsgToServer(String msg) {
        try {
            // 1.把消息类型发送给服务端（2代表群聊消息）
            dos.writeInt(2);
            // 2.把消息内容发送给服务端
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 更新在线人数
    public void updataClientOnlinetUsers(String[] onlinenames) {
        // 确保在事件调度线程中更新UI
        SwingUtilities.invokeLater(() -> {
            // 把线程读取到的在线用户昵称展示到界面上
            onlineUserList.setListData(onlinenames);
        });
    }

    // 更新群聊消息
    public void setMsgToWin(String msg){
        // 确保在事件调度线程中更新UI
        SwingUtilities.invokeLater(() -> {
            // 将\r\n替换为\n，确保换行正确显示
            String formattedMsg = msg.replace("\r\n", "\n");
            // 更新群聊消息到界面展示
            msgDisplayArea.append(formattedMsg + "\n");
            // 滚动到最新消息
            msgDisplayArea.setCaretPosition(msgDisplayArea.getText().length());
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
        // 发送消息给服务端
        sendMsgToServer(msg);
        // 清空输入框
        msgInputField.setText("");
    }

}
