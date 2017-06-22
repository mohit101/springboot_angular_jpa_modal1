package com.websystique.springboot;

import com.websystique.springboot.model.User;
import com.websystique.springboot.mysql.ervice.impl.MysqlServiceImpl;
import java.net.URI;
import java.sql.ResultSet;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mohitm@bdcvit.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuth {

    public static final String REST_SERVICE_URI = "http://localhost:8089/SpringBootRestApi/api2";
    /* POST */

    private static void createUser1() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User("Sarah", "Sarah@123", "123qwe", 1);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/registerUser/", user, User.class);
        System.out.println("Location : " + uri.toASCIIString());
        MysqlServiceImpl mysqlServiceImpl = new MysqlServiceImpl();
        mysqlServiceImpl.connectionMysql();
        System.out.println("Testing create User API----------");

        Boolean executeMysqlQuery = mysqlServiceImpl.executeMysqlQuery1("insert into user (username,email,password,active)" + "values ('suresh7','suresh@gmail.com','suresh123',1)");
        ResultSet executeMysqlQuery1 = mysqlServiceImpl.executeMysqlQuery("select * from user");
        System.err.println("yes" + executeMysqlQuery1);
        System.err.println("No" + executeMysqlQuery);
    }

    public static void main(String args[]) {
        createUser1();
    }
}
