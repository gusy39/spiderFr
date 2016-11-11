package com.beidou.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beidou.common.dto.BaseDTO;
import com.beidou.common.dto.Pager;
import com.beidou.common.dto.PagerHelper;
import com.beidou.common.exception.BaseDaoNullException;
import com.beidou.common.exception.ServiceException;
import com.beidou.common.persistent.BaseDao;

/**
 * Created by Administrator on 2015/6/25.
 */
public abstract class BaseServiceImpl<T extends BaseDTO, PK extends java.io.Serializable> implements BaseService<T, PK> {
    public Logger logger = LoggerFactory.getLogger(getClass());

    private BaseDao<T, PK> baseDao;

   // public abstract BaseDao getSupperBaseDao();

    public BaseDao<T, PK> getBaseDao() {
//        if (baseDao == null) {
//            baseDao = getSupperBaseDao();
//            if (baseDao == null) {
//                throw new BaseDaoNullException("BaseService is null");
//            }
//        }
        return baseDao;
    }

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public Pager<T> queryBySelective(Pager<T> page, T condition) throws ServiceException {
        try {
            return getBaseDao().queryBySelective(page, condition);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<T> queryBySelective(T condition) throws ServiceException {
        try {
            return getBaseDao().queryBySelective(condition);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public T getUniqueBySelective(T condition) throws ServiceException {
        List<T> list = getBaseDao().queryBySelective(condition);
        if (list != null && list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<T> queryBySelectiveByLimit(int limit, T condition) throws ServiceException {
        if (limit <= 0) {
            limit = 10;
        }
        Pager<T> page = PagerHelper.createPage(1, limit);
        List<T> list = baseDao.queryBySelective(page, condition).getResult();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(PK id) throws ServiceException {
        try {
            return getBaseDao().deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(T record) throws ServiceException {
        try {
            int rows = getBaseDao().insert(record);
            return rows;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertSelective(T record) throws ServiceException {
        try {
            int rows = getBaseDao().insertSelective(record);
            return rows;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T selectByPrimaryKey(PK id) throws ServiceException {
        try {
            if(id==null){
                return null;
            }
            return getBaseDao().selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKeySelective(T record) throws ServiceException {
        try {
            return getBaseDao().updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(T record) throws ServiceException {
        try {
            return getBaseDao().updateByPrimaryKey(record);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getModelIdByLock(Long id) {
        try {
            return getBaseDao().getModelIdByLock(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T getModelByLock(Long id) {
        try {
            return getBaseDao().getModelByLock(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean isExist(T record){
        try {
             return getBaseDao().isExist(record);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean isExistSingle(T record){
        try {
            return getBaseDao().isExistSingle(record);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }

    }
}
