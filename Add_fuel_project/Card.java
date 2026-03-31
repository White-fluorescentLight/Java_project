package payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String cardNumber;  //  车牌
    private String name;
    private String phone;
    private double money;  //  余额

    //预存金额
    public void deposit(double money) {
        this.money += money;
    }

    //消费金额
    public void consume(double money) {
        this.money -= money;
    }
}

