package com.spc.coupon.template.dao;

import com.spc.coupon.template.dao.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JpaRepository 提供了findBy + 属性 方法
 * JpaRepository支持接口规范方法名查询。
 * 意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现
 *
 * 可以通过内置的 save 方法完成对象的创建和更新，也可以使用内置的 delete 方法删除数据
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate,Long> {

//    根据shopid查询所有优惠券模板
   List<CouponTemplate> findAllByShopId(Long shopId);

//   多Id的In查询加分页（拼写时会自动关联出CouponTemplate中的字段！！！太强了吧）
//    特殊的参数： 还可以直接在方法的参数上加入分页Pageable或排序Sort 的参数
   Page<CouponTemplate> findAllByIdIn(List<Long> Id, Pageable pageable);

//   根据shop ID + 可用状态查询店铺有多少券模板
   Integer countByShopIdAndAvailable(Long shopId, Boolean available);


//   将优惠券设置为不可用（连sql里面的都有提示，太友好了！！！！）
   @Modifying
   @Query("update CouponTemplate c set c.available = 0 where c.id=:id")
   int makeCouponUnavailable(@Param("id")Long id);

}
