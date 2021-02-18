package cn.practice.community.controller;

import cn.practice.community.dto.CommentDTO;
import cn.practice.community.dto.QuestionDTO;
import cn.practice.community.enums.CommentTypeEnum;
import cn.practice.community.exception.CustomizeErrorCode;
import cn.practice.community.exception.CustomizeException;
import cn.practice.community.service.CommentService;
import cn.practice.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by tanhao on 2020/5/21.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }

        QuestionDTO questionDTO = questionService.getById(questionId);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        //所有的问题回复
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
