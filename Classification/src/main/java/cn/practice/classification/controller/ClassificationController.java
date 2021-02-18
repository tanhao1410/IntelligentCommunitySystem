package cn.practice.classification.controller;

import cn.practice.classification.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Autowired
    private ClassificationService service;

    @RequestMapping(value="/textClassify")
    public Map textClassify(@RequestBody Map<String,String> content){
        return service.textClassify(content.get("content"));
    }
}
