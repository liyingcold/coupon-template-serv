package com.spc.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateSearchParams {
    private Long id;
    private String name;
    private String type;
    private Long ShopId;
    private Boolean available;
    private int page;
    private int pageSize;
}
