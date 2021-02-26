package com.umb.diaryumb;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public Connection connection;

    private final String host = "ec2-3-229-210-93.compute-1.amazonaws.com";  // For Google Cloud Postgresql
    private final String database = "d9aphjm3u3i6vt";
    private final int port = 5432;
    private final String user = "nortmrnrkxhihx";
    private final String pass = "4c0136e54ef2a1d954ed737ca35381f82cde2ef45dd839fcf48cbaa9ae18d8bf";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        getConnection();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }


    private synchronized Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            status = true;
            System.out.println("connected:" + status);
        } catch (Exception e) {
            status = false;
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}