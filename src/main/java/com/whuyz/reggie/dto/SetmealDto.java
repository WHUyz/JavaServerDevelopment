package com.whuyz.reggie.dto;

import com.whuyz.reggie.entity.Setmeal;
import com.whuyz.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
