package generator;

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverrideMergeablePlugin extends PluginAdapter{
  
  private static Logger logger = LoggerFactory.getLogger(OverrideMergeablePlugin.class);
  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }
  
  public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
      IntrospectedTable introspectedTable) {
    try {
      Field field = sqlMap.getClass().getDeclaredField("isMergeable");
      field.setAccessible(true);
      field.setBoolean(sqlMap, true);
    } catch (Exception e) {
      logger.error("设置属性失败",e);
    }
    return true;
  }
}
