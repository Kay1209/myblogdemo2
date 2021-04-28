package com.oracle.kays.controller;

import com.oracle.kays.entity.Items;
import com.oracle.kays.json.JsonData;
import com.oracle.kays.json.JsonResult;
import com.oracle.kays.service.ItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Api("商品模块接口")
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Resource
    private ItemsService itemsService;

    //添加单个商品动作
    @ApiOperation("添加单个商品资料")
    @PostMapping("/")
    public JsonData saveItems(@RequestBody Items items, HttpServletRequest request, HttpSession session) {
        System.out.println("执行添加商品动作");
        Items newItem = null;
        try {

            itemsService.save(items);
            return JsonResult.success(newItem, "添加商品成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("添加商品失败！");
        }
    }

    //查询所有商品资料
    @ApiOperation("查询所有商品资料")
    @GetMapping("/")
    public JsonData queryAllItems(HttpServletRequest request, HttpSession session) {
        System.out.println("执行查询全部商品动作");
        List<Items> itemsList = null;
        try {
            Optional<List<Items>> optional = itemsService.findList(null);
            if(optional.isPresent()) {
                itemsList = optional.get();
                return JsonResult.success(itemsList, "查询全部商品成功！");
            }
            return JsonResult.fail("查询全部商品失败！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("查询全部商品失败！");
        }
    }

    //查询单个商品资料
    @ApiOperation("查询单个商品资料")
    @GetMapping("/{id}")
    public JsonData queryItemById(@PathVariable("id") String id, HttpServletRequest request, HttpSession session) {
        System.out.println("执行查询商品动作");
        Items item = null;
        try {
            Optional<Items> optional = itemsService.findById(Integer.parseInt(id));
            if(optional.isPresent()) {
                item = optional.get();
                return JsonResult.success(item, "查询商品成功！");
            }
            return JsonResult.fail("查询全部失败！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("查询全部失败！");
        }
    }

    //更新单个商品资料
    @ApiOperation("更新单个商品资料")
    @PutMapping("/{id}")
    public JsonData queryItemById(@RequestBody Items items, @PathVariable("id") String id, HttpServletRequest request, HttpSession session) {
        System.out.println("执行更新商品动作");
        Items newItem = null;
        try {
            items.setId(Integer.parseInt(id));
            itemsService.save(items);
            return JsonResult.success(newItem, "更新商品成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("更新商品失败！");
        }
    }

    //删除单个商品资料
    @ApiOperation("删除单个商品资料")
    @DeleteMapping("/{id}")
    public JsonData deleteItemById(@PathVariable("id") String id, HttpServletRequest request, HttpSession session) {
        System.out.println("执行删除商品动作");
        try {
            itemsService.deleteById(Integer.parseInt(id));
            return JsonResult.success(null, "删除商品成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return JsonResult.fail("删除商品失败！");
        }
    }
}
