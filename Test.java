package payment;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //  加油支付小程序
        //  金/银卡类(继承Card类)必须重写消费金额，它有消费优惠(8折/9折)
        //  办一张金卡：创建金卡对象，交给一个独立业务(pos机)完成流水
        //  金卡对象
        Card goldCard = new GoldCard("京A88888", "马化腾", "13888888888", 5000);
        pay(goldCard);
        //  银卡对象
        Card silverCard = new SilverCard("京A88888", "马云", "13888888888", 2000);
        pay(silverCard);
    }

    // 支付机：用一个方法来刷卡
    public static void pay(Card c) {
        System.out.println("请刷卡，请输入当前消费金额");
        Scanner sc = new Scanner(System.in);
        double money = sc.nextDouble();
        c.consume(money);
    }
}
