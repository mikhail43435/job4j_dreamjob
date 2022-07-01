package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostDBStoreTest {

    @Test
    void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool(), new CityService());
        Post post = new Post(0, "Junior Java Job", "great job",
                LocalDate.of(2022, Month.JANUARY, 1),
                null, false, new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
        assertThat(postInDb.getDescription()).isEqualTo(post.getDescription());
        assertThat(postInDb.getCreated()).isEqualTo(post.getCreated());
        assertThat(postInDb.getUpdated()).isEqualTo(post.getUpdated());
        assertThat(postInDb.getCity()).isEqualTo(post.getCity());
    }

    @Test
    void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool(), new CityService());
        Post post1 = new Post(0, "Junior Java Job", "great job",
                LocalDate.of(2022, Month.JANUARY, 1),
                null, false, new City(1, "Москва"));
        int addedPostId = (store.add(post1).getId());
        Post post2 = new Post(addedPostId, "Junior Java Job2", "great job2",
                LocalDate.of(2021, Month.JANUARY, 1),
                null, true, new City(2, "СПб"));
        store.update(post2);
        Post postInDb = store.findById(post1.getId());
        assertThat(postInDb.getName()).isEqualTo(post2.getName());
        assertThat(postInDb.getDescription()).isEqualTo(post2.getDescription());
        assertThat(postInDb.getCreated()).isEqualTo(post2.getCreated());
        assertThat(postInDb.getUpdated()).isEqualTo(post2.getUpdated());
        assertThat(postInDb.getCity()).isEqualTo(post2.getCity());
    }
}