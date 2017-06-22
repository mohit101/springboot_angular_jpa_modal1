/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.controller;

//import static com.websystique.springboot.controller.TestAuthController.AUTH_TOKEN;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mohitm@bdcvit.com
 */
@RestController
@RequestMapping("/api2")
public class TestCookies {

//    @RequestMapping(value = "/getCookie/", method = RequestMethod.POST)
//    public ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Cookie cookie = new Cookie("hitCounter", "hello");
//        response.addCookie(cookie);
//
//        Cookie[] cookies1 = request.getCookies();
//        if (cookies1 != null) {
//            for (Cookie cookie1 : cookies1) {
//                System.out.println("cookie" + cookie1.getValue());
//            }
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
    
   @RequestMapping(value = "/getCookie")
    public String hello(
          HttpServletRequest request,  HttpServletResponse response) {

        // increment hit counter

        // create cookie and set it in response
        Cookie cookie = new Cookie("hitCounter", "hello");
        response.addCookie(cookie);
        
         Cookie[] cookies1 = request.getCookies();
        if (cookies1 != null) {
            for (Cookie cookie1 : cookies1) {
                System.out.println("cookie" + cookie1.getValue());
            }
        }
        

        // render hello.jsp page
        return "hello";
    }

}
