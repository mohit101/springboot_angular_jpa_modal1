package com.websystique.springboot;

import java.util.LinkedHashMap;
import java.util.List;

import com.websystique.springboot.model.User;
import com.websystique.springboot.mysql.ervice.impl.MysqlServiceImpl;
import java.net.URI;
import java.sql.ResultSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Import(SpringBootRestApiApp.class)

public class SpringBootRestTestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8089/SpringBootRestApi/api";

    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllUsers() {
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI + "/user/", List.class);

        if (usersMap != null) {
            for (LinkedHashMap<String, Object> map : usersMap) {
                System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary=" + map.get("salary"));;
            }
        } else {
            System.out.println("No user exist----------");
        }
    }

    /* GET */
    private static void getUser() {
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI + "/user/1", User.class);
        System.out.println(user);
    }

    /* POST */
    private static void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
//        User user = new User(0, "Sarah", 51, 134);
//        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", user, User.class);
//        System.out.println("Location : " + uri.toASCIIString());
        MysqlServiceImpl mysqlServiceImpl = new MysqlServiceImpl();
        mysqlServiceImpl.connectionMysql();
        System.out.println("Testing create User API----------");
        User user = new User("Sarah", "Sarah@123", "123qwe", 1);
        Boolean executeMysqlQuery = mysqlServiceImpl.executeMysqlQuery1("insert into user (username,email,password,active)" + "values ('suresh5','suresh@gmail.com','suresh123',1)");
        ResultSet executeMysqlQuery1 = mysqlServiceImpl.executeMysqlQuery("select * from user");
        System.err.println("yes"+executeMysqlQuery1);
         System.err.println("No"+executeMysqlQuery);
    }

    /* PUT */
    private static void updateUser() {
        System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
//        User user = new User(1, "Tomy", 33, 70000);
//        restTemplate.put(REST_SERVICE_URI + "/user/1", user);
//        System.out.println(user);
    }

    /* DELETE */
    private static void deleteUser() {
        System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/user/3");
    }

    /* DELETE */
    private static void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/user/");
    }

    public static void main(String args[]) {
//        listAllUsers();
//        getUser();
        createUser();
//        listAllUsers();
//        updateUser();
//        listAllUsers();
//        deleteUser();
//        listAllUsers();
//        deleteAllUsers();
//        listAllUsers();
        registerUser();
    }

    @Test
    private static void registerUser() {
        MysqlServiceImpl mysqlServiceImpl = new MysqlServiceImpl();
        mysqlServiceImpl.connectionMysql();
        System.out.println("Testing create User API----------");
        User user = new User("Sarah", "Sarah@123", "123qwe", 1);
        ResultSet executeMysqlQuery = mysqlServiceImpl.executeMysqlQuery("insert into user (username,email,password,active) values ('suresh3','suresh@gmail.com','suresh123',1)");

    }

}
