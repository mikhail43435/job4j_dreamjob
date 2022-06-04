package ru.job4j.dreamjob.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "great job", "2022/01/01"));
        posts.put(2, new Post(2, "Middle Java Job","wonderful job", "2022/02/04"));
        posts.put(3, new Post(3, "Senior Java Job","nice job", "2022/05/02"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public int getStoreSize() {
        return posts.size();
    }

    public void add(Post post) {
        posts.put(post.getId(), post);
    }
}