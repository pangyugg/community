package lift.majiang.community.controller;

import lift.majiang.community.dto.AccessTokenDTO;
import lift.majiang.community.dto.GitHubUser;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.model.User;
import lift.majiang.community.provider.GitHubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 不要过早的把什么东西都加上
 * 后面缺什么，再慢慢加上去。
 */
@Controller
public class AuthorizeController {
    //跟去spring的依赖注入值 记住是￥$符号不能是&符号
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect.uri}")
    private String clientRedirect;

    @Value("${git.client.secret}")
    private String clientSecret;

    //自动实例化。
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        //的@RequestParme  是自带的对象吗？通过github的api返回的对象取值的
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirect);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //ctrl+alt+t是try -catch 的快捷键。
        try {
            String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
            GitHubUser gitHubUser = gitHubProvider.getuser(accessToken);
            if(gitHubUser!=null){
                //登录成功，写cookie和session，并且写入数据库；服务器断点，session对象将会被清空。
                //直接写，直接拿。
                User user=new User();
                user.setToken(UUID.randomUUID().toString());//随机生成UUid
                user.setAccountId(String.valueOf(gitHubUser.getId()));//将用户id改成AccountId
                user.setName(gitHubUser.getName());
                user.setGmtCreate(System.currentTimeMillis());//拿到当前的时间
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
                request.getSession().setAttribute("user",gitHubUser);
                response.addCookie(new Cookie("token",user.getToken()));
                return "redirect:/";//重定向
            }else {
                //登录失败！
                return "redirect:/";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "redirect:/";
    }
}
