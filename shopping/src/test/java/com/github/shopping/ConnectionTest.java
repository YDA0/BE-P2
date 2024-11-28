package com.github.shopping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {

    @Test
    public void ConnectionTest() throws Exception {

        Class.forName("org.mariadb.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://database-p2.cx00oo0eeajx.ap-northeast-2.rds.amazonaws.com:3306/shopping",
                "root",
                "asdfqwer1234");

        Assertions.assertNotNull(connection);

        connection.close();
    }
}
