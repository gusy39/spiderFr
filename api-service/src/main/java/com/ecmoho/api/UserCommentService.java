package com.ecmoho.api;

import com.beidou.common.dto.Pager;
import com.beidou.models.UserComment;

import java.util.List;

/**
 * Created by Administrator on 16-1-19.
 */
public interface UserCommentService {

    public Pager<UserComment> getList(UserComment condition, Pager<UserComment> page);
}
