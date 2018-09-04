package com.dsmjd.productutil.utils;


/**
 * Bean工具类。用于获取DAO对象、Service对象。
 * 
 * @projectName: [smhTcpServer]
 * @package: [cn.smh.util.BeanUtil.java]
 * @className: [BeanUtil]
 * @author: [Administrator]
 * @createDate: [2013-8-12 下午04:37:47]
 * @version: [v1.0] Copyright DESSMANN Corporation 2013
 */
//@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class BeanUtil {
//
//	/**
//	 * Dao的Context。
//	 */
//	private static ApplicationContext daoContext = null;
//
//	/**
//	 * Bean对象的列表。用于存储从Context中获取到的Bean。
//	 */
//	private static Map<Class, Object> beanMap = new HashMap<Class, Object>();
//
//	/**
//	 * 获取ApplicationContext对象。
//	 * 
//	 * @methodName: [BeanUtil.getDaoContext]
//	 * @return
//	 */
//	private static ApplicationContext getDaoContext() {
//		if (daoContext == null) {
//			daoContext = new ClassPathXmlApplicationContext("xmls\\spring\\applicationContext-dao-all.xml");
//			// System.out.println(".........DAO初始化完成..........");
//		}
//		return daoContext;
//	}
//
//	/**
//	 * 根据Dao的类获取Bean对象。 <br/>
//	 * 如 <code>TservCustomerinfoDAO custDao = BeanUtil.getBean(TservCustomerinfoDAO.class);</code>
//	 * 
//	 * @methodName: [BeanUtil.getBean]
//	 * @param <T>
//	 * @param classT
//	 * @return
//	 * @throws Exception
//	 */
//	public static <T> T getBean(Class<T> classT) throws Exception {
//
//		T bean = (T) beanMap.get(classT);
//
//		if (bean == null) {
//
//			Iterator iterator = getDaoContext().getBeansOfType(classT).values().iterator();
//
//			if (iterator.hasNext()) {
//				bean = (T) getDaoContext().getBeansOfType(classT).values().iterator().next();
//				beanMap.put(classT, bean);
//				return bean;
//			} else {
//				System.out.println("\n-------------------------------------------------------------");
//				System.out.println("\n 没有找到该Class的Bean对象。Class Name : " + classT.getName());
//				throw new Exception("\n 没有找到该Class的Bean对象。Class Name : " + classT.getName());
//			}
//		} else {
//			return bean;
//		}
//	}
//
//	/**
//	 * Dao的事务控制。
//	 */
//	private static DataSourceTransactionManager transactionManager = null;
//
//	public synchronized static DataSourceTransactionManager getTransactionManager() {
//		if (transactionManager == null) {
//			try {
//				transactionManager = BeanUtil.getBean(DataSourceTransactionManager.class);
//			} catch (Exception e) {
//				System.out.println("事务控制管理启动异常。");
//				e.printStackTrace();
//			}
//		}
//		return transactionManager;
//	}
//
//	public static void main(String[] args) throws Exception {
//		PuSysGroupDAO groupDAO = BeanUtil.getBean(PuSysGroupDAO.class);
//		PuSysGroupExample example = new PuSysGroupExample();
//		System.out.println(groupDAO.selectByExample(example));
//	}

}
