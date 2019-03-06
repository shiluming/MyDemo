package com.slm.netty;

import com.slm.netty.callback.Work;

public class Application {

    public static void main(String[] args) {
        Work work = new Work();
        for (int i = 0; i < 10; i++)  {
            work.onWork();
        }
    }
}
