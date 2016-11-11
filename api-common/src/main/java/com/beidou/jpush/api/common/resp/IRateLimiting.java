package com.beidou.jpush.api.common.resp;

public interface IRateLimiting {

    public int getRateLimitQuota();
    
    public int getRateLimitRemaining();
    
    public int getRateLimitReset();
    
}

