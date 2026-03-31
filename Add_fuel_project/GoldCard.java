package payment;

public class GoldCard extends  Card{
    public GoldCard(String cardNumber, String name, String phone, double money) {
        super(cardNumber, name, phone, money);
    }
    @Override
    public void consume(double money) {
        System.out.println("您当前消费："  + money);
        System.out.println("金卡优惠后的价格：："  + money*0.8);
        if(getMoney()<money*0.8){
            System.out.println("您当前余额为：\" + getMoney() + \",余额不足，欢迎下次光临");
            return;  //  结束方法
        }
        //  更新金卡余额
        setMoney(getMoney()-money*0.8);
        //  消费大于200送洗车票
        if(money*0.8>200){
            printTicket();
        }
        else{
            System.out.println("您当前消费不满200，不能免费洗车");
        }
    }

    //  打印洗车票
    public void printTicket() {
        System.out.println("恭喜您获得一张洗车票");
    }
}
