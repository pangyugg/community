package lift.majiang.community.provider;


import com.alibaba.fastjson.JSON;
import lift.majiang.community.dto.AccessTokenDTO;
import lift.majiang.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//componpent的作用自动加入spring的上下文。不需要new 就可以拿到对象
@Component
public class GitHubProvider {
    //okhttp3框架构建的模拟post方法。
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String[] split = string.split("&");
            String token = split[0].split("=")[1];
            System.out.println(string);
            return token ;
        }catch (Exception e){

        }
        return null;
    }
    ////okhttp3框架构建的模拟get方法。
    public GitHubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {//try-catch 的快捷键
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            //使用json把字符串对象自动转换成Java的一个类对象。
            GitHubUser gitHubUser= JSON.parseObject(string,GitHubUser.class);
            return gitHubUser;
        }catch (IOException e){

        }


        return null;

    }
}
