package com.zsw.practise.gp.factory.mothed;

import com.zsw.practise.gp.factory.simple.ICourse;
import com.zsw.practise.gp.factory.simple.JavaCourse;

/**
 * Java工厂
 *
 * @author zheng.shaowei
 * @create 2019-03-07 18:53
 **/
public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}
