package com.whuyz.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.whuyz.reggie.common.BaseContext;
import com.whuyz.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//检查用户是否已经登录
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符写法
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取本次请求的URL
        String url = request.getRequestURI();
        log.info("拦截到请求：{}",request.getRequestURI());
        //2.判断本次请求是否需要处理
            //定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"};
        boolean check = Check(url, urls);
        //3.如果不处理，需要放行
        if (check){
            log.info("本次请求不需要处理：{}",request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        //4-1.判断移动端用户登录状态，如果已登录则直接放行
        if(request.getSession().getAttribute("employee")!=null){
              log.info("用户已登录，用户id为{}", request.getSession().getAttribute("employee"));
              Long empId = (Long) request.getSession().getAttribute("employee");

              BaseContext.setCurrentId(empId);
              filterChain.doFilter(request, response);
              return;
          }
        //4-2.判断移动端用户登录状态，如果已登录则直接放行
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已登录，用户id为{}", request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录！！！");
        //5.如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    //路径匹配，检查本次请求是否需要放行
    public boolean Check(String requestURL, String[] urls)
    {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if(match){
                return true;
            }
        }
      return false;
    }
}

