package com.itlab;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class BaseController {

    private final String COOKIE_NAME = "is_login";
    private final String NOT_LOGIN = "Chưa đăng nhập";
    private final String ERROR_USER = "Sai tài khoản";
    private final String KEY_USER = "KEY_USERNAME";
    private final String NOT_AUTH = "Không có quyền";
    private final Map<String, String> MAP_USER = new HashMap<>();

    public BaseController() {
        MAP_USER.put("user", "123");
    }

    @RequestMapping(value = {"/", "/login"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie==null||cookie.getValue()==null||cookie.getValue().equals("")){
            model.addAttribute("notLogin", NOT_LOGIN);
            return "login";
        }
        //auto login
        else {
            /*Cookie cookieLogged = WebUtils.getCookie(request, LOGGED_IN);
            if (cookieLogged==null||cookieLogged.getValue()==null||cookieLogged.getValue().equals("")){
                String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                JsonParser jsonParser = new JsonParser();
                Object obj = jsonParser.parse(cookieValue);
                log.info(obj.toString());
                JsonObject jsonObject = (JsonObject) obj;
                model.addAttribute("hasCookie", true);
                String user = removedSign(jsonObject.get(KEY_USER).toString());
                model.addAttribute("userName", user);
                model.addAttribute("password", MAP_USER.get(user));
                return "login";
            }
            else {
                return "redirect:/user";
            }*/
            return "redirect:/user";
        }

    }

    @RequestMapping(value = "/j_login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        if (!password.equals(MAP_USER.get(username))){
            model.addAttribute("errorUser", ERROR_USER);
            return "login";
        }

        //add cookie neu dang nhap thanh cong
        else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(KEY_USER, username);
            Cookie cookie = new Cookie(COOKIE_NAME, URLEncoder.encode( jsonObject.toString(), "UTF-8" ));
            response.addCookie(cookie);
            return "redirect:/user";
        }
    }
    @RequestMapping("/user")
    public String user(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
        //check cookie neu khong co thi ve trang login
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie==null||cookie.getValue()==null||cookie.getValue().equals("")){
            model.addAttribute("not_auth", NOT_AUTH);
            return "redirect:/login";
        }

        //neu co thi hien thi thong tin
        else{
            String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
            JsonParser jsonParser = new JsonParser();
            Object obj = jsonParser.parse(cookieValue);
            JsonObject jsonObject = (JsonObject) obj;
            model.addAttribute("userName",removedSign(jsonObject.get(KEY_USER).toString()));
            return "user";
        }
    }

    //loai bo dau nhay kep
    private static String removedSign(String s){
        s = s.substring(1, s.length()-1);
        return s;
    }
}
