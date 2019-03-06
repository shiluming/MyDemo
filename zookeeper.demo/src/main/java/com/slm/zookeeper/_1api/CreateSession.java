package com.slm.zookeeper._1api;

import com.slm.zookeeper.Const;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class CreateSession implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            //这里的事件是异步的，而且是一次通知
            zooKeeper = new ZooKeeper(Const.HOST, 5000, new CreateSession());

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
        System.out.println("do something ...");
    }
}
