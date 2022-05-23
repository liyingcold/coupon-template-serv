package com.spc.coupon.template.impl.converter;

import com.spc.coupon.template.api.beans.CouponTemplateInfo;
import com.spc.coupon.template.dao.entity.CouponTemplate;

public class CouponTemplateConvertr {

    public static CouponTemplateInfo convertToTemplateInfo(CouponTemplate template){

        return CouponTemplateInfo.builder()
                .id(template.getId())
                .name(template.getName())
                .desc(template.getDescription())
                .type(template.getCategory().getCode())
                .shopId(template.getShopId())
                .available(template.getAvailable())
                .rule(template.getRule())
                .build();
    }
}
