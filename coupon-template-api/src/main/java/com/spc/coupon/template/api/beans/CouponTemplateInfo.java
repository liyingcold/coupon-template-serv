package com.spc.coupon.beans;

import com.spc.coupon.beans.rules.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 创建优惠券模板
 * @Builder 作用主要是用来生成对象，并且可以为对象链式赋值。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTemplateInfo {
    private Long id;

    @NotNull
    private String name;//优惠券名称
    @NotNull
    private String desc;//优惠券描述
    @NotNull
    private String Type;//优惠券类型（引用CouponType里的code）
    private Long shopId;//优惠券适用门店 - 若无则为全店通用券

    @NotNull
    private TemplateRule rule;//优惠券使用规则

    private Boolean available;//当前模板是否为可用状态
}
