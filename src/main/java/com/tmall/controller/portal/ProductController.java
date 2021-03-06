package com.tmall.controller.portal;


import com.github.pagehelper.PageInfo;
import com.tmall.entity.vo.ReturnBean;
import com.tmall.entity.vo.ProductDetailVO;
import com.tmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 *
 */
@RequestMapping("product")
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 根据产品ID获取产品细节
     *
     * @param productId 产品ID
     * @return
     */
    @RequestMapping(value = "{productId}/detail", method = {RequestMethod.GET})
    public ReturnBean detail(@PathVariable("productId") Integer productId) {

        ProductDetailVO productDetailVO = productService.getProductDetail(productId);

        return productDetailVO == null || productDetailVO.getStatus() != 0 ?
                ReturnBean.error("产品不存在或已下架", 1) : ReturnBean.success("产品查询成功", productDetailVO);
    }

    /**
     * 根据品类ID或关键字查询产品列表
     *
     * @param categoryId 品类ID
     * @param orderBy    排序规则
     * @param pageNum    页码
     * @param pageSize   单页大小
     * @return
     */
    @RequestMapping("listByCategoryId")
    public ReturnBean listByCategoryId(Integer categoryId,
                                       @RequestParam(required = false) String orderBy,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (categoryId == null) return ReturnBean.error("查询参数异常", 1);

        PageInfo pageInfo = productService.listByCategoryId(categoryId, orderBy, pageNum, pageSize);

        return ReturnBean.success(null,pageInfo);
    }

    /**
     * 根据品类ID或关键字查询产品列表
     *
     * @param keywords 查询关键字
     * @param orderBy  排序规则
     * @param pageNum  页码
     * @param pageSize 单页大小
     * @return
     */
    @RequestMapping("listByKeywords")
    public ReturnBean listByKeywords(String keywords,
                                     @RequestParam(required = false) String orderBy,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (!StringUtils.hasText(keywords) || (orderBy != null && !orderBy.matches("[A-Za-z]+_((desc)|(asc))"))) {
            return ReturnBean.error("查询参数异常", 1);
        }

        PageInfo pageInfo = productService.listByKeywords(keywords, orderBy, pageNum, pageSize);
        return ReturnBean.success(null, pageInfo);
    }


}
