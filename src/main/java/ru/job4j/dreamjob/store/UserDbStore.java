package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.LoggerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserDbStore {

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
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
            }
        } catch (Exception e) {
            LoggerService.LOGGER.error("Exception in UserDbStore.add method", e);
        }
        return user.getId() == 0 ? Optional.empty() : Optional.of(user);
    }
}