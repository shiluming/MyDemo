package com.slm.zookeeper._4Subscribe;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: 家庭作业 <br>
 * @Author: slm
 * @Date: 2019/3/1 16:42
 * @Param
 * @return
 */
public class HomeWork implements Serializable {

    public HomeWork(String value, Date expire) {
        this.value = value;
        this.expire = expire;
    }

    private String value;

    private Date expire;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
