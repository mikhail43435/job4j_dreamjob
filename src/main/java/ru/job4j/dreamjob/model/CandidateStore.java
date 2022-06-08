package ru.job4j.dreamjob.model;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class CandidateStore {

    private static final AtomicInteger CURRENT_ID = new AtomicInteger();
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> store = new ConcurrentHashMap<>();

    private CandidateStore() {
        store.put(1, new Candidate(1, "Ivan", "clever", null));
        store.put(2, new Candidate(2, "Petr", "nice looking", null));
        store.put(3, new Candidate(3, "Jack", "rusty", null));
        CURRENT_ID.set(3);
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(CURRENT_ID.incrementAndGet());
        candidate.setCreated(LocalDate.now());
        store.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return store.get(id);
    }

    public void update(Candidate candidate) {
        store.replace(candidate.getId(), candidate);
    }
}
