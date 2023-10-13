package com.example.aml.controller;

import com.example.aml.common.Response;
import com.example.aml.model.system.Menu;
import com.example.aml.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    /**
     * @param request
     * @return
     * @description: 添加菜单
     */
    @PostMapping("/menu/add")
    public Response createMenu(@RequestBody Menu request) {
        try {
            Menu menu = new Menu();
            BeanUtils.copyProperties(request, menu);
            menuService.saveOrUpdate(menu);
            return Response.success(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/menu/list")
    public Response getMenu() {
        try {
            List<Menu> menuTree = menuService.getMenuTree();
            return Response.success(menuTree);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/menu/delete")
    public Response deleteMenu(Menu request) {
        try {
            menuService.removeById(request.getId());
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }
}