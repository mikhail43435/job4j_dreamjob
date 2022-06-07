package ru.job4j.dreamjob.model;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private static final AtomicInteger CURRENT_ID = new AtomicInteger();

    private final Map<Integer, Post> store = new ConcurrentHashMap<>();

    private PostStore() {
        store.put(1, new Post(1, "Junior Java Job", "great job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        store.put(2, new Post(2, "Middle Java Job", "wonderful job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        store.put(3, new Post(3, "Senior Java Job", "nice job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        CURRENT_ID.set(3);
    }

    public Post findById(int id) {
        return store.get(id);
    }

    public Collection<Post> findAll() {
        return store.values();
    }

    public void add(Post post) {
        post.setId(CURRENT_ID.incrementAndGet());
        post.setCreated(LocalDate.now());
        store.put(post.getId(), post);
    }

    public void update(Post post) {
        Post postToUpdate = findById(post.getId());
        if (postToUpdate == null) {
            System.out.println("LOG: null post to update has been found");
            return;
        }
        postToUpdate.setName(post.getName());
        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setUpdated(LocalDate.now());
    }
}