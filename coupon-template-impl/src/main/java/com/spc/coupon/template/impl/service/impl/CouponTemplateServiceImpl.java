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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 复制优惠券模板
     * @param templateId
     * @return
     */
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        CouponTemplate source= templateDao.findById(templateId)
                .orElseThrow(()->new IllegalArgumentException("template Id 不存在"));
        CouponTemplate target=new CouponTemplate();
//        spring还有这么个好东西！我好菜，我竟然不知道...
//        某热心网友的提示：1.Spring的BeanUtils的CopyProperties方法需要对应的属性有getter和setter方法；
//        2.如果存在属性完全相同的内部类，但是不是同一个内部类，即分别属于各自的内部类，则spring会认为属性不同，不会copy；
//        3.泛型只在编译期起作用，不能依靠泛型来做运行期的限制；
//        4.最后，spring和apache的copy属性的方法源和目的参数的位置正好相反，所以导包和调用的时候都要注意一下。
//        5.对象和实体类的set 方法逻辑需要保证一致，否则出现问题会查到你怀疑人生。
        BeanUtils.copyProperties(source,target);

        target.setAvailable(true);
        target.setId(null);
        templateDao.save(target);
//        无语我自己，照着起名字都能拼错CouponTemplateConverter。。。。。
        return CouponTemplateConvertr.convertToTemplateInfo(target);
    }

    /**
     * 根据传入参数进行分页查询
     * @param request
     * @return
     */
    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
//        都写了好多次，还是没记住category需要转换的！！！
       CouponTemplate template=CouponTemplate.builder()
               .shopId(request.getShopId())
               .category(CouponType.convert(request.getType()))
               .available(request.getAvailable())
               .name(request.getName())
               .build();
//       Pageable 得到当前页和页容量
        Pageable page = PageRequest.of(request.getPage(),request.getPageSize());
//        查询所有满足条件的数据
        Page<CouponTemplate> result = templateDao.findAll(Example.of(template),page);
//        将数据转由所有CouponTemplate换成convertToTemplateInfo
        List<CouponTemplateInfo> couponTemplateInfos=result.stream()
                .map(CouponTemplateConvertr::convertToTemplateInfo)
                .collect(Collectors.toList());

        PagedCouponTemplateInfo response =PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    /**
     * 通过Id查询
     * @param id
     * @return
     */
    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplate> template=templateDao.findById(id);
//        orElse是真的好用，太友好了
        return template.map(CouponTemplateConvertr::convertToTemplateInfo).orElse(null);
    }

    /**
     * 通过优惠券id删除优惠券
     * @param id
     */
    @Override
    public void deleteTemplate(Long id) {
        int rows = templateDao.makeCouponUnavailable(id);
        if (rows == 0){
            throw new IllegalArgumentException("Template not found:"+id);
        }
    }

    /**
     * 批量读取模板
     * @param ids
     * @return 返回一个形如 List<Map<id,convertToTemplateInfo>> 的数据
     */
    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> templateList = templateDao.findAllById(ids);
//        Java 8+允许在接口中加入具体方法。接口中的具体方法有两种，default方法和static方法，identity()就是Function接口的一个静态方法。
//        Function.identity()返回一个输出跟输入一样的Lambda表达式对象，等价于形如t -> t形式的Lambda表达式
//       源码中的例子： Map<String, Student> studentIdToStudent
//     *         students.stream().collect(toMap(Student::getId,
//     *                                         Functions.identity());
        return templateList.stream()
                .map(CouponTemplateConvertr::convertToTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }
}
