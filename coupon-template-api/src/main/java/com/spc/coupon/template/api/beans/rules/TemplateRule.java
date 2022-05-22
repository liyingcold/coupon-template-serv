package com.spc.coupon.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券模板规则
 * Data 注解自动生成 getter、setter、toString 等方法
 * NoArgsConstructor 生成无参构造器
 * AllArgsConstructor 全参构造器
 * 一是领券规则，包括每个用户可领取的数量和券模板的过期时间
 * 二是券模板的计算规则
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {

//    可以享受的折扣
    private Discount discount;
//    每个人对多可以领券数
    private Integer limitation;
//    过期时间
    private Long deadline;
}
