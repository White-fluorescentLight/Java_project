package demo;

public class SmartControl {
    public void control(JD jd){
        System.out.println("控制台：");
        System.out.println(jd.getName()+"状态是："+(jd.isStatus()?"开着":"关闭"));
        System.out.println("Ready");
        jd.press();
        System.out.println(jd.getName()+"状态已经是："+(jd.isStatus()?"开着":"关闭"));
    }

    public void printAllStatus(JD[] jds){
        System.out.println("展示台");
        //  使用for循环遍历
        for(int i=0;i<jds.length;i++){
            System.out.println(jds[i].getName()+"状态是："+(jds[i].isStatus()?"开着":"关闭"));
        }
    }
}
