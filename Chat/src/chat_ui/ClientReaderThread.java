package chat_ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientReaderThread extends Thread {
    private Socket socket;
    private DataInputStream dis;
    private ClientChatFrame win;
    public ClientReaderThread(Socket socket, ClientChatFrame win) {
        this.win = win;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 接受的消息类型可能有很多种：1.在线人数更新的数据 2.群聊消息
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int msgType = dis.readInt(); // 消息类型编号 1、2、3
                switch (msgType) {
                    case 1:
                        // 服务端发来的在线人数更新消息
                        updataClientOnlinetUserList();
                        break;
                    case 2:
                        // 服务端发送来的群聊消息
                        getMsgToWin();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取群聊消息
    private void getMsgToWin() throws IOException {
        // 1.获取群聊消息
        String msg = dis.readUTF();
        win.setMsgToWin(msg);
    }

    // 获取在线人数更新消息
    private void updataClientOnlinetUserList() throws Exception {
        // 1.读取有多少个在线用户
        int count = dis.readInt();
        // 2.循环控制读取多少个用户信息
        String[] names = new String[count];
        for (int i = 0; i < count; i++){
            // 3.读取每个用户信息
            String nickname = dis.readUTF();
            // 4.将每个用户信息添加到集合中
            names[i] = nickname;
        }
        // 5.将集合中的数据展示到窗口上
        win.updataClientOnlinetUsers(names);
    }
}
