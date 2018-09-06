package com.pi.automation.db.test;

import com.pi.automation.db.generator.tool.BasedMapperGenerator;

public class BasedMapperGeneratorTest {

	public static void main(String[] args) throws Exception {
		// 输出文件在output目录下
		BasedMapperGenerator mapperGenerator = new BasedMapperGenerator();
//		mapperGenerator.generate("stroop_db", "user_login_log", "com.pi.uc.dao");
		mapperGenerator.generate("stroop_db", "user", "com.pi.uc.dao");
//		mapperGenerator.generate("stroop_db", "stroop_invitation", "com.pi.stroop.dao");
//		mapperGenerator.generate("stroop_db", "stroop_diagnosis_history", "com.pi.stroop.dao");
//		mapperGenerator.generate("stroop_db", "base_partner_info", "com.pi.config.dao");
//		mapperGenerator.generate("stroop_db", "base_partner_config", "com.pi.config.dao");
	}
}
