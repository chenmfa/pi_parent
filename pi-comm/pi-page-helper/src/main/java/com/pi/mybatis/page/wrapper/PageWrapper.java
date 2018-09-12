package com.pi.mybatis.page.wrapper;

import java.lang.reflect.Method;

import com.pi.mybatis.page.iface.ICallbackableEntity;
import com.pi.mybatis.page.iface.IPageableCallback;
import com.pi.mybatis.page.iface.IPageInfo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 此类适用于包装mapper方法中的parameter。
 * 并且此mapper方法中只有一个参数；
 * 如果有多个参数，请使用 {@link MapperWrapper}
 * <br>
 * <strong>注意：当方法的参数是一个final类时，此类不适用</strong>
 * @author luxiao
 *
 */
public class PageWrapper implements MethodInterceptor {

	private Object target;

	private Integer pageNumber;
	private Integer pageSize;
	private ICallbackableEntity<?, ?> callback;
	
	private PageWrapper(Integer pageNumber, Integer pageSize) {
		super();
		this.pageNumber = pageNumber ==null?1:pageNumber;
		this.pageSize = pageSize == null ? 1: pageSize;
		
	}

	public PageWrapper(Integer pageNumber, Integer pageSize, ICallbackableEntity<?, ?> callback) {
		this(pageNumber, pageSize);
		this.callback = callback;
	}

	/**
	 * 创建代理对象
	 * 
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T createProxyInstance(T target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		// 回调方法
		enhancer.setCallback(this);
		if(callback != null){
			enhancer.setInterfaces(new Class[] { IPageInfo.class, IPageableCallback.class });
		}else{
			enhancer.setInterfaces(new Class[] { IPageInfo.class });
		}
		// 创建代理对象
		return (T)enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (method != null && "getPageNumber".equals(method.getName())) {
			return pageNumber;
		}
		if (method != null && "getPageSize".equals(method.getName())) {
			return pageSize;
		}
		if (method != null && "getCallback".equals(method.getName())){
			return callback;
		}
		return method.invoke(target, args);
	}

	public static <T> T getWrappedInstance(T target, int pageNumber, int pageSize) {
		PageWrapper pageWrapper = new PageWrapper(pageNumber, pageSize);
		return pageWrapper.createProxyInstance(target);
	}

	public static <T> T getWrappedInstance(T target, int pageNumber, int pageSize, ICallbackableEntity<?, ?> callback) {
		PageWrapper pageWrapper = new PageWrapper(pageNumber, pageSize, callback);
		return pageWrapper.createProxyInstance(target);
	}

}
