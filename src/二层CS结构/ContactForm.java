package 二层CS结构;

import javax.swing.*;
import java.awt.*;

public class ContactForm extends JDialog {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private boolean succeeded;
    private Contact contact;

    public ContactForm(Frame parent, String title) {
        super(parent, title, true);
        initUI(null);
    }

    public ContactForm(Frame parent, String title, Contact contact) {
        super(parent, title, true);
        initUI(contact);
    }

    private void initUI(Contact contact) {
        this.contact = contact;

        nameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);

        if (contact != null) {
            nameField.setText(contact.getName());
            addressField.setText(contact.getAddress());
            phoneField.setText(contact.getPhone());
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("住址:"));
        panel.add(addressField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);

        JButton okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        okButton.addActionListener(e -> {
            this.contact = new Contact(
                    contact == null ? 0 : contact.getId(),
                    nameField.getText(),
                    addressField.getText(),
                    phoneField.getText()
            );
            succeeded = true;
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Contact getContact() {
        return contact;
    }
}
