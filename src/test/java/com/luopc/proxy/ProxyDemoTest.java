package com.luopc.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class ProxyDemoTest {

	@Test
	public void test001() {
		ForumService service = new ForumServiceImpl();
		service.removeForum(10);
		service.removeTopic(1024);
	}
	
	
	@Test
	public void test002() {
		ForumService service = new ForumServiceImpl();
		
		PerformanceHandler handler = new PerformanceHandler(service);
		
		ForumService proxy = (ForumService) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), handler);
		proxy.removeForum(10);
		proxy.removeTopic(1024);
	}
	
	@Test
	public void test003() {
		CglibProxy proxy = new CglibProxy();
		ForumServiceImpl foImpl = (ForumServiceImpl) proxy.getProxy(ForumServiceImpl.class);
		foImpl.removeForum(10);
		foImpl.removeTopic(1024);
		
	}
}
