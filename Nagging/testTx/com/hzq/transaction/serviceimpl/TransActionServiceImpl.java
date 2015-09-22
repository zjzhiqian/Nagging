package com.hzq.transaction.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hzq.commom.base.BaseService;
import com.hzq.transaction.dao.StorageMapper;
import com.hzq.transaction.entity.Storage;
import com.hzq.transaction.service.TransActionService;

/**
 * @author hzq
 *
 *         2015年8月22日 下午5:43:34
 */
@Service
public class TransActionServiceImpl extends BaseService<Storage> implements
		TransActionService {
	@Autowired
	private StorageMapper storageMapper;

	public void reduce(int num) {
		Storage sotrage = storageMapper.getStorage(1);
		int leftNum = sotrage.getLeftnum();
		sotrage.setLeftnum(leftNum - num);
		BaseUpdate(sotrage);
		if ((leftNum - num) < 0) {
			throw new RuntimeException("余额不足");
		}
	}

}
