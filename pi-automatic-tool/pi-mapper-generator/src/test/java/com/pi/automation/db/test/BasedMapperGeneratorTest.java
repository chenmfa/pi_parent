package com.pi.automation.db.test;

import com.pi.automation.db.generator.tool.BasedMapperGenerator;

public class BasedMapperGeneratorTest {

	public static void main(String[] args) throws Exception {
		// 输出文件在output目录下
		BasedMapperGenerator mapperGenerator = new BasedMapperGenerator();
		mapperGenerator.generate("stripe_db", "user", "com.pi.usercenter.dao");
	}
}
