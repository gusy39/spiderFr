package com.beidou.models;

import com.beidou.common.dto.BaseDTO;

public class OrlAnswer extends BaseDTO {
    private Integer id;

    private Short fseq;

    private String fanswer;

    private Short fparentid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getFseq() {
        return fseq;
    }

    public void setFseq(Short fseq) {
        this.fseq = fseq;
    }

    public String getFanswer() {
        return fanswer;
    }

    public void setFanswer(String fanswer) {
        this.fanswer = fanswer == null ? null : fanswer.trim();
    }

    public Short getFparentid() {
        return fparentid;
    }

    public void setFparentid(Short fparentid) {
        this.fparentid = fparentid;
    }
}