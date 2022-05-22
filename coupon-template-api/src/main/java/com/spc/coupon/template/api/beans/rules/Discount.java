package com.spc.coupon.beans.rules;

public class Discount {

//    金额以分为单位，用Long存。比使用 Double 到处转换 BigDecimal

//    对于满减卷 - quota是减掉的钱数，单位是分
//    对于打折券 - quota是折扣（以100表示原价），例95就是95折
//    对于随机立减券 - quota是最高的随机立减券
//    对于晚间特别优惠券 - quota是日间优惠券，晚间优惠翻倍
    private Long quota;

//    订单最低要求达到多少钱才能用优惠券，单位为分
    private Long threshold;
}
