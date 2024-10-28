package 二层CS结构;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private ContactDAO contactDAO;
    private JTable contactTable;
    private ContactTableModel tableModel;

    public MainFrame() {
        contactDAO = new ContactDAO();
        setTitle("个人通讯录系统");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建表格
        tableModel = new ContactTableModel(contactDAO.getAllContacts());
        contactTable = new JTable(tableModel);

        // 创建按钮
        JButton addButton = new JButton("添加");
        JButton editButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");

        // 添加按钮的事件监听器
        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());

        // 布局
        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(new JScrollPane(contactTable), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    // 添加联系人
    private void addContact() {
        ContactForm form = new ContactForm(this, "添加联系人");
        form.setVisible(true);
        if (form.isSucceeded()) {
            contactDAO.addContact(form.getContact());
            refreshTable();
        }
    }

    // 修改联系人
    private void editContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow >= 0) {
            Contact contact = tableModel.getContactAt(selectedRow);
            ContactForm form = new ContactForm(this, "修改联系人", contact);
            form.setVisible(true);
            if (form.isSucceeded()) {
                contactDAO.updateContact(form.getContact());
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请选择要修改的联系人");
        }
    }

    // 删除联系人
    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = tableModel.getContactAt(selectedRow).getId();
            contactDAO.deleteContact(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "请选择要删除的联系人");
        }
    }

    // 刷新表格数据
    private void refreshTable() {
        tableModel.setContacts(contactDAO.getAllContacts());
        tableModel.fireTableDataChanged();
    }
}
