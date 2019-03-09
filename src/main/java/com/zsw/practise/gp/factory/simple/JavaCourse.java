package com.zsw.practise.gp.factory.simple;

/**
 * @author zheng.shaowei
 * @create 2019-03-07 16:20
 **/
public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制Java课程");
    }
}
