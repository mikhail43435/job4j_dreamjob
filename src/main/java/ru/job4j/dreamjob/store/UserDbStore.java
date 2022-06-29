package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.LoggerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDbStore {

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        String param = "INSERT INTO users(email, password) VALUES (?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(param, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
                result = Optional.of(user);
            }
        } catch (Exception e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    LoggerService.LOGGER.error("User exist exception in UserDbStore.add method   "
                            + " user data: email <" + user.getEmail() + ">");
                    return result;
                }
            }
            LoggerService.LOGGER.error("Exception in UserDbStore.add method", e);
        }
        return result;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE "
                     + "email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(new User(
                            it.getInt("id"),
                            it.getString("email"),
                            it.getString("password")
                    ));
                }
            }
        } catch (Exception e) {
            LoggerService.LOGGER.error("Exception in UserDbStore.findUserByEmailAndPwd method", e);
        }
        return result;
    }
}