package org.kys.clothing.controller;

import org.kys.clothing.Good.GoodsBean;
import org.kys.clothing.discounts.DiscountsBean;
import org.kys.clothing.returnI.DiscountBeanList;
import org.kys.clothing.service.DiscountsService;
import org.kys.util.PageUtil;
import org.kys.util.datePackaging.ReturnListDataPackaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountsController {

    @Autowired
    DiscountsService discountsService;

    /**
     * 获得所有的折扣商品信息
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("get_discounts_goods")
    public List<GoodsBean> getAllGoodsInfo(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "page_size", defaultValue = "-1") int pageSize) {
        pageSize = pageSize == -1 ? PageUtil.EACH_PAGE_SIZE : pageSize;
        List<GoodsBean> list = discountsService.getAllCountsService(page, pageSize);
        return list;
    }

    /**
     * 获得指定sku商品的优惠信息
     *
     * @param sku
     * @return
     */
    @RequestMapping("get_discounts_information")
    public DiscountsBean getDiscountsInformation(@RequestParam("sku") String sku) {
        return discountsService.getDiscountsInformationBySku(sku);
    }

    /**
     * 获得指定的商品详情
     *
     * @param sku
     * @return
     */
    @RequestMapping("get_goods_with_discounts")
    public GoodsBean getGoodsinfoWithDisCounts(@RequestParam("sku") String sku) {
        return discountsService.getGoodsInfoWithDiscounts(sku);
    }

    @RequestMapping("get_discount")
    public DiscountBeanList getDiscount(@RequestParam("sku") String sku, @RequestParam("page") int page) {
        if (sku.equals("")) {
            sku = null;
        }
        return discountsService.getDiscount(sku, page);
    }

    @RequestMapping("update_discout")
    public boolean updateDiscount(@RequestParam("sku") String sku, @RequestParam("info") String info, @RequestParam("money") int money) {

        DiscountsBean discountsBean = this.getDiscountsInformation(sku);
        if (discountsBean != null) {
            return discountsService.updateDiscount(sku, info, money);
        }else{
            return discountsService.addNewDiscount(sku,info,money);
        }
    }
}
