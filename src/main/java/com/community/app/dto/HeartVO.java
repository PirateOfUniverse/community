package com.community.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class HeartVO {
    private int hidx;
    private int pidx;
    private int idx;
    //private int heartCheck;

    public HeartVO() {}

    public HeartVO(int hidx, int pidx, int idx) {
        this.hidx = hidx;
        this.pidx = pidx;
        this.idx = idx;
        //this.heartCheck = heartCheck;
    }


}
