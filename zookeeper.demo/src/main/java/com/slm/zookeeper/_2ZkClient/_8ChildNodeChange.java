package com.slm.zookeeper._2ZkClient;

import com.slm.zookeeper.Const;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class _8ChildNodeChange {

    public static void main(String[] args) {
        ZkClient zkc = new ZkClient(Const.HOST, 10000, 10000, new SerializableSerializer());
        System.out.println("connected ok!");
        zkc.subscribeChildChanges("/", new ZkClientListerner());
        zkc.subscribeChildChanges("/server_name", new ZkClientListerner2());
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class ZkClientListerner implements IZkChildListener {

        public void handleChildChange(String s, List<String> list) throws Exception {
            System.out.println("ZkClientListerner parent path=" + s);
            System.out.println("childs = " + list.toString());
        }
    }

    private static class ZkClientListerner2 implements IZkChildListener {

        public void handleChildChange(String s, List<String> list) throws Exception {
            System.out.println("ZkClientListerner2 parent path=" + s);
            System.out.println("childs = " + list.toString());
        }
    }
}
