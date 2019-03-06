package com.slm.zookeeper._5Lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseLock {

    private final ZkClient zkClient;

    private final String path;

    private final String basePath;

    private final String lockName;

    private static final Integer MAX_RETRY_COUNT = 10;

    public BaseLock(ZkClient zkClient, String path, String lockName) {
        this.zkClient = zkClient;
        this.basePath = path;
        this.path = path.concat("/").concat(lockName);
        this.lockName = lockName;
    }

    /**
     * 功能描述: 等待获取锁<br>
     * @Author: slm
     * @Date: 2019/3/4 10:36
     * @Param [startMills, millisToWait, ourPath]
     * @return boolean
     */
    private boolean waitToLock(long startMills, long millisToWait, String ourPath) throws Exception {
        boolean haveTheLock = false;

        boolean doDeleteOurPath = false;

        while (!haveTheLock) {
            //获取所有锁节点（/locker 下的子节点）并排序
            List<String> children = getSortedChildren();

            //获取顺序节点的名字，如：/locker/lock-00000012 > /locker-0000000013
            String seqNodeName = ourPath.substring(basePath.length() + 1);

            //判断该节点是否在所有子节点的第一位，如果是就已经获得锁
            int outIndex = children.indexOf(seqNodeName);
            if (outIndex < 0) {
                throw new ZkNoNodeException("节点没有找到：" + seqNodeName);
            }

            boolean isGetTheLock = (outIndex == 0);
            if (isGetTheLock) {
                //如果第一位，已经获得锁
                haveTheLock = true;
            } else {

                String pathToWatch = children.get(outIndex - 1);
                String previousSeqPath = basePath.concat("/").concat(pathToWatch);
                final CountDownLatch latch = new CountDownLatch(1);
            }
            return true;
        }
        return true;
    }



    /**
     * 功能描述: 获取所有锁节点（/locker 下的节点）并排序 <br>
     * @Author: slm
     * @Date: 2019/3/4 10:38
     * @Param []
     * @return java.util.List<java.lang.String>
     */
    private List<String> getSortedChildren() throws Exception {
        try {
            List<String> children = zkClient.getChildren(basePath);
            Collections.sort(children, new Comparator<String>() {
                public int compare(String lhs, String rhs) {
                    return getLockNodeNumber(lhs, lockName).compareTo(getLockNodeNumber(rhs, lockName));
                }
            });
            return children;
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(basePath, true);
            return getSortedChildren();
        }
    }


    private String getLockNodeNumber(String str, String lockName) {
        int index = str.lastIndexOf(lockName);
        if (index > 0) {
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }


}
