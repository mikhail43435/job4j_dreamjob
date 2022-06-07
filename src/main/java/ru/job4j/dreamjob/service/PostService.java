package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.controller.PostController;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.PostStore;

import java.util.Collection;

public class PostService {

    private final PostStore store = PostStore.instOf();

    private PostService() {
    }

    private static class SingletonHolder {
        private static final PostService INST = new PostService();
    }

    public static PostService instanceOf() {
        return SingletonHolder.INST;
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}