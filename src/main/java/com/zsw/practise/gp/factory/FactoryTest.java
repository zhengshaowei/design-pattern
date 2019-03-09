package com.zsw.practise.gp.factory;

import com.zsw.practise.gp.factory.abstractfactory.CourseAbstractFactory;
import com.zsw.practise.gp.factory.abstractfactory.INote;
import com.zsw.practise.gp.factory.abstractfactory.IVideo;
import com.zsw.practise.gp.factory.mothed.ICourseFactory;
import com.zsw.practise.gp.factory.mothed.JavaCourseFactory;
import com.zsw.practise.gp.factory.mothed.PythonCourseFactory;
import com.zsw.practise.gp.factory.simple.CourseSimpleFactory;
import org.junit.Test;

/**
 * @author zheng.shaowei
 * @create 2019-03-07 16:38
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {StartClass.class})
//@WebAppConfiguration
public class FactoryTest {

    @Test
    public void simpleFactoryTest(){
        ICourse course = CourseSimpleFactory.create(JavaCourse.class);
        course.record();

        course = CourseSimpleFactory.create(PythonCourse.class);
        course.record();
    }

    @Test
    public void factoryTest(){
        ICourseFactory factory = new JavaCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new PythonCourseFactory();
        course = factory.create();
        course.record();
    }

    @Test
    public void abstracFactoryTest(){
        CourseAbstractFactory factory = new com.zsw.practise.gp.factory.abstractfactory.JavaCourseFactory();
        INote note = factory.createNote();
        note.edit();
        IVideo video = factory.createVideo();
        video.record();

        factory = new com.zsw.practise.gp.factory.abstractfactory.PythonCourseFactory();
        note = factory.createNote();
        note.edit();
        video = factory.createVideo();
        video.record();
    }
}
