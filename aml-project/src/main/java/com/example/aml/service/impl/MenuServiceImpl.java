package com.example.aml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.common.Response;
import com.example.aml.mapper.MenuMapper;
import com.example.aml.model.request.MenuRequest;
import com.example.aml.model.system.Menu;
import com.example.aml.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl  extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getMenuTree() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort");
        List<Menu> allMenus = menuMapper.selectList(queryWrapper);
        List<Menu> menuTree = new ArrayList<>();
        for (Menu menu : allMenus) {
            if (menu.getParentId() == null) {
                buildMenuTree(menu, allMenus);
                menuTree.add(menu);
            }
        }
        return menuTree;
    }

    private void buildMenuTree(Menu root, List<Menu> allMenus) {
        List<Menu> children = new ArrayList<>();
        for (Menu menu : allMenus) {
            if(root.getId().equals(menu.getParentId())){
                children.add(menu);
            }
        }
        if(children.size() == 0){
            return;
        }
        root.setChildren(children);
        for (Menu menu : children) {
            buildMenuTree(menu, allMenus);
        }
    }
}
