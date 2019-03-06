package com.slm.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZKClient {

    private static ZooKeeper zk;

    private static ZookeeperConnection conn;

    public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/MyClientTest";

        byte[] data = "".getBytes();

        conn = new ZookeeperConnection();

        zk = conn.connect("192.168.10.235");
        Stat exists =  zk.exists("/sss", true);
        System.out.println(exists);
//        create(path, data);
        Stat stat = new Stat();
        byte[] bytes = zk.getData(path, new Watcher() {
            public void process(WatchedEvent event) {

                System.out.println("接受到通知了");
            }
        }, stat);

        System.out.println("bytes = " + new java.lang.String(bytes));
        System.out.println("stat = " + stat);

        Thread.sleep(100000);
    }
}
