package com.hzq.transaction.dao;

import org.springframework.stereotype.Repository;

import com.hzq.commom.base.BaseMapper;
import com.hzq.transaction.entity.Storage;

/**
 * @author hzq
 *
 *         2015年8月22日 下午5:47:14
 */
@Repository
public interface StorageMapper extends BaseMapper<Storage> {

	/**
	 * @param i
	 * @return
	 */
	public Storage getStorage(int id);

}
