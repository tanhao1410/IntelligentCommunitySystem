package cn.practice.community.controller;

import cn.practice.community.dto.PaginationDTO;
import cn.practice.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tanhao on 2020/4/24.
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "sort", required = false) String sort) {
        PaginationDTO pagination = questionService.list(search, sort, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        return "index";
    }
}
