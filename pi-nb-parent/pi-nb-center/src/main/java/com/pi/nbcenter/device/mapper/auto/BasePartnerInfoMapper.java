package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePartnerInfo;
import com.pi.nbcenter.device.entity.auto.BasePartnerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePartnerInfoMapper {


    /**
     * 添加数据
     * @param record
     * @return
     */
    int insert(BasePartnerInfo record);

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(BasePartnerInfo record);


    /**
     * 根据id查询信息
     * @param id
     * @return
     */

    BasePartnerInfo selectByPrimaryKey(Integer id);


    /**
     * 查询列表信息
     * @param example
     * @return
     */
    List<BasePartnerInfo> selectByExample(BasePartnerInfoExample example);







    int countByExample(BasePartnerInfoExample example);



    int deleteByExample(BasePartnerInfoExample example);




    int deleteByPrimaryKey(Integer id);






    int insertSelective(BasePartnerInfo record);












    int updateByExampleSelective(@Param("record") BasePartnerInfo record, @Param("example") BasePartnerInfoExample example);

    int updateByExample(@Param("record") BasePartnerInfo record, @Param("example") BasePartnerInfoExample example);



    int updateByPrimaryKey(BasePartnerInfo record);
}