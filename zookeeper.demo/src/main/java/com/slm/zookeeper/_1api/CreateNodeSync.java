package com.slm.zookeeper._1api;

import com.slm.zookeeper.Const;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 功能描述: 同步创建节点<br>
 * @Author: slm
 * @Date: 2019/2/26 19:12
 * @Param
 * @return
 */
public class CreateNodeSync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper(Const.HOST, 5000, new CreateNodeSync());
            System.out.println(zooKeeper.getState());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            doSomething();
        }
    }

    private void doSomething() {

        try {
            String s = zooKeeper.create("/MyAppTest", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("result = " + s);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do something ...");

    }




}
