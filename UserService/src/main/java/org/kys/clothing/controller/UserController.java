package org.kys.clothing.controller;

import org.kys.clothing.service.UserService;
import org.kys.clothing.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    private static final long DAY_TIME = 86400000;
    @Autowired
    RedisTemplate<String,UserBean> userRedisTemplate;

    @Autowired
    UserService userService;

    @RequestMapping("user_login")
    public boolean UserLogin(@RequestParam("user_code")String code,
                            @RequestParam("password")String passwordsa,HttpServletResponse response){
        UserBean userBean = userService.getUserInfo(code);
        if(passwordsa.equals(userBean.getUserPassword())){
            if (userRedisTemplate.hasKey("login_information_"+code)==Boolean.FALSE) {
                Cookie cookie = new Cookie("login_information", "login_information_" + code);
                response.addCookie(cookie);
                ValueOperations<String,UserBean> cacheValue = userRedisTemplate.opsForValue();
                cacheValue.set("login_information_" + code,userBean,DAY_TIME*7,TimeUnit.MICROSECONDS);
            }
            return true;
        }
        return false;
    }

    @RequestMapping("get_user_info")
    public UserBean getUserInfo(@RequestParam("user_code") String userCode){
        return userService.getUserInfo(userCode);
    }

    @RequestMapping("register")
    public boolean regiterUser(@ModelAttribute UserBean userBean){
        boolean isRegister = userService.isRegister(userBean.getUserCode());
        if (isRegister){
            return false;
        }
        userBean.setStatus(1);
        return userService.insertUserBean(userBean);
    }

    @RequestMapping("is_login")
    public UserBean isLogin(@CookieValue(value = "login_information") String httpCookie){
        ValueOperations<String,UserBean> valueOperations=userRedisTemplate.opsForValue();
        UserBean userBean=valueOperations.get(httpCookie);
        return userBean;
    }
}