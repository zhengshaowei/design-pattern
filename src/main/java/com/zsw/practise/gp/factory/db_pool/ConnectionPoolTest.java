package com.zsw.practise.gp.factory.db_pool;

import org.junit.Test;

import java.sql.Connection;

/**
 * @author zheng.shaowei
 * @create 2019-03-09 14:27
 **/
public class ConnectionPoolTest {

    //TODO 继续完成测试
    @Test
    public void getPoolTest(){
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        System.out.println(connectionPool);
    }

    @Test
    public void getConnectionTest(){
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        System.out.println(connection);
    }
}
