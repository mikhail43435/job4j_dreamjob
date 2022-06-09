package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.PostStore;

import java.time.LocalDate;
import java.util.Collection;

@Service
@ThreadSafe
public class PostService {

    private final PostStore store;
    private final CityService cityService;

    public PostService(PostStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        post.setCreated(LocalDate.now());
        updateCity(post);
        store.add(post);
    }

    public void update(Post post) {
        post.setCreated(store.findById(post.getId()).getCreated());
        post.setUpdated(LocalDate.now());
        updateCity(post);
        store.update(post);
    }

    private void updateCity(Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
    }
}