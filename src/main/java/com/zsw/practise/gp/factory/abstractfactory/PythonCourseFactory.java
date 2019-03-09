package com.zsw.practise.gp.factory.abstractfactory;

/**
 * @author zheng.shaowei
 * @create 2019-03-08 0:57
 **/
public class PythonCourseFactory implements CourseAbstractFactory {
    @Override
    public INote createNote() {
        return new PythonNote();
    }

    @Override
    public IVideo createVideo() {
        return new PythonVideo();
    }
}
