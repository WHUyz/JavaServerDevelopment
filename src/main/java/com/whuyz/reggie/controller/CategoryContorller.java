package com.whuyz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whuyz.reggie.common.R;
import com.whuyz.reggie.entity.Category;
import com.whuyz.reggie.entity.Employee;
import com.whuyz.reggie.service.CategoryService;
import com.whuyz.reggie.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryContorller {
    @Autowired
    private CategoryService categoryService = new CategoryServiceImpl();

    //添加菜品分类
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("接收到客户端的申请数据：{}", category.toString());
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {}, pageSize = {}",page, pageSize);
        //构造分页构造器
        //构造条件构造器
        //执行查询
        Page<Category> pageinfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        //添加排序条件
        categoryService.page(pageinfo, queryWrapper);
        return R.success(pageinfo);
    }
   //删除菜系
   @DeleteMapping
    public R<String> deleteById(Long ids){
        log.info("删除id号为：{}的菜系", ids);

        categoryService.remove(ids);
        return R.success("删除成功");
   }
   //根据id修改分类信息
   @PutMapping
    public R<String> modify(@RequestBody Category category){
        log.info("前端请求数据：{}" ,category.toString());
        categoryService.updateById(category);
        return R.success("修改分类成功");
   }

   @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null,Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
   }
}
