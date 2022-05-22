package com.spc.coupon.template.dao.converter;

import com.alibaba.fastjson.JSON;
import com.spc.coupon.template.api.beans.rules.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RuleConverter implements AttributeConverter<TemplateRule,String> {
    @Override
    public String convertToDatabaseColumn(TemplateRule attribute) {
        return JSON.toJSONString(attribute);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String dbData) {
        return JSON.parseObject(dbData,TemplateRule.class);
    }
}
