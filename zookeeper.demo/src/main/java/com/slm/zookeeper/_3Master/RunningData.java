package com.slm.zookeeper._3Master;

import java.io.Serializable;

public class RunningData implements Serializable {

    private Long cid;

    private String name;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
