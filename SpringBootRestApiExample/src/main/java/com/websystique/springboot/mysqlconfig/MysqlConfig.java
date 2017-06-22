/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.mysqlconfig;

/**
 *
 * @author mohitm@bdcvit.com
 */
public class MysqlConfig {

    private String className = null;
    private String hostServer = null;
    private String dbName = null;
    private String username = null;
    private String password = null;
    private static final MysqlConfig mysqlConfig = new MysqlConfig();

    private MysqlConfig() {
        className = "com.mysql.jdbc.Driver";
        dbName = "auth";
        username = "alfresco";
        password = "alfresco";
        hostServer = "localhost";
    }

    public static MysqlConfig getInstance() {
        return mysqlConfig;
    }

    public static MysqlConfig setConfig(String hostServer, String dbName, String userName, String password) {
        mysqlConfig.className = "com.mysql.jdbc.Driver";
        mysqlConfig.hostServer = hostServer;
        mysqlConfig.dbName = dbName;
        mysqlConfig.username = userName;
        mysqlConfig.password = password;
        return mysqlConfig;
    }

    public String getClassName() {
        return className;
    }

    public String getHostServer() {
        return hostServer;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static MysqlConfig getMysqlConfig() {
        return mysqlConfig;
    }

}
