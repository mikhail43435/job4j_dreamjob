package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;

import static ru.job4j.dreamjob.util.UserHandler.handleUserOfCurrentSession;

@Controller
@ThreadSafe
public class IndexControl {

    private final PostService postService;

    public IndexControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("user", handleUserOfCurrentSession(session));
        return "index";
    }
}