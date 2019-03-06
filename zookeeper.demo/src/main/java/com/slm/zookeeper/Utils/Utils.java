package com.slm.zookeeper.Utils;

import com.slm.zookeeper._3_1Master_StudentDemo.Student;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static Random random = new Random();

    public static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static Integer getRandomSecond(){
        return random.nextInt(10) * 1000;
    }

    public static String showTime(Date start) {
        StringBuilder sb = new StringBuilder("消耗时间【");
        Date now = new Date();
        long l = now.getTime() - start.getTime();
        sb.append(l+"】毫秒");
        return sb.toString();
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    /**
     * 功能描述: 随机获取家庭作业<br>
     * @Author: slm
     * @Date: 2019/3/1 17:10
     * @Param []
     * @return java.lang.String
     */
    public static String getRandomHomeWork() {
        String[] homeWork = {"数学作业", "物理作业", "语文作业", "外语作业", "地理作业", "生物作业"};
        int i = random.nextInt(homeWork.length);
        return homeWork[i];
    }


    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("-------");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程名称：" + t.getName() + ", msg:" + e.getMessage());
            }
        });
        final Student student = null;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println(student.getName());
            }
        }, 2, 3, TimeUnit.SECONDS);

    }
}
