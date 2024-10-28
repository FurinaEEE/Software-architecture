package 二层CS结构;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private Connection connection;

    public ContactDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/contacts?serverTimezone=UTC";
            String user = "root"; // 替换为你的数据库用户名
            String password = "fjy94shabi"; // 替换为你的数据库密码
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 增加联系人
    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getAddress());
            stmt.setString(3, contact.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询所有联系人
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contact contact = new Contact(rs.getInt("id"), rs.getString("name"),
                        rs.getString("address"), rs.getString("phone"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // 修改联系人
    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name=?, address=?, phone=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getAddress());
            stmt.setString(3, contact.getPhone());
            stmt.setInt(4, contact.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除联系人
    public void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
