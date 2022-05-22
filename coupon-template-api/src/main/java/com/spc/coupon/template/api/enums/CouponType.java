package com.spc.coupon.enums;

import com.sun.org.glassfish.gmbal.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.stream.Stream;

/**
 * 优惠券类型
 */
@Getter
@AllArgsConstructor
public enum CouponType {
        UNKNOWN("unknown","0"),
        MONEY_OFF("满减卷","1"),
        DISCOUNT("打折","2"),
        RANDOM_DISCOUNT("随机减","3"),
        LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券","4");

        private String description;
        private String code;

    /**
     * 根据优惠券的编码返回对应的枚举对象
     * values()//枚举类里面的value()方法会返回当前枚举数据组成的的一个数组
     * Stream.of()//用于为给定的元素创建顺序流
     * .filter()//过滤出code对应的值，.equalsIgnoreCase不区分大小写
     * .findFirst()//从流中取第一个元素
     * .orElse(UNKNOWN)//一个都没匹配到，走默认值UNKNOWN
    * @param code
     * @return
     */
    public static com.spc.coupon.enums.CouponType convert(String code) {
            return Stream.of(values())
                    .filter(bean ->bean.code.equalsIgnoreCase(code))
                    .findFirst()
                    .orElse(UNKNOWN);
        }
}
