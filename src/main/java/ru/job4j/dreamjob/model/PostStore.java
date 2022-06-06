package ru.job4j.dreamjob.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final AtomicInteger CURRENT_ID = new AtomicInteger();
    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "great job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        posts.put(2, new Post(2, "Middle Java Job", "wonderful job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        posts.put(3, new Post(3, "Senior Java Job", "nice job",
                LocalDate.of(2022, Month.JANUARY, 1), null));
        CURRENT_ID.set(3);
    }

    public static PostStore instOf() {
        return INST;
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(CURRENT_ID.incrementAndGet());
        post.setCreated(LocalDate.now());
        posts.put(post.getId(), post);
    }

    public void updatePostName(Post post) {
        Post postToUpdate = findById(post.getId());
        postToUpdate.setName(post.getName());
        postToUpdate.setUpdated(LocalDate.now());
    }
}