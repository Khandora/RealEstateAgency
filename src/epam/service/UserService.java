package epam.service;

import epam.connection.Util;
import epam.dao.Dao;
import epam.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService extends Util implements Dao<User> {
    private Connection connection = getConnection();
    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User(1,"John", "john@domain.com"));
        users.add(new User(2,"Susan", "susan@domain.com"));
    }

    @Override
    public Optional<User> get(long id) throws SQLException {

        String sql = "SELECT ID, NAME, EMAIL FROM USER WHERE ID=?";

        User user = new User();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("NAME"));
            user.setEmail(resultSet.getString("EMAIL"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT NAME, EMAIL FROM ADDRESS";

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("NAME"));
                user.setEmail(resultSet.getString("EMAIL"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return userList;
    }

    private void callPreparedStatement(User user, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void save(User user) throws SQLException {

        String sql = "INSERT INTO USER(ID, NAME , EMAIL) VALUES(?, ?, ?)";

        callPreparedStatement(user, sql);
    }

    @Override
    public void update(User user,String[] params) throws SQLException {

        String sql = "UPDATE USER SET NAME=?, EMAIL=? WHERE ID=?";

        callPreparedStatement(user, sql);
    }

    @Override
    public void delete(User user) throws SQLException {

        String sql = "DELETE FROM USER WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
