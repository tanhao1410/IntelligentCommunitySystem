package cn.practice.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageCotroller {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request,
                            HttpServletResponse response) {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request,
                               HttpServletResponse response) {
        return "regist";
    }

}
