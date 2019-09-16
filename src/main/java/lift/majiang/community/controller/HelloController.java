package lift.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建一个controller
 * 接受前端的请求
 */
@Controller
public class HelloController {
    @GetMapping("/hello")//获取参数的方法。和返回参的路径
    public String hello(@RequestParam(name="name") String name, Model model){
        model.addAttribute("name",name);

        return "hello";
    }
}
