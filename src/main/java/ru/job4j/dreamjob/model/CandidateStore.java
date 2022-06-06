package ru.job4j.dreamjob.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final AtomicInteger CURRENT_ID = new AtomicInteger();
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "clever", null));
        candidates.put(2, new Candidate(2, "Petr", "nice looking", null));
        candidates.put(3, new Candidate(3, "Jack", "rusty", null));
        CURRENT_ID.set(3);
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(CURRENT_ID.incrementAndGet());
        candidate.setCreated(LocalDate.now());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void updateCandidateNameAndDescription(Candidate candidate) {
        Candidate postToUpdate = findById(candidate.getId());
        if (postToUpdate == null) {
            System.out.println("LOG: null candidate to update has been found");
            return;
        }
        postToUpdate.setName(candidate.getName());
        postToUpdate.setDescription(candidate.getDescription());
    }

}
