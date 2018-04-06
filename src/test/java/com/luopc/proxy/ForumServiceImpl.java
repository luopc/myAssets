package com.luopc.proxy;

public class ForumServiceImpl implements ForumService {

	@Override
	public void removeTopic(int topicId) {
//		PerformanceMonitor.begin("com.luopc.proxy.ForumSerivceImpl.removeTopic");
		System.out.println("模拟删除Topic记录：" + topicId);
		try {
			Thread.currentThread().sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		PerformanceMonitor.end();
	}

	@Override
	public void removeForum(int forumId) {
//		PerformanceMonitor.begin("com.luopc.proxy.ForumSerivceImpl.removeForum");
		System.out.println("模拟删除Forum记录：" + forumId);
		try {
			Thread.currentThread().sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		PerformanceMonitor.end();

	}

}
