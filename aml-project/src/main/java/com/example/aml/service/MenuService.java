package com.example.aml.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.model.system.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getMenuTree();
}
