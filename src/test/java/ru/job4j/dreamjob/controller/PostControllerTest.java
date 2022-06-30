package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostControllerTest {

    private final Model model = mock(Model.class);
    private final CityService cityService = mock(CityService.class);
    private final PostService postService = mock(PostService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final List<City> cities;
    private final User user;
    private final Post post;

    public PostControllerTest() {
        this.user = new User(1, "new_user_email", "new_user_password");
        this.cities = Arrays.asList(
                new City(1, "<>"),
                new City(2, "Москва"),
                new City(3, "СПб"),
                new City(4, "Екб"));
        this.post = new Post(0, "Заполните поле");
    }

    @Test
    void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        when(postService.findAll()).thenReturn(posts);
        when(httpSession.getAttribute("user")).thenReturn(user);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, httpSession);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    void wheAddPost() {
        Post post = new Post(0, "Заполните поле");
        when(cityService.getAllCities()).thenReturn(cities);
        when(httpSession.getAttribute("user")).thenReturn(user);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, httpSession);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", post);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    void wheFormUpdatePost() {
        when(postService.findById(0)).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(cities);
        when(httpSession.getAttribute("user")).thenReturn(user);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, 0, httpSession);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("updatePost");
    }

    @Test
    void wheUpdatePost() {
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}