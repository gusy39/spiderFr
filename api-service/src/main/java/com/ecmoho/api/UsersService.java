package com.ecmoho.api;

import com.beidou.common.dto.Pager;
import com.beidou.models.ShopCount;
import com.beidou.models.Users;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
public interface UsersService {


    public Pager<Users> getList(Users conditon, Pager<Users> page);
}
