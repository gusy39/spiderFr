package com.beidou.jpush.api.push;


import com.beidou.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {
    
    @Expose public long msg_id;
    @Expose public int sendno;
    
}

