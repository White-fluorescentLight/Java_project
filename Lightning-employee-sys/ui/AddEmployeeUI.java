package com.employee.ui;

import com.employee.bean.Employee;
import javax.swing.*;
import java.awt.*;

/**
 * 独立的添加员工界面
 * 功能：接收用户输入、验证数据、封装对象、存入主界面集合并刷新表格
 */
public class AddEmployeeUI extends JFrame {
    // 主界面引用，用于回调刷新
    private EmployeeManagerUI parentUI;

    // 输入框组件
    private JTextField nameField;
    private JTextField sexField;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField positionField;
    private JTextField entryDateField;
    private JTextField salaryField;
    private JTextField departmentField;

    /**
     * 构造方法
     * @param parentUI 员工管理主界面
     */
    public AddEmployeeUI(EmployeeManagerUI parentUI) {
        super("添加新员工");
        this.parentUI = parentUI;

        // 初始化窗口属性
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450, 500);
        this.setLocationRelativeTo(parentUI); // 相对于主界面居中
        this.setLayout(null);
        this.setResizable(false); // 禁止调整窗口大小

        // 创建界面组件
        createAndShowGUI();
        this.setVisible(true);
    }

    /**
     * 创建并布局所有UI组件
     */
    private void createAndShowGUI() {
        // 背景面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 450, 500);
        panel.setBackground(new Color(245, 248, 255));
        this.add(panel);

        // 标题
        JLabel title = new JLabel("添加新员工", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 24));
        title.setForeground(new Color(47, 128, 237));
        title.setBounds(0, 30, 450, 35);
        panel.add(title);

        // 统一的样式配置
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font fieldFont = new Font("微软雅黑", Font.PLAIN, 14);
        Color borderColor = new Color(47, 128, 237);

        // ---------------- 1. 姓名 ----------------
        JLabel nameLabel = new JLabel("姓  名：");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(80, 90, 70, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(160, 90, 200, 28);
        nameField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(nameField);

        // ---------------- 2. 性别 ----------------
        JLabel sexLabel = new JLabel("性  别：");
        sexLabel.setFont(labelFont);
        sexLabel.setBounds(80, 130, 70, 25);
        panel.add(sexLabel);

        sexField = new JTextField("男"); // 默认值
        sexField.setFont(fieldFont);
        sexField.setBounds(160, 130, 200, 28);
        sexField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(sexField);

        // ---------------- 3. 年龄 ----------------
        JLabel ageLabel = new JLabel("年  龄：");
        ageLabel.setFont(labelFont);
        ageLabel.setBounds(80, 170, 70, 25);
        panel.add(ageLabel);

        ageField = new JTextField();
        ageField.setFont(fieldFont);
        ageField.setBounds(160, 170, 200, 28);
        ageField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(ageField);

        // ---------------- 4. 电话 ----------------
        JLabel phoneLabel = new JLabel("电  话：");
        phoneLabel.setFont(labelFont);
        phoneLabel.setBounds(80, 210, 70, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        phoneField.setBounds(160, 210, 200, 28);
        phoneField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(phoneField);

        // ---------------- 5. 职位 ----------------
        JLabel positionLabel = new JLabel("职  位：");
        positionLabel.setFont(labelFont);
        positionLabel.setBounds(80, 250, 70, 25);
        panel.add(positionLabel);

        positionField = new JTextField();
        positionField.setFont(fieldFont);
        positionField.setBounds(160, 250, 200, 28);
        positionField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(positionField);

        // ---------------- 6. 入职日期 ----------------
        JLabel entryDateLabel = new JLabel("入职日期：");
        entryDateLabel.setFont(labelFont);
        entryDateLabel.setBounds(80, 290, 70, 25);
        panel.add(entryDateLabel);

        entryDateField = new JTextField("2026-04-05"); // 默认当前日期
        entryDateField.setFont(fieldFont);
        entryDateField.setBounds(160, 290, 200, 28);
        entryDateField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(entryDateField);

        // ---------------- 7. 工资 ----------------
        JLabel salaryLabel = new JLabel("工  资：");
        salaryLabel.setFont(labelFont);
        salaryLabel.setBounds(80, 330, 70, 25);
        panel.add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setFont(fieldFont);
        salaryField.setBounds(160, 330, 200, 28);
        salaryField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(salaryField);

        // ---------------- 8. 部门 ----------------
        JLabel departmentLabel = new JLabel("部  门：");
        departmentLabel.setFont(labelFont);
        departmentLabel.setBounds(80, 370, 70, 25);
        panel.add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setFont(fieldFont);
        departmentField.setBounds(160, 370, 200, 28);
        departmentField.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        panel.add(departmentField);

        // ---------------- 按钮区域 ----------------
        // 确定按钮
        JButton confirmBtn = new JButton("确  定");
        confirmBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        confirmBtn.setBounds(100, 420, 100, 36);
        confirmBtn.setBackground(new Color(102, 204, 0)); // 黄绿色
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        panel.add(confirmBtn);
        confirmBtn.addActionListener(e -> handleConfirm()); // 绑定添加事件

        // 取消按钮
        JButton cancelBtn = new JButton("取  消");
        cancelBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        cancelBtn.setBounds(250, 420, 100, 36);
        cancelBtn.setBackground(new Color(153, 51, 255)); // 紫色
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        panel.add(cancelBtn);
        cancelBtn.addActionListener(e -> this.dispose()); // 绑定关闭事件
    }

    /**
     * 处理确定按钮点击：验证数据 -> 封装对象 -> 存入集合 -> 刷新表格
     */
    private void handleConfirm() {
        try {
            // 1. 获取并去除输入框首尾空格
            String name = nameField.getText().trim();
            String sex = sexField.getText().trim();
            String ageText = ageField.getText().trim();
            String phone = phoneField.getText().trim();
            String position = positionField.getText().trim();
            String entryDate = entryDateField.getText().trim();
            String salaryText = salaryField.getText().trim();
            String department = departmentField.getText().trim();

            // 2. 非空验证
            if (name.isEmpty() || phone.isEmpty() || position.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. 数字格式转换
            int age = Integer.parseInt(ageText);
            double salary = Double.parseDouble(salaryText);

            // 4. 范围验证
            if (age <= 0 || age > 100) {
                JOptionPane.showMessageDialog(this, "年龄输入不合法（1-100）！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (salary < 0) {
                JOptionPane.showMessageDialog(this, "工资不能为负数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. 生成新ID（集合大小+1）
            int newId = parentUI.getEmployees().size() + 1;

            // 6. 封装成 Employee 对象
            Employee newEmployee = new Employee(
                    newId, name, sex, age, phone, position, entryDate, salary, department
            );

            // 7. 添加到主界面集合
            parentUI.getEmployees().add(newEmployee);

            // 8. 刷新主界面表格
            parentUI.refreshTable();

            // 9. 提示成功并关闭窗口
            JOptionPane.showMessageDialog(this, "员工添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "年龄和工资必须输入数字！", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}