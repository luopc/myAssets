package com.luopc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;

/**
 * BeanFactory作为最原始同时也最重要的Ioc容器,它主要的功能是为依赖注入 （DI） 提供支持,BeanFactory
 * 和相关的接口.这里定义的只是一系列的接口方法，通过这一系列的BeanFactory接口，
 * 可以使用不同的Bean的检索方法很方便地从Ioc容器中得到需要的Bean，
 * 从而忽略具体的Ioc容器的实现，从这个角度上看，这些检索方法代表的是最为基本的容器入口。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 13 April 2001
 */
public interface BeanFactory {

	/**
	 * 转定义符"&" 用来引用实例，或把它和工厂产生的Bean区分开，就是说，如果一个FactoryBean的名字为a，那么，&a会得到那个Factory
	 *
	 * FactoryBean和BeanFactory
	 * 是在Spring中使用最为频繁的类，它们在拼写上很相似。一个是Factory，也就是Ioc容器或对象工厂；一个
	 * 是Bean。在Spring中，所有的Bean都是由BeanFactory（也就是Ioc容器）来进行管理的。但对FactoryBean而言，这个Bean不是简单的Be
	 * an，而是一个能产生或者修饰对象生成的工厂Bean，它的实现与设计模式中的工厂模式和修饰器模式类似。
	 */
	String FACTORY_BEAN_PREFIX = "&";

	/**
	 * 五个不同形式的getBean方法，获取实例
	 * 
	 * @param name
	 *            检索所用的Bean名
	 * @return Object（<T> T） 实例对象
	 * @throws BeansException
	 *             如果Bean不能取得
	 */
	Object getBean(String name) throws BeansException;

	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	<T> T getBean(Class<T> requiredType) throws BeansException;

	Object getBean(String name, Object... args) throws BeansException;

	<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

	/**
	 * 让用户判断容器是否含有指定名字的Bean.
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @return boolean 是否包含其中
	 */
	boolean containsBean(String name);

	/**
	 * 查询指定名字的Bean是否是Singleton类型的Bean. 对于Singleton属性，可以在BeanDefinition指定.
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @return boolean 是否包是Singleton
	 * @throws NoSuchBeanDefinitionException
	 *             没有找到Bean
	 */
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 查询指定名字的Bean是否是Prototype类型的。 与Singleton属性一样，可以在BeanDefinition指定.
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @return boolean 是否包是Prototype
	 * @throws NoSuchBeanDefinitionException
	 *             没有找到Bean
	 */
	boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 查询指定了名字的Bean的Class类型是否是特定的Class类型.
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @param typeToMatch
	 *            匹配类型
	 * @return boolean 是否是特定类型
	 * @throws NoSuchBeanDefinitionException
	 *             没有找到Bean
	 */
	boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;

	boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

	/**
	 * 查询指定名字的Bean的Class类型.
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @return 指定的Bean或者null(没有找到合适的Bean)
	 * @throws NoSuchBeanDefinitionException
	 *             没有找到Bean
	 */
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 查询指定了名字的Bean的所有别名，这些都是在BeanDefinition中定义的
	 * 
	 * @param name
	 *            搜索所用的Bean名
	 * @return 指定名字的Bean的所有别名 或者一个空的数组
	 */
	String[] getAliases(String name);
}
