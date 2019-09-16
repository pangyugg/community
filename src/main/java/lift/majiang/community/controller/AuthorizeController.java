package lift.majiang.community.controller;

import lift.majiang.community.dto.AccessTokenDTO;
import lift.majiang.community.dto.GitHubUser;
import lift.majiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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
    @GetMapping("callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirect);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
       // accessTokenDTO.setClient_id("5ba947cbe7e214327b60");
        //accessTokenDTO.setClient_secret("66e72a0c0147e7f04ae9b21c2365450f24c44055");
        //ctrl+alt+t是try -catch 的快捷键。
        try {
            String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
            GitHubUser user = gitHubProvider.getuser(accessToken);
            System.out.println(user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "index";
    }
}
