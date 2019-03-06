package com.slm.zookeeper._1api;

import com.slm.zookeeper.Const;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * 功能描述: 异步创建节点<br>
 * @Author: slm
 * @Date: 2019/2/26 19:12
 * @Param
 * @return
 */
public class CreateNodeASync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper(Const.HOST, 5000, new CreateNodeASync());
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
           zooKeeper.create("/MyAppTest", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.PERSISTENT, new IStringCallBack(), "创建");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("do something ...");

    }

    static class IStringCallBack implements AsyncCallback.StringCallback {

        /**
         * 功能描述: <br>
         * @Author: slm
         * @Date: 2019/2/26 19:18
         * @Param [ rc: 返回码 0 表示成功
         *          path: 创建节点的完整路径
         *          ctx: 上面传入的值
         *          name: 服务器返回给我们已经创建的节点的真实路径，如果顺序节点和path name是不一样的
         * @return void
         */
        public void processResult(int rc, String path, Object ctx, String name) {

            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path=" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            sb.append("name=" + name).append("\n");
            System.out.println(sb.toString());
        }
    }

    static class IACLCallBack implements AsyncCallback.ACLCallback {

        public void processResult(int rc, String path, Object ctx, List<ACL> acl, Stat stat) {

        }
    }




}
