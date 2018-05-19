package org.kys.clothing.controller;

import org.kys.clothing.GoodsBean;
import org.kys.clothing.discounts.DiscountsBean;
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

    @RequestMapping("get_discounts_goods")
    public List<GoodsBean> getAllGoodsInfo(@RequestParam(value = "page",defaultValue = "1")int page,
                                                   @RequestParam(value = "page_size",defaultValue = "-1")int pageSize){
        pageSize=pageSize==-1?PageUtil.EACH_PAGE_SIZE:pageSize;
        List<GoodsBean> list = discountsService.getAllCountsService(page,pageSize);
        return list;
    }

    @RequestMapping("get_discounts_information")
    public DiscountsBean getDiscountsInformation(@RequestParam("sku")String sku){
        return discountsService.getDiscountsInformationBySku(sku);
    }

}
