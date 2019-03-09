package com.zsw.practise.gp.factory.db_pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 自定义连接池 getInstance()返回 POOL 唯一实例,第一次调用时将执行构造函数
 * 构造函数 Pool()调用驱动装载 loadDrivers()函数;连接池创建 createPool()函数 loadDrivers()装载驱动
 * createPool()建连接池 getConnection()返回一个连接实例 getConnection(long time)添加时间限制
 * freeConnection(Connection con)将 con 连接实例返回到连接池 getnum()返回空闲连接数
 * getnumActive()返回当前使用的连接数
 * @author zheng.shaowei
 * @create 2019-03-08 17:39
 **/
public abstract class Pool {

    public String propertiesName = "connection-INF.properties";

    private static Pool instance = null; // 定义唯一实例

    /**
     * 最大连接数
     */
    protected int maxConnect = 100;

    /**
     * 保持连接数
     */
    protected int normalConnect = 10;

    /**
     * 驱动字符串
     *
     */
    protected String driverName = null;

    /**
     * 驱动类
     */
    protected Driver driver = null;

    /**
     * 私有构造函数，不允许外界访问
     */
    protected Pool(){
        try {
            init();
            loadDrivers(driverName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化从配置文件中读取的成员变量
     */
    private void init() throws IOException {
        InputStream is = Pool.class.getResourceAsStream(propertiesName);
        Properties p = new Properties();
        p.load(is);
        this.driverName = (String) p.get("driverName");
        this.maxConnect = Integer.parseInt((String) p.get("maxConnect"));
        this.normalConnect = (Integer) p.get("normalConnect");
    }

    /**
     * 装载和注册所有JDBC驱动程序
     * @param driverName    驱动字符串
     */
    private void loadDrivers(String driverName) {
        String driverClassName = driverName;
        try {
            this.driver = (Driver) Class.forName(driverClassName).newInstance();
            DriverManager.registerDriver(this.driver);
            System.out.println("成功注册JDBC驱动程序" + driverClassName);
        } catch (Exception e) {
            System.err.println("无法注册JDBC驱动程序" + driverClassName + ",错误：" + e);
        }
    }

    /**
     * 创建连接池
     */
    public abstract void createPool();

    /**
     * 返回数据库连接池Pool实例 (单例模式)
     *
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static synchronized Pool getInstance() throws IOException, IllegalAccessException, InstantiationException {
        if (instance == null){
            instance.init();
            instance = Pool.class.newInstance();
//            instance = (Pool) Class.forName("org.e_book.sqlhelp.Pool").newInstance();
        }
        return instance;
    }

    /**
     * 获得一个可用的连接，如果没有则创建一个连接，且小于最大连接限制
     * @return
     */
    public abstract Connection getConnection();

    /**
     * 获得一个连接，有时间限制
     * @param time  设置一个获取连接的等待时间（以毫秒为单位）
     * @return
     */
    public abstract Connection getConnection(long time);

    /**
     * 将连接对象返回给连接池
     * @param con   连接对象
     */
    public abstract void freeConnection(Connection con);

    /**
     * 返回当前空闲连接数量
     * @return
     */
    public abstract int getnum();

    /**
     * 返回当前使用的连接数
     * @return
     */
    public abstract int getnumActive();

    /**
     * 关闭所有连接，撤销驱动注册（此方法为单例方法）
     */
    public synchronized void release(){
        // 撤销驱动
        try {
            DriverManager.deregisterDriver(driver);
            System.out.println("撤销JDBC驱动程序" + driver.getClass().getName());
        } catch (SQLException e) {
            System.err.println("无法撤销JDBC驱动程序的注册：" + driver.getClass().getName());
        }
    }

}
