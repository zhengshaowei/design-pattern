package com.zsw.practise.gp.factory.abstractfactory;

/**
 * @author zheng.shaowei
 * @create 2019-03-08 0:47
 **/
public class JavaNote implements INote {
    @Override
    public void edit() {
        System.out.println("记录Java笔记");
    }
}
