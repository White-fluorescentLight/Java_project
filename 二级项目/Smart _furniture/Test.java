package demo;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //  面向对象编程实现智能家具控制系统
        //  角色：设备
        //  具备的功能：开关
        //  谁控制他们：智能控制系统（单例对象），控制设备开关
        //  1.定义设备类：创建设备对象
        //  2.准备设备对象放数组中，代表整个家庭的设备
        JD[] jds = new JD[4];
        jds[0] = new TV("苹果电视\uD83D\uDCFA",true);
        jds[1] = new WashMachine("美的洗烘一体机\uD83E\uDDFA",true);
        jds[2] = new Lamp("华为LED\uD83D\uDCA1",true);
        jds[3] = new Air("大米空调❄\uFE0F",true);

        //  3.为每个设备制定开关功能。定义一个接口，让JD实现开关功能
        //  4.创建智能控制系统对象，控制设备开关
        demo.SmartControl smartControl = new demo.SmartControl();
        //  5.控制电视机
        //  smartControl.control(jds[0]);

        //  6.提示用户操作， a、展示全部设备状况 b、让用户选择操作
        //  打印全部设备的开关状态
          while(true) {
            smartControl.printAllStatus(jds);
            System.out.println("请选择要控制的设备：");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            switch (choice) {
                case "1":
                    smartControl.control(jds[0]);
                    break;
                case "2":
                    smartControl.control(jds[1]);
                    break;
                case "3":
                    smartControl.control(jds[2]);
                    break;
                case "4":
                    smartControl.control(jds[3]);
                    break;
                case "exit":
                    System.out.println("退出App！");
                    return;
                default:
                    System.out.println("输入错误！");
            }
          }
    }
}
