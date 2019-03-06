package com.slm.zookeeper._3_1Master_StudentDemo;

public interface IStudent {

    /**
     * 功能描述: 演讲<br>
     * @Author: slm
     * @Date: 2019/3/1 10:12
     * @Param []
     * @return void
     */
    void speak(Student student);

    /**
     * 功能描述: 竞选班长<br>
     * @Author: slm
     * @Date: 2019/3/1 10:12
     * @Param []
     * @return void
     */
    void takeMaster(Student student);

    void go();
}
