package com.slm.zookeeper._3_1Master_StudentDemo;

import com.slm.zookeeper.Utils.Utils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class DefaultStudentImpl implements IStudent {

    private static String MASTER_PATH = "/STUDENT_MASTER";

    private ZkClient zkClient;

    private Student student;

    private CyclicBarrier cyclicBarrier;

    //班长
    private Student master;

    public DefaultStudentImpl(ZkClient zkClient, Student student, CyclicBarrier cyclicBarrier) {
        this.zkClient = zkClient;
        this.student = student;
        this.cyclicBarrier = cyclicBarrier;
    }

    public void speak(Student student) {
        Date start = new Date();
        try {
            System.out.println("【" + student.getName() + "】正在发表演讲 ! ");
            Thread.sleep(Utils.getRandomSecond());
            System.out.println("【" + student.getName() + "】发表演讲完毕 ! 等待其他同学发表演讲" + Utils.showTime(start));

            //等待其他线程发表演讲
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("【" + student.getName() + "】所有同学演讲完毕，开始竞选班长 ! ");
    }

    public void takeMaster(Student student) {
        try {
            String ret = zkClient.create(MASTER_PATH, student, CreateMode.EPHEMERAL);
            master = student;
            System.out.println("【"+ student.getName() +"】竞选班长成功啦，热烈欢迎，呼啦啦啦啦啦啦啦啦啦啦啦啦啦！！！！！！");
            Utils.getScheduledExecutorService().schedule(new Runnable() {
                public void run() {
                    releaseMaster();
                }
            }, 8, TimeUnit.SECONDS);
        }catch (Exception e) {
            Student masterStudent = zkClient.readData(MASTER_PATH);
            System.out.println("【" + student.getName() + "】竞选班长失败! 现任班长是：" + masterStudent.getName());
            master = masterStudent;
        }
    }

    public void go () {
        //订阅删除事件
        zkClient.subscribeDataChanges(MASTER_PATH, new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            public void handleDataDeleted(String dataPath) throws Exception {
                //删除事件, 直接竞选班长
                takeMaster(student);
            }
        });
        speak(student);
        takeMaster(student);
    }

    /**
     * 功能描述: <br>
     * @Author: slm
     * @Date: 2019/3/1 13:42
     * @Param []
     * @return void
     */
    public void releaseMaster() {
        if (master != null && student != null) {
            if (master.getName().equals(student.getName())) {
                //释放master
                zkClient.delete(MASTER_PATH);
                System.out.println("");
                System.out.println("");
                System.out.println("班长【"+ master.getName() +"】卸任了。其他同学可以直接竞选班长啦~~~~");

            }
        }
    }

    public Student getMasterStudent() {
        return master;
    }
}
