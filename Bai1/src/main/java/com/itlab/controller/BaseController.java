package com.itlab.controller;

import com.google.gson.Gson;
import com.itlab.model.Facebook;
import com.itlab.model.Google;
import com.itlab.service.FacebookService;
import com.itlab.service.GoogleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@Slf4j
public class BaseController {
    private final String COOKIE_NAME = "USER_INFO";
    private final String EMAIL_TOPICA = "@topica.edu.vn";
    private final String EMAIL_ERROR = "Email không hợp lệ";
    @Autowired
    FacebookService facebookService;
    @Autowired
    GoogleService googleService;
    @RequestMapping(value = {"/", "/login"})
    public String login(HttpServletRequest request, @RequestParam(required = false) String fail, Model model){
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie==null||cookie.getValue()==null||cookie.getValue().equals("")){
            log.info("No cookie");
            model.addAttribute("fail", fail);
            return "login";
        }
        else {
            log.info("Chuyen ve user info");
            return "redirect:/user";
        }
    }

    /*@RequestMapping("/login-google")
    public String loginGoogle(@RequestParam String code, Model model, HttpServletResponse response) throws IOException {
        if (code==null||code.equals("")){
            return "redirect:/login";
        }
        else {
            String accessToken = googleService.getToken(code);
            Google google = googleService.getUserInfo(accessToken);
            if (!google.getEmail().endsWith(EMAIL_TOPICA)){
                model.addAttribute("email", "khong hop le")
                log.info("Email khong hop le");
                return "redirect:/login";
            }
            else {
                return creatCookie(response, google);
            }
        }
    }*/
    @RequestMapping("/login-google")
    public ModelAndView loginGoogle(@RequestParam String code, ModelMap model, HttpServletResponse response) throws IOException {
        if (code==null||code.equals("")){
            return new ModelAndView("redirect:/login",  model);
        }
        else {
            String accessToken = googleService.getToken(code);
            Google google = googleService.getUserInfo(accessToken);
            if (!google.getEmail().endsWith(EMAIL_TOPICA)){
                model.addAttribute("fail", EMAIL_ERROR );
                log.info("Email khong hop le");
                return new ModelAndView("redirect:/login",  model);
            }
            else {
                return new ModelAndView(creatCookie(response,google),  model);

            }
        }
    }
    @RequestMapping("/login-facebook")
    public String loginFacebook(@RequestParam String code, Model model, HttpServletResponse response) throws IOException {
        if (code ==null||code.equals("")){
            return "redirect:/login";
        }

        else {
            String accessToken = facebookService.getToken(code);
            Facebook facebook = facebookService.getUserDetailsFromAccessToken(accessToken);
            /*if ((!facebook.getEmail().endsWith(EMAIL_TOPICA))){
                log.warn("Email khong hop le");
                return "redirect:/login";
            }
            else {
                return creatCookie(response, facebook);
            }*/
            return creatCookie(response, facebook);
        }
    }

    @RequestMapping("/user")
    public String user(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie==null||cookie.getValue()==null||cookie.getValue().equals("")){
            return "redirect:/login";
        }
        else {
            Gson gson = new Gson();
            String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
            Google google = gson.fromJson(cookieValue, Google.class);
            model.addAttribute("userInfo", google);
            return "user";
        }
    }

    private String creatCookie(HttpServletResponse response, Object object) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String cookieValue = URLEncoder.encode(gson.toJson(object), "UTF-8");
        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        response.addCookie(cookie);
        return "redirect:/user";
    }
}
