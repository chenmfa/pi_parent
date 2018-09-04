package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePartnerSubscription;
import com.pi.nbcenter.device.entity.auto.BasePartnerSubscriptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePartnerSubscriptionMapper {
    long countByExample(BasePartnerSubscriptionExample example);

    int deleteByExample(BasePartnerSubscriptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasePartnerSubscription record);

    int insertSelective(BasePartnerSubscription record);

    List<BasePartnerSubscription> selectByExample(BasePartnerSubscriptionExample example);

    BasePartnerSubscription selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasePartnerSubscription record, @Param("example") BasePartnerSubscriptionExample example);

    int updateByExample(@Param("record") BasePartnerSubscription record, @Param("example") BasePartnerSubscriptionExample example);

    int updateByPrimaryKeySelective(BasePartnerSubscription record);

    int updateByPrimaryKey(BasePartnerSubscription record);
}