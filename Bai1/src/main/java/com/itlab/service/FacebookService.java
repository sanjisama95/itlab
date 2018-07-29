package com.itlab.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlab.model.Facebook;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class FacebookService {
    @Autowired
    private Environment env;
    private RestTemplate restTemplate = new RestTemplate();

    //get token from code
    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = env.getProperty("facebook.link.get.token");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", env.getProperty("facebook.client.id"))
                        .add("client_secret", env.getProperty("facebook.client.secret"))
                        .add("redirect_uri", env.getProperty("facebook.redirect.uri"))
                        .add("code", code)
                        .build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    //get userinfo
    public Facebook getUserDetailsFromAccessToken(String accessToken) throws IOException {
        String fields = "id,name,email";
        String link = env.getProperty("facebook.link.get.user_info");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("access_token", accessToken).add("fields", fields).build())
                .execute().returnContent().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        Facebook facebook = objectMapper.readValue(response,Facebook.class);
        log.info(facebook.toString());
        return facebook;
    }

}
