package com.slm.netty.callback;

import java.util.Random;

public class NetWork {

    public void netWork(ICallBack callBack) {

        System.out.println(Thread.currentThread().getName() + " 开始上网");
        try {
            Thread.sleep(1000 * 2);
            Random random = new Random();
            boolean b = random.nextBoolean();
            if (b) {
                throw new Exception("");
            }
        } catch (Exception e) {
            callBack.fail();
        }
        callBack.success();
    }
}
