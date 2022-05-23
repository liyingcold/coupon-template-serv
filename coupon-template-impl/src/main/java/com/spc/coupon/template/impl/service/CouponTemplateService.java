package com.spc.coupon.template.impl.service;

import com.spc.coupon.template.api.beans.CouponTemplateInfo;
import com.spc.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.spc.coupon.template.api.beans.TemplateSearchParams;
import com.spc.coupon.template.dao.entity.CouponTemplate;

import java.util.Collection;
import java.util.Map;

public interface CouponTemplateService {

//    创建优惠券模板
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

//    复制
    CouponTemplate cloneTemplate(Long templateId);

//    通过模板ID查询优惠券模板
    PagedCouponTemplateInfo search(TemplateSearchParams request);

//    优惠模板失效
    CouponTemplateInfo loadTemplateInfo(Long id);
//    批量查询
//    Map是模板Id,key是模板详情
    Map<Long,CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);
}

