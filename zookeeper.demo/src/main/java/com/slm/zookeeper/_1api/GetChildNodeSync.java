package com.slm.zookeeper._1api;

import com.slm.zookeeper.Const;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 功能描述: 获取节点，同步方式<br>
 * @Author: slm
 * @Date: 2019/2/27 13:59
 * @Param
 * @return
 */
public class GetChildNodeSync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper(Const.HOST, 5000, new GetChildNodeSync());
            System.out.println(zooKeeper.getState().toString());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            if (event.getType() == Event.EventType.None && null == event.getPath()) {
                doSomething();
            } else {
                //孩子节点改变
                if (event.getType() == Event.EventType.NodeChildrenChanged) {

                }
            }
            doSomething();
        }
    }

    private void doSomething() {
        System.out.println("do something");
    }
}
