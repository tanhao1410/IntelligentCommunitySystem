package cn.practice.community.controller;

import cn.practice.community.model.User;
import cn.practice.community.service.FileService;
import cn.practice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @PostMapping("/user")
    public String upload(@RequestParam(name = "userName") String userName,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "password2") String password2,
                         @RequestParam("file") MultipartFile file,
                                     Model model) {
        //检验文件
        if (file.isEmpty()) {
            //增加提示信息
            model.addAttribute("message","未选择头像");
            return "regist";
        }
        String url = fileService.saveAvatar(file);

        if(!password.equals(password2)){
            model.addAttribute("message","密码不相同");
            //提示
            return "regist";
        }

        User oldUser = userService.getUserByUserName(userName);
        if(oldUser != null){
            //提示信息
            model.addAttribute("message","用户名已存在");
            return "regist";
        }
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setAvatarUrl(url);

        userService.createOrUpdate(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "userName") String userName,
                        @RequestParam(name = "password") String password,
                        Model model,
                        HttpServletResponse response) {

        User user =  userService.getUserByUserName(userName);
        if(null == user){
            //用户名输入错误
            model.addAttribute("message","用户不存在");
            return "login";
        }else{
            if(user.getPassword().equals(password)){
                //登录成功需要将信息写入session
                String token = user.getName();
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
                response.addCookie(cookie);
                return "redirect:/";
            }else{
                //密码输入错误
                model.addAttribute("message","密码错误");
                return "login";
            }
        }
    }


}
