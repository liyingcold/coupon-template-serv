package com.spc.coupon.template.impl.controller;

import com.spc.coupon.template.api.beans.CouponTemplateInfo;
import com.spc.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.spc.coupon.template.api.beans.TemplateSearchParams;
import com.spc.coupon.template.impl.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/template")
public class CouponTemplateController {

    @Autowired
    private CouponTemplateService couponTemplateService;

    /**
     * 创建优惠券
     */
    @PostMapping("addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request){
        return couponTemplateService.createTemplate(request);
    }

    /**
     * 赋值模板
     * @param templateId
     * @return
     */
    @PostMapping("cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id")Long templateId){
        return couponTemplateService.cloneTemplate(templateId);
    }

    /**
     * 根据id获取优惠券
     * @param id
     * @return
     */
    @GetMapping("getTemplate")
    public CouponTemplateInfo getTemplate(@RequestParam("id")Long id){
        return couponTemplateService.loadTemplateInfo(id);
    }

    /**
     * 支持分页的查询
     * @param request
     * @return
     */
    @PostMapping("search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request){
        return couponTemplateService.search(request);
    }

    /**
     * 删除优惠券
     * @param id
     */
    @DeleteMapping("deleteTemplate")
    public void deleteTemplate(@RequestParam("id")Long id){
        couponTemplateService.deleteTemplate(id);
    }

    /**
     * 获取模板
     * @param ids
     * @return
     */
    @GetMapping("templateMap")
    public Map<Long,CouponTemplateInfo> getTemplateInfoMap(@RequestParam("ids")Collection<Long> ids){
        return couponTemplateService.getTemplateInfoMap(ids);
    }


}
