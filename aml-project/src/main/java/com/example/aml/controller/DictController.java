package com.example.aml.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aml.common.Response;
import com.example.aml.model.request.DictListRequest;
import com.example.aml.model.system.Dict;
import com.example.aml.model.system.DictItem;
import com.example.aml.service.DictItemService;
import com.example.aml.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DictController {
    @Autowired
    DictService dictService;
    @Autowired
    DictItemService dictItemService;

    @PostMapping("/dict/add")
    public Response createDict(@RequestBody Dict request) {
        try {
            Dict dict = new Dict();
            BeanUtils.copyProperties(request, dict);
            dictService.saveOrUpdate(dict);
            List<DictItem> list = request.getList();
            list.forEach(e -> {
                e.setRelatedId(request.getId());
                dictItemService.saveOrUpdate(e);
            });
            return Response.success(dict);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/dict/list")
    public Response queryDict(DictListRequest request) {
        Page<Dict> list = dictService.findAll(request);
        return Response.success(list);
    }

    @GetMapping("/dictItem/list")
    public Response queryDictItem(Dict request) {
        List<DictItem> list = dictItemService.findAll(request.getId());
        return Response.success(list);
    }

    @GetMapping("/dictItem/delete")
    public Response deleteDictItem(DictItem request) {
        try {
            dictItemService.removeById(request.getId());
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/dict/dictArray")
    public Response queryDictArray() {
        try {
            List<Dict> dictList = dictService.list();  // 获取所有字典
            Map<String, List<DictItem>> dictMap = dictList.stream()
                    .collect(Collectors.toMap(Dict::getCode, dict -> dictItemService.findAll(dict.getId())));
            return Response.success(dictMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }
}
