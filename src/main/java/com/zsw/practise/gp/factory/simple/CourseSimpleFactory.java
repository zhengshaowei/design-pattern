package com.zsw.practise.gp.factory.simple;

/**
 * 简单工厂模式
 * 简单工厂模式（Simple Factory Pattern）是指由一个工厂对象决定创建出哪一种产品
 * 类的实例，但它不属于 GOF，23 种设计模式
 * 适用场景：
 * 工厂类负责创建的对象较少；
 * 客户端只需要传入工厂类的参数，对于如何创建对象的逻辑不需要关心。
 * 优点：
 * 只需传入一个正确的参数，就可以获取你所需要的对象，无须知道其创建的细节。
 * 缺点：
 * 工厂类的职责相对过重，增加新的产品时需要修改工厂类的判断逻辑，违背开闭原则；
 * 不易于扩展过于复杂的产品结构。
 * @author zheng.shaowei
 * @create 2019-03-07 16:29
 **/
public class CourseSimpleFactory {

    public static ICourse create(Class<? extends ICourse> clazz){
        if (clazz != null){
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
