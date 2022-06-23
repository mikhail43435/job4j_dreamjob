package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.service.PostService;

@Controller
@ThreadSafe
public class IndexControl {

    private final PostService postService;

    public IndexControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "index";
    }
}