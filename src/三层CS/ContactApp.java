package 三层CS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;



public class ContactApp {
    private JFrame frame;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField idField;
    private JTextArea contactsArea;
    private ContactManager manager;


    public ContactApp() {
        manager = new ContactManager();
        initializeUI();
        loadContacts();
    }

    private void initializeUI() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(22, 20, 46, 14);
        frame.getContentPane().add(idLabel);

        idField = new JTextField();
        idField.setBounds(94, 17, 86, 20);
        frame.getContentPane().add(idField);
        idField.setColumns(10);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(22, 46, 46, 14);
        frame.getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(94, 43, 86, 20);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JLabel addressLabel = new JLabel("住址:");
        addressLabel.setBounds(22, 72, 46, 14);
        frame.getContentPane().add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(94, 69, 86, 20);
        frame.getContentPane().add(addressField);
        addressField.setColumns(10);

        JLabel phoneLabel = new JLabel("电话:");
        phoneLabel.setBounds(22, 98, 46, 14);
        frame.getContentPane().add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(94, 95, 86, 20);
        frame.getContentPane().add(phoneField);
        phoneField.setColumns(10);



        JButton addButton = new JButton("增加联系人");
        addButton.setBounds(22, 132, 120, 23);
        frame.getContentPane().add(addButton);

        JButton updateButton = new JButton("修改联系人");
        updateButton.setBounds(147, 132, 120, 23);
        frame.getContentPane().add(updateButton);

        JButton deleteButton = new JButton("删除联系人");
        deleteButton.setBounds(272, 132, 120, 23);
        frame.getContentPane().add(deleteButton);

        JButton getButton = new JButton("查询联系人");
        getButton.setBounds(397, 132, 120, 23);
        frame.getContentPane().add(getButton);

        contactsArea = new JTextArea();
        contactsArea.setBounds(10, 165, 570, 126);
        frame.getContentPane().add(contactsArea);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        getButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContact();
            }
        });

        frame.setVisible(true);
    }

    private void loadContacts() {
        ResultSet rs = manager.getAllContacts();
        try {
            contactsArea.setText("");
            while (rs.next()) {
                contactsArea.append(rs.getString("id") + " - " + rs.getString("姓名") + " - " + rs.getString("住址") + " - " + rs.getString("电话") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addContact() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        boolean success = manager.addContact(name, address, phone);
        if (success) {
            contactsArea.append("增加联系人: " + name + "\n");
            clearFields();
            loadContacts();
        } else {
            contactsArea.append("增加联系人失败\n");
        }
    }

    private void updateContact() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        int id = Integer.parseInt(idField.getText());
        boolean success = manager.updateContact(id, name, address, phone);
        if (success) {
            contactsArea.append("成功修改\n");
            clearFields();
            loadContacts();
        } else {
            contactsArea.append("修改联系人失败\n");
        }
    }

    private void deleteContact() {
        int id = Integer.parseInt(idField.getText());
        boolean success = manager.deleteContact(id);
        if (success) {
            contactsArea.append("成功删除\n");
            loadContacts();
        } else {
            contactsArea.append("删除失败\n");
        }
    }

    private void getContact() {
        int id = Integer.parseInt(idField.getText());
        Contact contact = manager.getContact(id);
        if (contact != null) {
            contactsArea.setText(contact.toString());
        } else {
            contactsArea.setText("查询失败\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        idField.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ContactApp window = new ContactApp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}