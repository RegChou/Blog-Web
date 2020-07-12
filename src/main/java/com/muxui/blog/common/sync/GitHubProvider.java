package com.muxui.blog.common.sync;

import com.alibaba.fastjson.JSON;
import com.muxui.blog.service.auth.domain.vo.GithubVO;
import com.muxui.blog.service.auth.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ouyang
 * @title :
 * @description :
 * @createDate 2020/7/11
 */

@Component
public class GitHubProvider {
    private static final MediaType MediaType_JSON = MediaType.get("application/json; charset=utf-8");

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType_JSON, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resstring = response.body().string();
            String token =resstring.split("&")[0]
                    .split("=")[1];
            return token;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubVO getUser(String AccessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+AccessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return JSON.parseObject(res, GithubVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
