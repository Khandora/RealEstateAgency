package epam;

import epam.dao.Dao;
import epam.entity.User;
import epam.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

public class UserApplication {

    private static Dao<User> userDao;

    public static void main(String[] args) throws SQLException {
        userDao = new UserService();

        User user1 = getUser(0);
        System.out.println(user1);
        userDao.update(user1, new String[]{String.valueOf(1),"Jake", "jake@domain.com"});

        User user2 = getUser(1);
        try {
            userDao.delete(user2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userDao.save(new User(2,"Julie", "julie@domain.com"));

        try {
            userDao.getAll().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static User getUser(long id) {
        Optional<User> user = Optional.empty();
        try {
            user = userDao.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user.orElseGet(
                () -> new User(0,"non-existing user", "no-email"));
    }
}
