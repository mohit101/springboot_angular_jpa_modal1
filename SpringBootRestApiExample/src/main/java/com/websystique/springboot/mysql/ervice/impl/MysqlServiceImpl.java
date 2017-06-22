/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.mysql.ervice.impl;

import com.websystique.springboot.mysqlconfig.MysqlConfig;
import com.websystique.springboot.mysqlservice.MysqlService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mohitm@bdcvit.com
 */
public class MysqlServiceImpl implements MysqlService {

    Logger logger = (Logger) LoggerFactory.getLogger(MysqlServiceImpl.class);
    private static String connectionURL = null;
    public Connection connection;
    public Statement statement;
    public static MysqlConfig config;

    public MysqlServiceImpl() {
        config = MysqlConfig.getInstance();
        connectionURL = prepareConnectionString(config.getHostServer(), config.getDbName());
    }

    public MysqlServiceImpl(String hostServer, String dbName, String userName, String password) {
        config = MysqlConfig.setConfig(hostServer, dbName, userName, password);
        connectionURL = prepareConnectionString(config.getHostServer(), config.getDbName());
    }

    @Override
    public void close() {
        logger.info("closed the msyql connection.");
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionMysql() {
        logger.info("connecting to mysql...");
        try {
            Class.forName(config.getClassName());
            connection = DriverManager.getConnection(connectionURL, config.getUsername(), config.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("connected to mysql ...");
    }

    public ResultSet executeMysqlQuery(String query) {
        try {
            statement = connection.createStatement();
            return (statement.executeQuery(query));
        } catch (Exception e) {

        }
        return null;
    }

    public Boolean executeMysqlQuery1(String query) {
        try {
            statement = connection.createStatement();
            return (statement.execute(query));
        } catch (Exception e) {

        }
        return null;
    }

    private static String prepareConnectionString(String hostURL, String dbName) {
        return "jdbc:mysql://" + hostURL + ":3306/" + dbName + "";
    }

}
