package ru.job4j.dreamjob.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "clever", "2022/02/11"));
        candidates.put(2, new Candidate(2, "Petr", "nice looking", "2022/02/24"));
        candidates.put(3, new Candidate(3, "Jack", "rusty", "2022/03/23"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

}
