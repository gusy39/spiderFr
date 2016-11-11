package com.ecmoho.api;

import com.beidou.common.dto.Pager;
import com.beidou.models.OrlAnswer;
import com.beidou.models.OrlTest;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
public interface OrlAnswerService {

    public Pager<OrlAnswer> getListMap(OrlAnswer condition, Pager page);

}
