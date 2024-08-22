package com.lab08.main.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lab08.main.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Globalinterceptor implements HandlerInterceptor {
    @Autowired
    CategoryService categoryService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle,
            ModelAndView modelAndView)
            throws Exception {
        request.setAttribute("cates", categoryService.findAll());
    }
}
