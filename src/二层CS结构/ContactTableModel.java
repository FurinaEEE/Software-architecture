package 二层CS结构;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContactTableModel extends AbstractTableModel {
    private List<Contact> contacts;
    private final String[] columnNames = {"ID", "姓名", "住址", "电话"};

    public ContactTableModel(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getRowCount() {
        return contacts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contact contact = contacts.get(rowIndex);
        switch (columnIndex) {
            case 0: return contact.getId();
            case 1: return contact.getName();
            case 2: return contact.getAddress();
            case 3: return contact.getPhone();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Contact getContactAt(int rowIndex) {
        return contacts.get(rowIndex);
    }
}

