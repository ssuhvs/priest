package com.little.g.admin.web.concurrent;

import com.little.g.admin.dto.LoggerActionDTO;
import com.little.g.admin.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

public class CheckLoggerActionTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(CheckLoggerActionTask.class);
	@Resource
	LoggerService loggerService;
	private Future<LoggerActionDTO> future;
	private List<Future<?>> futureList;

	public CheckLoggerActionTask(List<Future<?>> futureList,
			Future<LoggerActionDTO> future) {
		this.futureList = futureList;
		this.future = future;
	}

	@Override
	public void run() {
		checkLoggerAction();
	}

	private void checkLoggerAction() {
		try {
			if(future == null){
				return;
			}
			LoggerActionDTO action = future.get();
			if (action == null) {
				return;
			}
//			Integer actionId = action.getId();
			if(!CollectionUtils.isEmpty(futureList)){
				for(Future<?> tempFuture : futureList){
					tempFuture.get();
				}
			}
//			int count = loggerService.getCountOfLoggers(actionId.longValue());
//			if (count == 0) {
//				loggerService.deleteLoggerActionById(actionId.longValue());
//			} else {
//				loggerService.processActionComment(actionId.longValue());
//			}
//			loggerService.clearNoUsefulLoggerAction();
		} catch (Exception e) {
			LOG.info("checkLoggerAction exception, error->{}, message->", e, e.getMessage());
		}
	}
}
