package com.slm.zookeeper._5Lock;

import java.util.concurrent.TimeUnit;

public interface Lock {

    /**
     * 功能描述: 获取锁<br>
     * @Author: slm
     * @Date: 2019/3/4 10:23
     * @Param []
     * @return void
     */
    void getLock() throws Exception;

    /**
     * 功能描述: 获取锁，直到超时<br>
     * @Author: slm
     * @Date: 2019/3/4 10:23
     * @Param [timeout, unit]
     * @return void
     */
    void getLock(Long timeout, TimeUnit unit) throws Exception;

    /**
     * 功能描述: 释放锁<br>
     * @Author: slm
     * @Date: 2019/3/4 10:24
     * @Param []
     * @return void
     */
    void releaseLock() throws Exception;
}
