package com.slm.zookeeper._4Subscribe;

import com.slm.zookeeper._3_1Master_StudentDemo.Student;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class StudentService {


    private ZkClient zkClient;

    private Student student;


    public StudentService(ZkClient zkClient, Student student) {
        this.student = student;
        this.zkClient = zkClient;
    }

    /**
     * 功能描述: 订阅家庭作业<br>
     * @Author: slm
     * @Date: 2019/3/1 16:47
     * @Param []
     * @return void
     */
    public void subscribeHomeWork() {

        zkClient.subscribeDataChanges(TeadService.homeWorkPath, new IZkDataListener() {

            public void handleDataChange(String dataPath, Object data) throws Exception {
                StringBuilder sb = new StringBuilder("【"+student.getName()+"】收到作业啦~");
                if (data instanceof HomeWork) {
                    HomeWork work = (HomeWork) data;
                    sb.append(" 作业是:" +  work.getValue() + " ，截至时间是：" + work.getExpire());
                }
                System.out.println(sb.toString());
            }

            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });

    }


}
