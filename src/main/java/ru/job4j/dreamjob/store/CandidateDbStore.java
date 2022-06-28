package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.LoggerService;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("date_created", LocalDate.class),
                            it.getBytes("photo_data")
                    ));
                }
            }
        } catch (Exception e) {
            LoggerService.LOGGER.error("Exception in CandidateDbStore.findAll method", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        String param = "INSERT INTO candidate(name, description,"
                + " date_created, photo_data) VALUES (?,?,?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(param, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setObject(3, LocalDate.now());
            ps.setBinaryStream(4, new ByteArrayInputStream(candidate.getPhoto()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LoggerService.LOGGER.error("Exception in CandidateDbStore.add method", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        String param = "UPDATE candidate SET name = ?, description = ?,"
                + " date_created = ?, photo_data = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(param)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setObject(3, candidate.getCreated());
            ps.setBinaryStream(4, new ByteArrayInputStream(candidate.getPhoto()));
            ps.setObject(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("date_created", LocalDate.class),
                            it.getBytes("photo_data")
                    );
                }
            }
        } catch (Exception e) {
            LoggerService.LOGGER.error("Exception in CandidateDbStore.findById method", e);
        }
        return null;
    }
}