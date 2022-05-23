package com.spc.coupon.template.dao.entity;

import com.spc.coupon.template.api.beans.rules.TemplateRule;
import com.spc.coupon.template.api.enums.CouponType;
import com.spc.coupon.template.dao.converter.CouponTypeConverter;
import com.spc.coupon.template.dao.converter.RuleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name= "coupon_template")
public class CouponTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    状态是否可用
    @Column(name = "available",nullable = false)
    private Boolean available;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

//    适用门店-如果为空，则为全店满减
    @Column(name = "shop_id")
    private Long shopId;


    @Column(name = "type",nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;

    //    CreatedDate可自动装填时间，CreationTimestamp timestamp格式
    @Column(name = "create_time",nullable = false)
    @CreationTimestamp
    @CreatedDate
    private Timestamp createTime;

//    优惠券核算规则，json字符串
    @Column(name = "rule",nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;

}
