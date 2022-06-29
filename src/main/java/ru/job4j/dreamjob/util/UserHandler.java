package ru.job4j.dreamjob.util;

import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public class UserHandler {

    private UserHandler() {
    }

    public static User handleUserOfCurrentSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User(0, "Гость", "");
            user.setEmail("Гость");
        }
        return user;
    }
}