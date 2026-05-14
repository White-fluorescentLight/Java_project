package com.employee.ui;

import com.employee.bean.Employee;
import com.employee.bean.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 员工信息管理主界面
 * 功能：
 * 1. 左上角显示当前登录用户
 * 2. 支持打开添加员工窗口
 * 3. 支持右键修改/删除员工
 * 4. 表格数据与集合实时同步
 */
public class EmployeeManagerUI extends JFrame {
    private JTable table;                     // 员工表格
    private DefaultTableModel model;          // 表格数据模型
    private JTextField textFieldSearch;       // 搜索输入框
    private JPopupMenu popupMenu;             // 右键菜单
    private User currentUser;                 // 当前登录的用户

    // 核心数据集合：存储系统中所有员工对象（初始为空）
    private ArrayList<Employee> employees = new ArrayList<>();

    /**
     * 带登录用户参数的构造方法（登录成功后调用）
     * @param user 当前登录的用户对象
     */
    public EmployeeManagerUI(User user) {
        super("员工信息管理系统");
        this.currentUser = user;
        initFrame();       // 初始化窗口
        initData();        // 初始化数据（已清空）
        createAndShowGUI();// 创建并显示界面
    }

    /**
     * 无参构造方法（仅测试用）
     */
    public EmployeeManagerUI() {
        super("员工信息管理系统");
        initFrame();
        initData();
        createAndShowGUI();
    }

    /**
     * 初始化窗口基本属性
     */
    private void initFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 800, 600);
        this.setLocationRelativeTo(null); // 居中显示
        this.setLayout(null);              // 绝对布局
    }

    /**
     * 初始化数据（已删除所有测试数据）
     */
    private void initData() {
        // ✅ 已删除所有模拟员工数据
        // 如需添加初始数据，可在此处手动添加：
        // employees.add(new Employee(1, "张三", "男", 25, "13800000001", "经理", "2023-01-01", 8000, "技术部"));
    }

    /**
     * 创建并显示所有UI组件
     */
    private void createAndShowGUI() {
        // 添加顶部面板
        this.add(createTopPanel());
        // 添加表格面板
        this.add(createTablePanel());
        // 初始刷新表格
        refreshTable();
        // 显示窗口
        this.setVisible(true);
    }

    /**
     * 创建顶部面板（包含用户信息、搜索框、添加按钮）
     * @return 顶部面板
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 800, 70);
        topPanel.setBackground(new Color(245, 248, 255));

        // 1. 左上角显示当前登录用户
        if (currentUser != null) {
            JLabel userLabel = new JLabel("当前登录：" + currentUser.getUsername());
            userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            userLabel.setForeground(new Color(100, 100, 100));
            userLabel.setBounds(20, 20, 200, 25);
            topPanel.add(userLabel);
        }

        // 2. 搜索输入框
        textFieldSearch = new JTextField();
        textFieldSearch.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textFieldSearch.setBounds(200, 20, 200, 30);
        textFieldSearch.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        topPanel.add(textFieldSearch);

        // 3. 搜索按钮
        JButton btnSearch = new JButton("搜索");
        btnSearch.setFont(new Font("微软雅黑", Font.BOLD, 14));
        btnSearch.setBounds(420, 20, 80, 30);
        btnSearch.setBackground(new Color(47, 128, 237));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        topPanel.add(btnSearch);

        // 4. 添加按钮
        JButton btnAdd = new JButton("添加");
        btnAdd.setFont(new Font("微软雅黑", Font.BOLD, 14));
        btnAdd.setBounds(520, 20, 80, 30);
        btnAdd.setBackground(new Color(79, 79, 237));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        topPanel.add(btnAdd);
        // 绑定添加事件：打开独立的添加窗口
        btnAdd.addActionListener(e -> new AddEmployeeUI(this));

        return topPanel;
    }

    /**
     * 创建表格面板（包含员工表格和右键菜单）
     * @return 表格面板
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(0, 70, 800, 530);

        // 1. 定义表格列名
        String[] columns = {"ID", "姓名", "性别", "年龄", "入职日期", "工资", "部门", "职位", "电话"};

        // 2. 创建不可编辑的表格模型
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 禁止单元格直接编辑
            }
        };

        // 3. 创建表格
        table = new JTable(model);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(60, 140, 255));
        table.setSelectionForeground(Color.WHITE);

        // 4. 将表格放入滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 780, 510);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tablePanel.add(scrollPane);

        // 5. 初始化右键菜单并绑定鼠标事件
        createPopupMenu();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 右键点击时显示菜单
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table.setRowSelectionInterval(row, row); // 选中当前行
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        return tablePanel;
    }

    /**
     * 初始化右键菜单（修改、删除）
     */
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        // 1. 修改菜单项
        JMenuItem editItem = new JMenuItem("修改");
        editItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editItem.addActionListener(e -> handleEdit());
        popupMenu.add(editItem);

        // 2. 删除菜单项
        JMenuItem deleteItem = new JMenuItem("删除");
        deleteItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteItem.addActionListener(e -> handleDelete());
        popupMenu.add(deleteItem);
    }

    /**
     * 处理修改员工操作
     */
    private void handleEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;

        // 获取选中行的员工ID
        int id = (int) model.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this,
                "准备修改员工: ID=" + id,
                "修改员工",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 处理删除员工操作：从集合中移除 -> 刷新表格
     */
    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;

        // 获取选中行的员工ID
        int id = (int) model.getValueAt(selectedRow, 0);

        // 弹出确认对话框
        int result = JOptionPane.showConfirmDialog(this,
                "确定要删除员工 ID: " + id + " 吗？",
                "删除确认",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // 1. 从集合中删除对应索引的员工
            employees.remove(selectedRow);
            // 2. 刷新表格显示
            refreshTable();
            // 3. 提示成功
            JOptionPane.showMessageDialog(this,
                    "员工 ID: " + id + " 已成功删除",
                    "删除成功",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 刷新表格数据：将集合中的数据同步到表格
     * 注意：必须是 public，供 AddEmployeeUI 调用
     */
    public void refreshTable() {
        // 清空表格现有行
        model.setRowCount(0);
        // 遍历集合，将每个员工对象添加到表格
        for (Employee emp : employees) {
            model.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    emp.getSex(),
                    emp.getAge(),
                    emp.getEntryDate(),
                    emp.getSalary(),
                    emp.getDepartment(),
                    emp.getPosition(),
                    emp.getPhone()
            });
        }
    }

    /**
     * 获取员工集合
     * 注意：必须是 public，供 AddEmployeeUI 调用
     * @return 员工集合
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * 程序入口（仅测试主界面用）
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagerUI());
    }
}