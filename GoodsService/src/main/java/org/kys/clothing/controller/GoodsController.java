package org.kys.clothing.controller;

import org.kys.clothing.GoodsBean;
import org.kys.clothing.discounts.DiscountsBean;
import org.kys.clothing.fegin.DiscountsFegin;
import org.kys.clothing.service.GoodsService;
import org.kys.util.PageUtil;
import org.kys.util.datePackaging.ReturnListDataPackaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    public GoodsService goodsService;

    @Autowired
    DiscountsFegin discountsFegin;
    /**
     * 查询所有的服装信息
     *
     * @param page     表示当前要查询的页码
     * @param pageSize 分页每页的大小 -1 表示大侠为空
     * @return 返回所有需要的服装信息标签 按照上架时间进行排序
     */
    @RequestMapping("get_all_goods")
    public ReturnListDataPackaging getAllGoodsInfo(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "page_size", defaultValue = "-1") int pageSize) {
        pageSize = pageSize == -1 ? PageUtil.EACH_PAGE_SIZE : pageSize;
        List<GoodsBean> list = goodsService.getAllGoodsInfo(page - 1, pageSize);
        return PageUtil.getReturnListPackaging(list);
    }

    /**
     * 查询服装的优惠信息
     * @param page 同上
     * @param pageSize 同上
     * @return
     */
    @RequestMapping("get_discounts_goods")
    public ReturnListDataPackaging getDiscountsGoods(@RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "page_size", defaultValue = "-1") int pageSize) {
        pageSize = pageSize == -1 ? PageUtil.EACH_PAGE_SIZE : pageSize;
        List<GoodsBean> list = discountsFegin.getDiscountsGoods(page,pageSize);
        return PageUtil.getReturnListPackaging(list);
    }

    /**
     * 获得商品的详情
     * @param sku
     * @return
     */
    @RequestMapping("get_goods_information")
    public GoodsBean getGoodsInformation(@RequestParam("sku")String sku){
        DiscountsBean discountsBean = discountsFegin.getDiscountsInformationBySku(sku);
        return goodsService.getGoodsInformation(discountsBean,sku);
    }
}