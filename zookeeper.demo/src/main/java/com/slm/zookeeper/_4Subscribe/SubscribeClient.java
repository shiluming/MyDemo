package com.slm.zookeeper._4Subscribe;

import com.slm.zookeeper.Const;
import com.slm.zookeeper.Utils.Utils;
import com.slm.zookeeper._3_1Master_StudentDemo.Student;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SubscribeClient {


    public static void main(String[] args) {

        final List<StudentService> studentList = new ArrayList<StudentService>();

        for (int i = 0; i < 10; i++) {
            final int number = i;
            Runnable runnable = new Runnable() {
                public void run() {
                    Student student = new Student(number, "Student$" + Thread.currentThread().getName(), number);
                    ZkClient zkClient = new ZkClient(Const.HOST, 5000, 5000, new SerializableSerializer());
                    StudentService studentService = new StudentService(zkClient, student);
                    studentService.subscribeHomeWork();
                }
            };
            new Thread(runnable).start();
        }

        final ZkClient zkClient = new ZkClient(Const.HOST, 5000, 5000, new SerializableSerializer());
        final Teacher teacher = new Teacher(10086, "牛逼老师");
        final TeadService teadService = new TeadService(zkClient, teacher);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //教师发布作业
                Calendar instance = Calendar.getInstance();
                instance.add(Calendar.DATE, 2);
                HomeWork homeWork = new HomeWork(Utils.getRandomHomeWork(), instance.getTime());
                teadService.putHomeWork(homeWork);
            }
        }, 5, 10, TimeUnit.SECONDS);

        System.out.println("回车键退出!\n");
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
