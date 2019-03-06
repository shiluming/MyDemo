package com.slm.zookeeper._3_1Master_StudentDemo;

import com.slm.zookeeper.Const;
import com.slm.zookeeper.Utils.Utils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TakeMasterDemo {



    public static void main(String[] args) {

        //参与竞选的学生数组
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        final List<DefaultStudentImpl> studentImpl = new ArrayList<DefaultStudentImpl>();
        for (int i = 0; i < 10; i++) {
            final int number = i + 1;
            Runnable runnable = new Runnable() {
                public void run() {
                    ZkClient zkClient = new ZkClient(Const.HOST, 5000, 5000, new SerializableSerializer());
                    Student student = new Student(number,  Thread.currentThread().getName() + " Student # " + number + 1, Utils.getRandomSecond());

                    DefaultStudentImpl defaultStudent = new DefaultStudentImpl(zkClient, student, cyclicBarrier);
                    //学生开始竞选班长
                    defaultStudent.go();

                    studentImpl.add(defaultStudent);
                }
            };
            new Thread(runnable).start();
        }

        System.out.println("回车键退出!\n");
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
