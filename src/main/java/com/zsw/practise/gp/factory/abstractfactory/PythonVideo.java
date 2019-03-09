package com.zsw.practise.gp.factory.abstractfactory;

/**
 * @author zheng.shaowei
 * @create 2019-03-08 0:48
 **/
public class PythonVideo implements IVideo {

    @Override
    public void record() {
        System.out.println("录制Python视频");
    }
}
