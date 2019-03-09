package com.zsw.practise.gp.factory.mothed;

import com.zsw.practise.gp.factory.ICourse;
import com.zsw.practise.gp.factory.PythonCourse;

/**
 * Java工厂
 *
 * @author zheng.shaowei
 * @create 2019-03-07 18:53
 **/
public class PythonCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new PythonCourse();
    }
}
