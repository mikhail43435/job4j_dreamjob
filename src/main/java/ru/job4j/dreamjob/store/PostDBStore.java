package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;
    private final CityService cityService;

    public PostDBStore(BasicDataSource pool, CityService cityService) {
        this.pool = pool;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("date_created", LocalDate.class),
                            it.getObject("date_updated", LocalDate.class),
                            it.getBoolean("is_published"),
                            cityService.findById(it.getInt("city"))
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        String param = "INSERT INTO post(name, city, description,"
                + " is_published, date_created, date_updated) VALUES (?,?,?,?,?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(param, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setBoolean(4, post.isVisible());
            ps.setObject(5, post.getCreated());
            ps.setObject(6, post.getUpdated());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        String param = "UPDATE post SET name = ?, city = ?, description = ?,"
                + " is_published = ?, date_created = ?, date_updated = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(param)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setBoolean(4, post.isVisible());
            ps.setObject(5, post.getCreated());
            ps.setObject(6, post.getUpdated());
            ps.setInt(7, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("date_created", LocalDate.class),
                            it.getObject("date_updated", LocalDate.class),
                            it.getBoolean("is_published"),
                            cityService.findById(it.getInt("city"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}