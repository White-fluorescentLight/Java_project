package demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//家电父类，实现Switch接口
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JD implements Switch {
    private String name;
    // 状态：开或关
    private boolean status; // false 默认关闭

    // 实现接口的press方法
    @Override
    public void press() {
        //  控制当前设备开关
        status = !status;
    }
}
