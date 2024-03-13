package com.whuyz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whuyz.reggie.common.R;
import com.whuyz.reggie.entity.Employee;
import com.whuyz.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeContorller {
    @Autowired
    private EmployeeService employeeService; //员工登录
    //两个参数的含义：employee 用于映射请求报文中所传来的用户输入的用户名和密码,request封装客户端的请求报文
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        //1.md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据用户名查询数据库，LambdaQueryWrapper：mybatis plus架构中提供的一个类，用于包装条件查询时的条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3.如果没查到则返回失败
        if(emp == null){
            return R.error("用户不存在！");
        }
        //4.比对密码
        if(emp.getPassword().equals(employee.getPassword())) {
            return R.error("密码错误!");
        }
        //5.查看用户是否处于禁用状态
        if(emp.getStatus() == 0){
            return R.error("用户已被禁用");
        }
        //6.登陆成功，将员工id存入session中并返回成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }
    //员工退出系统
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功!");
    }
    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工:{}", employee.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        //设置初始密码为123456，用md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录用户的id
        employee.setCreateUser((Long)request.getSession().getAttribute("employee"));
        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    //员工信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {}, pageSize = {}, name = {}",page, pageSize, name );
        //构造分页构造器
        //构造条件构造器
        //执行查询
        Page pageinfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like((!StringUtils.isEmpty(name)), Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageinfo, queryWrapper);
        return R.success(pageinfo);
    }
    //修改员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        //根据id修改员工信息
        log.info("员工信息:{}", employee.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("员工信息修改成功！");

    }
    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getbyid(@PathVariable Long id){
        log.info("根据id来查询员工{}",id);
        Employee employee = employeeService.getById(id);
        if(employee == null)
            return R.error("没有查询到员工信息!");
        return R.success(employee);

    }

}
