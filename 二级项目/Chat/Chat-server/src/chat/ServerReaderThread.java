package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

public class ServerReaderThread extends Thread {
    private Socket socket;
    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 接受的消息类型可能有很多种：1.登录消息 2.群聊消息 3.私聊消息
            // 所以客户端必须先声明协议
            // 比如客服端先发1，代表接下来是登录消息
            // 比如客户端先发2，代表接下来是群聊消息
            // 先从socket管道中接收客户端发送来的消息类型编号
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int msgType = dis.readInt(); // 消息类型编号 1、2、3
                switch (msgType) {
                    case 1:
                        // 登录消息,接下来接收昵称数据，再更新全部在线客户端的在线人数列表
                        String nickname = dis.readUTF();
                        // 把这个登录成功的客户端socket存入到在线集合
                        Server.onlineSockets.put(socket, nickname);
                        // 更新全部在线客户端的在线人数列表
                        updataClientOnlinetUserList();
                        break;
                    case 2:
                        // 群聊消息, 接收群聊消息内容，再把群聊消息发送给所有在线客户端
                        String Msg = dis.readUTF();
                        sendMsgToAll(Msg);
                        break;
                    case 3:
                        // 私聊消息, 接收私聊消息内容，再把私聊消息发送给指定客户端
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("客户端下线了"+ socket.getInetAddress().getHostAddress());
            Server.onlineSockets.remove(socket); // 把下线的客户端从在线集合中移除
            updataClientOnlinetUserList(); //更新全部在线客户端的在线人数列表
        }
    }

    // 给全部在线socket推送当前客服端发来的消息
    private void sendMsgToAll(String msg) {
        // 一定要拼装好这个消息，再发给全部在线socket
        StringBuilder sb = new StringBuilder();
        String name= Server.onlineSockets.get(socket);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowstr = dtf.format(now);

        String msgResult = sb.append(name).append(" ").append(nowstr).append("\r\n")
                .append(msg).append("\r\n").toString();
        // 把消息推送给全部客户端socket
        for (Socket socket : Server.onlineSockets.keySet()) {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(2); // 1告诉客户端接下来是在线人数列表信息 2代表发的群聊消息
                dos.writeUTF(msgResult);
                dos.flush(); // 刷新数据
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void updataClientOnlinetUserList() {
        // 1.拿到当前全部在线用户昵称
        Collection<String> onlinetUser = Server.onlineSockets.values();
        // 2.把这个集合中的所有的用户都推送给全部客户端的socket管道
        for (Socket socket : Server.onlineSockets.keySet()) {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(1); // 1告诉客户端接下来是在线人数列表信息 2代表发的群聊消息
                dos.writeInt(onlinetUser.size()); // 告诉客户端，要发多少个用户名称
                for (String nickname : onlinetUser) {
                    dos.writeUTF(nickname);
                }
                dos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

