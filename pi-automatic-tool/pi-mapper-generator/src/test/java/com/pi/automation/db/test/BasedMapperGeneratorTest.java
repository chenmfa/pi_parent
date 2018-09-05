package com.pi.automation.db.test;

import com.pi.automation.db.generator.tool.BasedMapperGenerator;

public class BasedMapperGeneratorTest {

	public static void main(String[] args) throws Exception {
		// 输出文件在output目录下
		BasedMapperGenerator mapperGenerator = new BasedMapperGenerator();
//		mapperGenerator.generate("stripe_db", "user", "com.pi.usercenter.dao");
		mapperGenerator.generate("stroop_db", "stroop_invitation", "com.pi.stroop.dao");
		mapperGenerator.generate("stroop_db", "stroop_diagnosis_history", "com.pi.stroop.dao");
//		mapperGenerator.generate("stripe_db", "base_partner_info", "com.pi.pub.dao");
//		mapperGenerator.generate("stripe_db", "base_partner_config", "com.pi.pub.dao");
	}
}
