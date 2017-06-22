/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.controller;

import javax.servlet.http.*;
import com.websystique.springboot.model.User;
import com.websystique.springboot.mysql.ervice.impl.MysqlServiceImpl;
import com.websystique.springboot.service.UserService;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author mohitm@bdcvit.com
 */
@CrossOrigin(origins = "http://localhost:8070")
@RestController
@RequestMapping("/api2")
@SessionAttributes("username")
public class TestAuthController {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    @Autowired
    UserService userService; //Service which will do all data retrieval/manipulation work

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (user.getUname() != null && user.getUpass() != null) {
            HttpSession session = request.getSession(true);

            session.setAttribute("username", user.getUname());
            String id = session.getId();
            authentication(user);

            String sueToken = issueToken(user.getUname());
            System.out.println("fist" + session.getId());
            System.out.println("second" + session.getId());

            setCookie(response);
            getCookie(request);

//            Cookie cookie = new Cookie("hitCounter", "hello");
//            response.addCookie(cookie);
//            response.setHeader(AUTH_TOKEN, "hellonew");
//            String header = request.getHeader(AUTH_TOKEN);
//
//            Cookie[] cookies1 = request.getCookies();
//            if (cookies1 != null) {
//                for (Cookie cookie1 : cookies1) {
//                    System.out.println("cookie" + cookie1.getValue());
//                }
//            }
            String authToken = request.getHeader(AUTH_TOKEN_HEADER);
            System.out.println("return auth token from header" + authToken);
            MysqlServiceImpl mysqlServiceImpl = new MysqlServiceImpl();
            mysqlServiceImpl.connectionMysql();

            QueryRunner run = new QueryRunner();
            ResultSetHandler<User> h = new BeanListHandler(User.class);
            User query = run.query("select * from user", h);

            ResultSet executeMysqlQuery1 = mysqlServiceImpl.executeMysqlQuery("select * from user where username='" + user.getUname() + "' && password='" + user.getUpass() + "' ");
            if (executeMysqlQuery1 != null) {
                while (executeMysqlQuery1.next()) {
                    user.setUname(executeMysqlQuery1.getString("username"));
                    user.setEmail(executeMysqlQuery1.getString("email"));
                    user.setUpass(executeMysqlQuery1.getString("password"));
                }
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            System.out.println("Username and password not entered");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/getUser/", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session = request.getSession(false);
//        String uname = (String) session.getAttribute("username");
        String sueToken = issueToken("suresh");

        System.out.println("third" + sueToken);

        Cookie cookie = new Cookie("hitCounter", "hello");
        response.addCookie(cookie);

        Cookie[] cookies1 = request.getCookies();
        if (cookies1 != null) {
            for (Cookie cookie1 : cookies1) {
                System.out.println("cookie" + cookie1.getValue());
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/registerUser/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletRequest request) {
        System.out.println("okiiii222");
        String sueToken = issueToken("suresh");
        Cookie[] cookies1 = request.getCookies();
        if (cookies1 != null) {
            for (Cookie cookie1 : cookies1) {
                System.out.println("cookie" + cookie1.getValue());
            }
        }
        String authToken = request.getHeader(AUTH_TOKEN_HEADER);
        System.out.println("return auth toke1n from header" + authToken);

        String valueFromCookie = getValueFromCookie(request);
        System.out.println("rget valu from cookies" + valueFromCookie);

        System.out.println(user.getEmail());
        MysqlServiceImpl mysqlServiceImpl = new MysqlServiceImpl();
        mysqlServiceImpl.connectionMysql();
        System.out.println("Testing create User API----------");
        Boolean executeMysqlQuery = mysqlServiceImpl.executeMysqlQuery1("insert into user (username,email,password,active)" + "values ('" + user.getUname() + "','" + user.getEmail() + "','" + user.getUpass() + "',1)");
        ResultSet executeMysqlQuery1 = mysqlServiceImpl.executeMysqlQuery("select * from user");
        System.err.println("yes" + executeMysqlQuery1);
        System.err.println("No" + executeMysqlQuery);
        logger.info("Creating User : {}", user.getName());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout/", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("okiiii333");
        String sueToken = issueToken("suresh");
        Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public void authentication(User user) {

    }

    public String issueToken(String uname) {
        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(uname).append(":");
        tokenBuilder.append("60000").append(":");
        tokenBuilder.append(computeSignature(uname, 60000));
//        String authToken = sessionId + ":" + Integer.toHexString(Integer.parseInt(uname));
        System.out.println("" + encrypt(tokenBuilder.toString()));
        return encrypt(tokenBuilder.toString());
    }

    public String computeSignature(String uname, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(uname).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append("3l8wOib86LJX644n");
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(digest.digest(signatureBuilder.toString().getBytes()));
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, generateKey());
            String charSet = "UTF-8";
            byte[] in = plainText.getBytes(charSet);
            byte[] out = cipher.doFinal(in);
            return new String(Base64.getEncoder().withoutPadding().encode(out), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
        } catch (Exception ex) {
        }
        return null;
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, generateKey());
            byte[] encryptedTextByte = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            String charSet = "UTF-8";
            String decryptedText = new String(decryptedByte, charSet);
            return decryptedText;
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
//            java.util.logging.Logger.getLogger(TokenHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(TokenHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec("3l8wOib86LJX644n".getBytes(), "AES");
        return key;
    }

    public String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String decryptedText = decrypt(authToken);
        if (decryptedText != null && !decryptedText.isEmpty()) {
            String[] parts = decrypt(authToken).split(":");
            return parts[0];
        } else {
            return null;
        }
    }

    private String getValueFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public int validateToken(String authToken, String uname) {
        if (authToken == null) {
            return 600;
        }
        String decryptedText = decrypt(authToken);
        if (decryptedText != null && !decryptedText.isEmpty()) {
            String[] parts = decryptedText.split(":");
            long expires = Long.parseLong(parts[1]);
            if (expires != -1) {
                if (expires < System.currentTimeMillis()) {
                    return 200;
                }
            }
            String signature = parts[2];
            if (signature.equals(computeSignature(uname, expires))) {
                return 400;
            }
        }
        return 600;

    }

    public void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("hitCounter", "hello");
        response.addCookie(cookie);
        response.setHeader(AUTH_TOKEN, "hellonew");
    }

    public void getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("cookie" + cookie.getValue());
            }
        }
    }

    @RequestMapping(value = "/testcookie", method = RequestMethod.GET)
    public String testCookie(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie cookie = new Cookie("test", "Boon");
        cookie.setMaxAge(265 * 24 * 60 * 60);  // (s)
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);   // response: HttpServletResponse
        Cookie[] cookies1 = request.getCookies();
        if (cookies1 != null) {
            for (Cookie cookie1 : cookies1) {
                if (cookie.getName().equals("user")) {
                    Cookie loginCookie = cookie;
                    break;
                }
            }
        }
        return null;
    }

}
