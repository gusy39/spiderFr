package com.beidou.common.persistent;


import com.beidou.common.dto.BaseDTO;
import com.beidou.common.dto.Pager;

import java.util.List;

public interface BaseDao<T extends BaseDTO, PK extends java.io.Serializable> {
	
	Pager<T> queryBySelective(Pager<T> page, T condition);

	List<T> queryBySelective(T condition);

	int deleteByPrimaryKey(PK id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(PK id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	Long getModelIdByLock(Long id);

	T getModelByLock(Long id);

    boolean isExist(T record);

    boolean isExistSingle(T record);
}
