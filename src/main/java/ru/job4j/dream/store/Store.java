package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {

    private static AtomicInteger POST_ID = new AtomicInteger(4);

    private static final Store INST = new Store();

    private final Map<Integer, Post> postsMap = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidatesMap = new ConcurrentHashMap<>();

    private Store() {
        postsMap.put(1, new Post(1, "Junior Java Job"));
        postsMap.put(2, new Post(2, "Middle Java Job"));
        postsMap.put(3, new Post(3, "Senior Java Job"));
        candidatesMap.put(1, new Candidate(1, "Junior Java"));
        candidatesMap.put(2, new Candidate(2, "Middle Java"));
        candidatesMap.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return postsMap.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidatesMap.values();
    }

    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        postsMap.put(post.getId(), post);
    }

    public Post findById(int id) {
        return postsMap.get(id);
    }

    public void saveCandidate(Candidate candidate) {
        candidate.setId(POST_ID.incrementAndGet());
        candidatesMap.put(candidate.getId(), candidate);
    }
}