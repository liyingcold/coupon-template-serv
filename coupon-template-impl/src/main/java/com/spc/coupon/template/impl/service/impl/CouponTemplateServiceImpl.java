package com.spc.coupon.template.impl.service.impl;

import com.spc.coupon.template.api.beans.CouponTemplateInfo;
import com.spc.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.spc.coupon.template.api.beans.TemplateSearchParams;
import com.spc.coupon.template.api.enums.CouponType;
import com.spc.coupon.template.dao.CouponTemplateDao;
import com.spc.coupon.template.dao.entity.CouponTemplate;
import com.spc.coupon.template.impl.converter.CouponTemplateConvertr;
import com.spc.coupon.template.impl.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

//    需要使用到dao里面的具体方法，所以先注入
    @Autowired
    private CouponTemplateDao templateDao;

    /**
     * 创建优惠券模板
     * @param request
     * @return
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
//        控制优惠券数量?不是很明白为什么要控制，具体控制为多少个的依据也不知道。
        if (request.getShopId() != null){
            Integer count = templateDao.countByShopIdAndAvailable(request.getShopId(),true);
            if (count>= 100){
                log.error("超过门店允许创建的最大数量");
//                UnsupportedOperationException 是用于表明操作不支持的异常
                throw new UnsupportedOperationException("超过门店允许创建的最大数量");
            }
        }
//        创建优惠券
        CouponTemplate template=CouponTemplate.builder()
                .name(request.getName())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .available(true)
                .shopId(request.getShopId())
                .rule(request.getRule())
                .build();
        template=templateDao.save(template);
        return CouponTemplateConvertr.convertToTemplateInfo(template);
    }

    @Override
    public CouponTemplate cloneTemplate(Long templateId) {
        return null;
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        return null;
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        return null;
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        return null;
    }
}
