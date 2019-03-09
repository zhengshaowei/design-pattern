package com.zsw.practise.gp.factory.db_pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * 数据库连接池管理类
 *
 * @author zheng.shaowei
 * @create 2019-03-08 22:41
 **/
public final class DBConnectionPool extends Pool {

    /**
     * 存放产生的连接对象容器
     */
    private Vector<Connection> freeConnections = new Vector<>();

    /**
     * 用户名
     */
    private String userName = null;

    /**
     * 密码
     */
    private String passWord = null;

    /**
     * 连接字符串
     */
    private String url = null;

    /**
     * 空闲连接数
     */
    private static int num = 0;

    /**
     * 当前连接数
     */
    private static int numActive = 0;

    /**
     * 正在使用的连接数
     */
    private int checkedOut;

    /**
     * 连接池实例变量
     */
    private static DBConnectionPool pool = null;

    /**
     * 获取数据连接池
     *
     * @return
     */
    public static synchronized DBConnectionPool getInstance() {
        if (pool == null) {
            pool = new DBConnectionPool();
        }

        return pool;
    }

    /**
     * 数据库连接池实例化构造方法
     */
    private DBConnectionPool() {
        try {
            init();
            for (int i = 0; i < normalConnect; i++) { //初始 normalConnect 个连接
                Connection c = newConnection();
                if (c != null) {
                    freeConnections.addElement(c); //往容器里添加一个连接对象
                    num++; // 记录总连接数
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件，初始化属性变量
     *
     * @throws IOException
     */
    private void init() throws IOException {
        InputStream is = DBConnectionPool.class.getResourceAsStream(propertiesName);
        Properties p = new Properties();
        p.load(is);
        this.userName = p.getProperty("userName");
        this.passWord = p.getProperty("passWord");
        this.driverName = p.getProperty("driverName");
        this.url = p.getProperty("url");
        this.maxConnect = Integer.parseInt(p.getProperty("maxConnect"));
        this.normalConnect = Integer.parseInt(p.getProperty("normalConnect"));
    }

    private Connection newConnection() {
        Connection con = null;
        try {
            if (userName == null || passWord == null) {
                con = DriverManager.getConnection(url);
            }else {
                con = DriverManager.getConnection(userName, passWord, url);
            }
            System.out.println("连接池创建一个新的连接");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return con;
    }

    /**
     * 建立连接池
     */
    @Override
    public void createPool() {
        pool = new DBConnectionPool();
        if (pool != null) {
            System.out.println("创建连接池成功");
        } else {
            System.out.println("创建连接池失败");
        }
    }

    /**
     * （单例模式）获取一个可用连接
     * @return
     */
    @Override
    public synchronized Connection getConnection() {
        Connection con = null;
        if (freeConnections.size() > 0){ //还有空闲连接
            num--;
            con = freeConnections.firstElement();
            freeConnections.removeElementAt(0);
            try {
                if (con.isClosed()){
                    System.out.println("从连接池删除一个无效连接");
                    con = getConnection();
                }
            } catch (SQLException e) {
                System.out.println("从连接池删除一个无效连接");
                con = getConnection();
            }
        }else if (maxConnect == 0 || checkedOut < maxConnect){
            // 没有空闲连接，且当前连接小于最大允许值；最大值为0则不限制
            con = newConnection();
        }

        if (con != null){ // 当前连接数加一
            checkedOut++;
        }
        numActive++;
        return con;
    }

    /**
     * 获取一个连接，并加上等待时间限制，时间为毫秒
     * @param timeout  获取连接的等待时间（以毫秒为单位）
     * @return
     */
    @Override
    public synchronized Connection getConnection(long timeout) {
        long startTime = new Date().getTime();
        Connection con;
        while((con = getConnection()) == null){
            try {
                wait(timeout); //现场等待（有连接被归还时，会notifyAll()）
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
            if ((new Date().getTime() - startTime) >= timeout){
                return null; // 如果超时，则返回
            }

        }
        return con;
    }

    /**
     * 如果不再使用某个连接对象时，可调此方法将该对象释放归还到连接池
     * @param con   连接对象
     */
    @Override
    public synchronized void freeConnection(Connection con) {
        freeConnections.addElement(con);
        num++;
        numActive--;
        checkedOut--;
        notifyAll(); // 解锁
    }

    /**
     * 关闭所有连接
     */
    public synchronized void release(){
        try {
            // 将当前连接赋值到 枚举中
            Enumeration<Connection> allConnections = freeConnections.elements();

            // TODO 是否存在没有归还的连接
            // 使用循环关闭所有连接
            while (allConnections.hasMoreElements()){
                // 如果此枚举对象至少还要一个可提供的元素，则返回此枚举的下一个元素
                Connection con = allConnections.nextElement();
                try {
                    con.close();
                    num--;
                } catch (SQLException e) {
                    System.err.println("无法关闭连接池中的连接");
                }
            }
            freeConnections.removeAllElements();
            numActive = 0;
        } finally {
            super.release();
        }
    }

    /**
     * 返回当前空闲连接数
     * @return
     */
    @Override
    public int getnum() {
        return num;
    }

    /**
     * 返回当前连接数
     * @return
     */
    @Override
    public int getnumActive() {
        return numActive;
    }
}
