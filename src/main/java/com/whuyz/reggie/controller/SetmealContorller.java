package com.whuyz.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whuyz.reggie.common.R;
import com.whuyz.reggie.dto.SetmealDto;
import com.whuyz.reggie.entity.Setmeal;
import com.whuyz.reggie.entity.SetmealDish;
import com.whuyz.reggie.service.SetmealDishService;
import com.whuyz.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealContorller {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
//    @GetMapping("/list")
//    public R<String> save(Long categoryId)

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("传来的信息：{}", setmealDto.toString());
        setmealService.saveWithDish(setmealDto);

        return R.success("保存成功");
    }


    @GetMapping("/page")
    public R<Page<Setmeal>> page(int page, int pageSize){
        log.info("分页信息：{},{}",page, pageSize);
        Page<Setmeal> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Setmeal::getUpdateTime);
        setmealService.page(page1);
        return R.success(page1);
    }

    @DeleteMapping
    public R<String> delete(List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list1 = setmealService.list(queryWrapper);
        return R.success(list1);
    }

}
