package com.zsw.practise.gp.factory;

/**
 * @author zheng.shaowei
 * @create 2019-03-07 16:20
 **/
public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制Python课程");
    }
}
