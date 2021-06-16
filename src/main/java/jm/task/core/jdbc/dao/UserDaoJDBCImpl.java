package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util con = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(20), lastName VARCHAR(20), age TINYINT, PRIMARY KEY (id))";
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException{
        String sql = "DROP TABLE IF EXISTS user";
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException{
        String sql = "INSERT INTO user (name, lastName, age) Values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM user WHERE id ="+id;
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM user";
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = "DELETE FROM user";
        try (Connection connection = DriverManager.getConnection(con.getURL(), con.getUSERNAME(), con.getPASSWORD());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
