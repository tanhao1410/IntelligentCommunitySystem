package cn.practice.community.controller;

import cn.practice.community.dto.QuestionDTO;
import cn.practice.community.model.Question;
import cn.practice.community.model.User;
import cn.practice.community.service.QuestionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cn.practice.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by tanhao on 2020/5/2.
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("id", question.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model) {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);

        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setId(id);

        try {
            //TODO 问题属于哪个类别，需要从分类软件中获得
            String json = "{\"content\":\""+description+"\"}";
            Response response =  HttpUtils.post("http://localhost:8080/classification/textClassify",json);
            ResponseBody body = response.body();
            JSONObject obj = JSONObject.parseObject(body.string());

            //System.out.println(body.string());
            Set<String> keys = obj.keySet();
            for(String s :keys){
                question.setTag(s);
            }

        }catch (Exception e){

            e.printStackTrace();
        }

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
