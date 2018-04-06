package com.luopc.advice;

import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdviceTest {

	@Test
	public void test001() {
		Waiter target = new NaiveWaiter();
		BeforeAdvice advice = new GreetingBeforAdvice();

		ProxyFactory pf = new ProxyFactory();

		pf.setTarget(target);
		pf.addAdvice(advice);

		Waiter proxy = (Waiter) pf.getProxy();
		proxy.greetTo("John");
		proxy.serveTo("Tom");
	}
	
	
	@Test
	public void test002() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-advice.xml");
		Waiter waiter = (Waiter) context.getBean("waiter");
		waiter.greetTo("John");
//		waiter.serveTo("Tom");
	}
}
