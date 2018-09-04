package com.dsmjd.productutil.utils;


public class ParamUtil {
//	/**
//	 * 根据参数CODE，或者参数列表
//	 * 
//	 * @param code
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static List<PuSysParameter> getParamListByCode(String code) {
//		PuSysParameterExample example = new PuSysParameterExample();
//		example.createCriteria().andCodeEqualTo(code).andLevelEqualTo(2);
//		try {
//			return BeanUtil.getBean(PuSysParameterDAO.class).selectByExample(example);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<PuSysParameter>();
//		}
//	}
//
//	/**
//	 * 根据参数CODE，或者参数列表
//	 * 
//	 * @param code
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static PuSysParameter getParamItemByCode(String code) {
//		PuSysParameterExample example = new PuSysParameterExample();
//		example.createCriteria().andCodeEqualTo(code).andLevelEqualTo(2);
//		try {
//			List<PuSysParameter> list = BeanUtil.getBean(PuSysParameterDAO.class).selectByExample(example);
//			return list.get(0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new PuSysParameter();
//		}
//	}
}
