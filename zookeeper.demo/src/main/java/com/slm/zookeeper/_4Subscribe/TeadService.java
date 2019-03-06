package com.slm.zookeeper._4Subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class TeadService implements ITeacher {

    private ZkClient zkClient;

    private Teacher teacher;

    public static String homeWorkPath = "/homework_path";

    public TeadService(ZkClient zkClient, Teacher teacher) {
        this.zkClient = zkClient;
        this.teacher = teacher;
    }

    /**
     * 功能描述: 发布作业<br>
     * @Author: slm
     * @Date: 2019/3/1 16:46
     * @Param [homeWork]
     * @return void
     */
    public void putHomeWork(HomeWork homeWork) {
        try {
            zkClient.writeData(homeWorkPath, homeWork);
        } catch (Exception e) {
            //第一次发布作业时，如果没有，创建作业频道
            zkClient.create(homeWorkPath, homeWork, CreateMode.PERSISTENT);
            System.out.println(e.getMessage());
        }
        System.out.println("【"+ teacher.getName() +"】发布作业啦~~");
    }
}
