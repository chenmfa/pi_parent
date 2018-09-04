package com.pi.automation.db.test;

import com.pi.automation.db.generator.tool.MapperGenerator;

public class MapperGeneratorTest {

	public static void main(String[] args) throws Exception {
		// 输出文件在output目录下
		MapperGenerator mapperGenerator = new MapperGenerator();

//		mapperGenerator.generate("<db_name>", "<table_name>", "<parent_package>");
//		mapperGenerator.generate("dsm_apollo_db_dev", "sc_judge_business", "com.dsm.apollo.coreorder.dao");
//		mapperGenerator.generate("dsm_apollo_db_dev", "sc_judge_content", "com.dsm.apollo.coreorder.dao");
//		mapperGenerator.generate("dsm_apollo_db_dev", "sc_judge_level", "com.dsm.apollo.coreorder.dao");
//		mapperGenerator.generate("dsm_apollo_db_dev", "sc_judge_tag", "com.dsm.apollo.coreorder.dao");
		mapperGenerator.generate("stripe_db", "user", "com.pi.usercenter.dao");
	}
}
