package com.hzq.transaction.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hzq.transaction.service.SecondService;
import com.hzq.transaction.service.TransActionService;

/**
 * @author hzq
 *
 *         2015年8月22日 下午6:12:51
 */
@Service
public class SecondServiceImpl implements SecondService {
	@Autowired
	private TransActionService transActionService;

	@Override
	public void batchReduce(List<Integer> list) {
		for (int num : list) {
			transActionService.reduce(num);
		}
	}

}
