package payment;

public class SilverCard extends  Card {
    public SilverCard(String cardNumber, String name, String phone, double money) {
        super(cardNumber, name, phone, money);
    }
    @Override
    public void consume(double money) {
        System.out.println("您当前消费：" + money);
        System.out.println("银卡优惠后的价格：" + money * 0.9);
        if(getMoney()<money*0.9){
            System.out.println("您当前余额为：" + getMoney() + ",余额不足，欢迎下次光临");
            return;  //  结束方法
        }
        //更新银卡卡余额
        setMoney(getMoney() - money * 0.9);
    }
}
