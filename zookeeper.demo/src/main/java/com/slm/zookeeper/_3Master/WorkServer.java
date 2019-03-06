package com.slm.zookeeper._3Master;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkServer {

    //服务器是否在运行
    private volatile boolean running = false;


    private ZkClient zkClient;

    private static final String MASTER_PATH = "/master";

    ///子节点的内容变化
    private IZkDataListener dataListener;


    //从节点
    private RunningData serverData;

    //Master 节点
    private RunningData masterData;

    //延迟执行
    private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);

    /**
     * 功能描述: <br>
     * @Author: slm
     * @Date: 2019/2/27 18:26
     * @Param [runningData]
     * @return
     */
    public WorkServer(RunningData runningData) {
        this.serverData = runningData;

        this.dataListener = new IZkDataListener() {
            //节点改变触发
            public void handleDataChange(String s, Object o) throws Exception {

            }

            //节点删除触发
            public void handleDataDeleted(String s) throws Exception {
                takeMaster();
            }
        };
    }

    public void startServer() throws Exception {
        System.out.println("# " + this.serverData.getName() + " is start ...");

        if (running) {
            throw new Exception("server has startup ...");
        }

        running = true;

        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);

        takeMaster();
    }

    /**
     * 功能描述: master 节点争夺<br>
     * @Author: slm
     * @Date: 2019/2/28 14:27
     * @Param []
     * @return void
     */
    private void takeMaster() {

        if (!running) {
            return ;
        }
        try {

            zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
            masterData = serverData;
            System.out.println(serverData.getName() + " is master ...");

            //测试，5s 后判断是否是master节点，是的话，就释放master节点
            //释放后，其他节点都是有监听删除事件的，会争夺master
            delayExector.schedule(new Runnable() {
                public void run() {
                    // 5 s 后释放master节点
                    if (checkIsMaster()) {
                        releaseMaster();
                    }
                }
            }, 5, TimeUnit.SECONDS);

        } catch (ZkNodeExistsException e) {

            RunningData runningData = zkClient.readData(MASTER_PATH, true);
            if (null == runningData) {
                //争夺master节点
                takeMaster();
            } else {
                masterData = runningData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能描述: 检查是否是master节点<br>
     * @Author: slm
     * @Date: 2019/2/28 13:49
     * @Param []
     * @return boolean
     */
    private boolean checkIsMaster() {
        RunningData data = zkClient.readData(MASTER_PATH, true);
        masterData = data;
        if (masterData.getName().equals(serverData.getName())) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述: 是否 master <br>
     * @Author: slm
     * @Date: 2019/2/28 14:08
     * @Param []
     * @return void
     */
    private void releaseMaster() {
        if (checkIsMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    public void stop() throws Exception {
        if (!running) {
            throw new Exception("server has stoped!");
        }
        running = false;
        delayExector.shutdown();;
        zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
        releaseMaster();
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
}
