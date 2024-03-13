package com.whuyz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whuyz.reggie.entity.Category;

public interface CategoryService extends IService<Category>{
  public void remove(Long id);
  public void modify(Category category);
}
