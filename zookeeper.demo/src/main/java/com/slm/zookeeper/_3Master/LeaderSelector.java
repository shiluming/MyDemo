package com.slm.zookeeper._3Master;

import com.slm.zookeeper.Const;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LeaderSelector {

    //启动的服务个数
    private static final int client_qty = 10;

    public static void main(String[] args) throws Exception {

        List<ZkClient> clients = new ArrayList<ZkClient>();

        List<WorkServer> workServers = new ArrayList<WorkServer>();

        try {

            for (int i = 0; i < client_qty; i++) {
                ZkClient client = new ZkClient(Const.HOST, 5000, 5000, new SerializableSerializer());
                clients.add(client);

                //创建server data
                RunningData runningData = new RunningData();
                runningData.setCid(Long.valueOf(i));
                runningData.setName("Client #" + i);

                //创建服务
                WorkServer workServer = new WorkServer(runningData);
                workServer.setZkClient(client);
                workServers.add(workServer);

                workServer.startServer();
            }

            System.out.println("回车键退出!\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();


        } finally {
            System.out.println("Shutting down ..");

            for (WorkServer workServer : workServers) {
                workServer.stop();
            }
            for (ZkClient client : clients) {
                client.close();
            }
        }
    }
}
