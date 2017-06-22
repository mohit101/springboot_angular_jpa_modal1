/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.mysqlservice;

import java.sql.ResultSet;

/**
 *
 * @author mohitm@bdcvit.com
 */
public interface MysqlService {

    void connectionMysql();

    void close();

    ResultSet executeMysqlQuery(String query);

}
