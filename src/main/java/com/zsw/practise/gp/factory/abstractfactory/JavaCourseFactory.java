package com.zsw.practise.gp.factory.abstractfactory;

/**
 * @author zheng.shaowei
 * @create 2019-03-08 0:53
 **/
public class JavaCourseFactory implements CourseAbstractFactory {
    @Override
    public INote createNote() {
        return new JavaNote();
    }

    @Override
    public IVideo createVideo() {
        return new JavaVideo();
    }
}
