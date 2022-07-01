package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CandidateDbStoreTest {

    @Test
    void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan", "clever",
                LocalDate.now(), "DATA".getBytes());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
        assertThat(candidateInDb.getDescription()).isEqualTo(candidate.getDescription());
        assertThat(candidateInDb.getCreated()).isEqualTo(candidate.getCreated());
        assertThat(candidateInDb.getPhoto()).isEqualTo(candidate.getPhoto());
    }

    @Test
    void whenUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan", "clever",
                LocalDate.now(), "DATA".getBytes());
        int addedCandidateId = store.add(candidate).getId();
        Candidate candidate2 = new Candidate(addedCandidateId, "Ivan2", "clever2",
                LocalDate.now(), "DATA2".getBytes());
        store.update(candidate2);
        Candidate candiateInDb = store.findById(candidate2.getId());
        assertThat(candiateInDb.getName()).isEqualTo(candidate2.getName());
        assertThat(candiateInDb.getDescription()).isEqualTo(candidate2.getDescription());
        assertThat(candiateInDb.getCreated()).isEqualTo(candidate2.getCreated());
        assertThat(candiateInDb.getPhoto()).isEqualTo(candidate2.getPhoto());
    }
}