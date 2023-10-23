package com.example.aml.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aml.common.Response;
import com.example.aml.mapper.CountryFlowMapper;
import com.example.aml.model.country.CountryList;
import com.example.aml.model.country.FilterCountryRecord;
import com.example.aml.model.country.FilterCountryRecordFlow;
import com.example.aml.model.error.ExcelError;
import com.example.aml.model.request.CountryListRequest;
import com.example.aml.service.CountryFlowService;
import com.example.aml.service.CountryListService;
import com.example.aml.service.CountryService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class FilterCountryRecordController {
    /**
     * @description: Excel 文件中的列名与实体类的属性名的映射关系
     */
    private static final Map<Integer, String> COLUMN_MAPPING = new HashMap<>();
    /**
     * @description: 日期格式化
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @description: 初始化映射关系
     */
    static {
        COLUMN_MAPPING.put(0, "riskLevel");
        COLUMN_MAPPING.put(1, "operate");
        COLUMN_MAPPING.put(2, "source");
        COLUMN_MAPPING.put(3, "businessManagementUnit");
        COLUMN_MAPPING.put(4, "cnName");
        COLUMN_MAPPING.put(5, "enName");
        COLUMN_MAPPING.put(6, "enAbbreviation");
        COLUMN_MAPPING.put(7, "iso2countryCode");
        COLUMN_MAPPING.put(8, "iso3countryCode");
        COLUMN_MAPPING.put(9, "oldName");
        COLUMN_MAPPING.put(10, "aka1");
        COLUMN_MAPPING.put(11, "aka2");
        COLUMN_MAPPING.put(12, "aka3");
        COLUMN_MAPPING.put(13, "maintenanceReasons");
        COLUMN_MAPPING.put(14, "controlRequirements");
        COLUMN_MAPPING.put(15, "continent");
        COLUMN_MAPPING.put(16, "region");
        COLUMN_MAPPING.put(17, "startDate");
        COLUMN_MAPPING.put(18, "expiringDate");
        COLUMN_MAPPING.put(19, "remark");
    }

    @Autowired
    CountryService counrtyService;

    @Autowired
    CountryFlowService countryFlowService;

    @Autowired
    CountryListService countryListService;

    @Autowired
    CountryFlowMapper countryFlowMapper;

    /**
     * @param request
     * @description: 查询国家列表
     */
    @GetMapping("/country/list")
    public Response queryCountryList(CountryListRequest request) {
        Page list = counrtyService.findAll(request);
        return Response.success(list);
    }

    /**
     * @param request
     * @description: 查询国家详情
     */
    @GetMapping("/country/detail")
    public Response queryCountryDetail(CountryListRequest request) {
        FilterCountryRecord country = counrtyService.getById(request.getId());
        return Response.success(country);
    }

    /**
     * @param request
     * @description: 查询国家流水详情
     */
    @GetMapping("/country/flow/detail")
    public Response queryCountryFlowDetail(CountryListRequest request) {
        FilterCountryRecordFlow countryFlow = countryFlowService.getById(request.getId());
        return Response.success(countryFlow);
    }

    /**
     * @param request
     * @description: 查询授权列表
     */
    @GetMapping("/country/authorized/list")
    public Response queryAuthorizedList(CountryListRequest request) {
        try {
            Page list = countryFlowService.queryAuthorizedList(request);
            return Response.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("查询失败");
        }
    }

    /**
     * @param request
     * @description: 新增国家地区
     */
    @PostMapping("/country/add")
    public Response addCountryFlow(@RequestBody FilterCountryRecordFlow request) {
        try {
            FilterCountryRecordFlow filterCountryRecordFlow = new FilterCountryRecordFlow();
            BeanUtils.copyProperties(request, filterCountryRecordFlow);
            filterCountryRecordFlow.setMaintenanceState("3");
            filterCountryRecordFlow.setOperate("ADD");
            countryFlowService.saveOrUpdate(filterCountryRecordFlow);
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("新增失败");
        }
    }

    /**
     * @param request
     * @description: 删除数据
     */
    @GetMapping("/country/delete")
    public Response deleteCountryFlow(Map<String, List<String>> params) {
        try {
            List<String> ids = params.get("ids");
            if (ids.size() > 0) {
                List<FilterCountryRecord> filterCountryRecords = counrtyService.listByIds(ids);
                for (FilterCountryRecord filterCountryRecord : filterCountryRecords) {
                    if (filterCountryRecord.getMaintenanceState().equals("3") || filterCountryRecord.getMaintenanceState().equals("4"))
                        return Response.fail(500, "删除失败,审批中或已删除的数据无法删除", null);
                    delete(filterCountryRecord);
                }
            } else {
                QueryWrapper<FilterCountryRecord> queryWrapper = new QueryWrapper<FilterCountryRecord>();
                queryWrapper.ne("maintenance_state", "3").and(wrapper -> wrapper.ne("maintenance_state", "4"));
                List<FilterCountryRecord> filterCountryRecord = counrtyService.list(queryWrapper);
                filterCountryRecord.forEach(countryRecord -> {
                    delete(countryRecord);
                });
            }
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("删除失败");
        }
    }

    /**
     * @param filterCountryRecord
     * @description: 删除
     */
    private void delete(FilterCountryRecord filterCountryRecord) {
        FilterCountryRecordFlow filterCountryRecordFlow = new FilterCountryRecordFlow();
        filterCountryRecord.setMaintenanceState("3");
        BeanUtils.copyProperties(filterCountryRecord, filterCountryRecordFlow);
        filterCountryRecordFlow.setMaintenanceState("3");
        filterCountryRecordFlow.setOperate("DELETE");
        counrtyService.saveOrUpdate(filterCountryRecord);
        countryFlowService.saveOrUpdate(filterCountryRecordFlow);
    }

    /**
     * @param request
     * @description: 审批通过
     */
    @PostMapping("/country/approved/pass")
    public Response approvedCountryFlow(@RequestBody Map<String, List<String>> params) {
        try {
            List<String> ids = params.get("ids");
            if (ids.size() > 0) {
                for (String id : ids) {
                    FilterCountryRecordFlow filterCountryRecordFlow = countryFlowService.getById(id);
                    pass(filterCountryRecordFlow);
                }
            } else {
                QueryWrapper<FilterCountryRecordFlow> queryWrapper = new QueryWrapper<FilterCountryRecordFlow>();
                queryWrapper.eq("maintenance_state", "3");
                List<FilterCountryRecordFlow> filterCountryRecordFlows = countryFlowMapper.selectList(queryWrapper);
                for (FilterCountryRecordFlow filterCountryRecordFlow : filterCountryRecordFlows) {
                    pass(filterCountryRecordFlow);
                }
            }
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("审批失败");
        }
    }

    /**
     * @param request
     * @description: 通过
     */
    private void pass(FilterCountryRecordFlow filterCountryRecordFlow) {
        if (filterCountryRecordFlow.getOperate().equals("DELETE")) {
            filterCountryRecordFlow.setDelFlag("2");
            filterCountryRecordFlow.setMaintenanceState("4");
        } else {
            filterCountryRecordFlow.setDelFlag("1");
            filterCountryRecordFlow.setMaintenanceState("1");
        }
        // #将审批通过的数据保存到正式表
        FilterCountryRecord filterCountryRecord = new FilterCountryRecord();
        BeanUtils.copyProperties(filterCountryRecordFlow, filterCountryRecord);
        counrtyService.saveOrUpdate(filterCountryRecord);
        // #将审批通过的数据从流水表中删除
        countryFlowService.removeById(filterCountryRecordFlow.getId());
    }

    /**
     * @param request
     * @description: 审批驳回
     */
    @PostMapping("/country/approved/rejected")
    public Response rejectedCountryFlow(@RequestBody Map<String, List<String>> params) {
        try {
            List<String> ids = params.get("ids");
            if (ids.size() > 0) {
                List<FilterCountryRecordFlow> filterCountryRecordFlows = countryFlowService.listByIds(ids);
                for (FilterCountryRecordFlow filterCountryRecordFlow : filterCountryRecordFlows) {
                    rejected(filterCountryRecordFlow);
                }
            } else {
                QueryWrapper<FilterCountryRecordFlow> queryWrapper = new QueryWrapper<FilterCountryRecordFlow>();
                queryWrapper.eq("maintenance_state", "3");
                List<FilterCountryRecordFlow> filterCountryRecordFlows = countryFlowMapper.selectList(queryWrapper);
                for (FilterCountryRecordFlow filterCountryRecordFlow : filterCountryRecordFlows) {
                    rejected(filterCountryRecordFlow);
                }
            }
            return Response.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("审批失败");
        }
    }

    /**
     * @param request
     * @description: 驳回
     */
    private void rejected(FilterCountryRecordFlow filterCountryRecordFlow) {
       if (filterCountryRecordFlow.getOperate().equals("ADD")) {
           countryFlowService.removeById(filterCountryRecordFlow.getId());
       } else {
           FilterCountryRecord filterCountryRecord = new FilterCountryRecord();
           filterCountryRecordFlow.setOperate("ADD");
           filterCountryRecordFlow.setMaintenanceState("1");
           BeanUtils.copyProperties(filterCountryRecordFlow, filterCountryRecord);
           counrtyService.saveOrUpdate(filterCountryRecord);
           countryFlowService.removeById(filterCountryRecordFlow.getId());
       }
    }

    /**
     * @param request
     * @description: 查询国家列表
     */
    @GetMapping("/country/countryList")
    private Response countryList() {
        QueryWrapper<CountryList> queryWrapper = new QueryWrapper<CountryList>();
        List<CountryList> countryList = countryListService.list(queryWrapper);
        return Response.success(countryList);
    }

    /**
     * @description: 国家地区模版下载
     */
    @GetMapping("/country/file")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        File file = new File("/Users/wangyuxing/countryTemp.xls");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    /**
     * @param request
     * @description: EXCEL导入国家列表
     */
    @PostMapping("/country/upload")
    public Response uploadCountry(@RequestParam("file") MultipartFile[] files) throws IOException {
        try {
            List<FilterCountryRecordFlow> excelCountryList = new ArrayList<>();
            for (MultipartFile file : files) {
                List<ExcelError> errors = new ArrayList<>();
                // #1. 将上传的文件保存在服务器上
                Path filePath = Paths.get("/Users/wangyuxing/aml/upload", file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                // #2. 解析 Excel 文件
                List<FilterCountryRecordFlow> FilterCountryRecordFlows = parseExcel(filePath, errors);
                if (errors.size() > 0) {
                    return Response.fail(401, "上传失败", errors);
                }
                // #3. 将解析出来的数据保存到列表返回给前端
                for (FilterCountryRecordFlow filterCountryRecordFlow : FilterCountryRecordFlows) {
                    excelCountryList.add(filterCountryRecordFlow);
                }
            }
            return Response.success(excelCountryList);
        } catch (Exception e) {
            return Response.error("上传失败");
        }
    }

    /**
     * @param filePath
     * @param errors
     * @description: 解析 Excel 文件
     */
    private List<FilterCountryRecordFlow> parseExcel(Path filePath, List<ExcelError> errors) throws IOException {
        List<FilterCountryRecordFlow> records = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(Files.newInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    FilterCountryRecordFlow record = parseRow(row, rowIndex, errors);
                    // #去重
                    boolean existsInDatabase = checkIfRecordExistsInDatabase(record);
                    if (record != null && !records.contains(record) && !existsInDatabase) {
                        record.setMaintenanceState("3");
                        records.add(record);
                        // #将解析出来的数据保存到数据库
                        countryFlowService.saveOrUpdate(record);
                    } else {
                        errors.add(new ExcelError(rowIndex + 1, 6, "英文名称", "该数据已存在"));
                    }
                }
            }
            return records;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param row
     * @param rowIndex
     * @param errors
     * @description: 解析 Excel 行数据
     */
    private FilterCountryRecordFlow parseRow(Row row, Integer rowIndex, List<ExcelError> errors) {
        try {
            FilterCountryRecordFlow record = new FilterCountryRecordFlow();
            for (int columnIndex = 0; columnIndex < COLUMN_MAPPING.size(); columnIndex++) {
                Cell cell = row.getCell(columnIndex);
                String columnName = COLUMN_MAPPING.get(columnIndex);
                String cellValue = (cell != null) ? cell.getStringCellValue() : null;
                setProperty(record, columnName, cellValue, rowIndex + 1, columnIndex + 1, errors);
            }
            return record;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param recordFlow
     * @description: 去重
     */
    private boolean checkIfRecordExistsInDatabase(FilterCountryRecordFlow recordFlow) {
        QueryWrapper<FilterCountryRecord> queryWrapper = new QueryWrapper<FilterCountryRecord>();
        queryWrapper.eq("en_name", recordFlow.getEnName());
        FilterCountryRecord filterCountryRecord = counrtyService.getOne(queryWrapper);
        if (filterCountryRecord != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param record
     * @param propertyName
     * @param propertyValue
     * @param rowIndex
     * @param columnIndex
     * @param errors
     * @description: 设置实体类的属性值
     */
    private void setProperty(FilterCountryRecordFlow record, String propertyName, String propertyValue, Integer rowIndex, Integer columnIndex, List<ExcelError> errors) {
        try {
            switch (propertyName) {
                case "riskLevel":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "名单类型", "不能为空"));
                    } else {
                        if (propertyValue.equals("制裁类国家（地区）名单")) {
                            record.setRiskLevel("SANCTION");
                        } else if (propertyValue.equals("高风险类国家（地区）名单")) {
                            record.setRiskLevel("HIGH");
                        } else if (propertyValue.equals("其他国家（地区）名单")) {
                            record.setRiskLevel("OTHER");
                        } else {
                            errors.add(new ExcelError(rowIndex, columnIndex, "名单类型", "不合法"));
                        }
                    }
                    break;
                case "operate":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "操作类型", "不能为空"));
                    } else {
                        if (propertyValue.equals("新增")) {
                            record.setOperate("ADD");
                        } else if (propertyValue.equals("删除")) {
                            record.setOperate("DELETE");
                        } else {
                            errors.add(new ExcelError(rowIndex, columnIndex, "操作类型", "不合法"));
                        }
                    }
                    break;
                case "source":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "制裁来源", "不能为空"));
                    } else {
                        record.setSource(propertyValue);
                    }
                    break;
                case "businessManagementUnit":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "业管单位", "不能为空"));
                    } else {
                        record.setBusinessManagementUnit(propertyValue);
                    }
                    break;
                case "cnName":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "中文名称", "不能为空"));
                    } else {
                        record.setCnName(propertyValue);
                    }
                    break;
                case "enName":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "英文名称", "不能为空"));
                    } else {
                        record.setEnName(propertyValue);
                    }
                    break;
                case "enAbbreviation":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "英文简称", "不能为空"));
                    } else {
                        record.setEnAbbreviation(propertyValue);
                    }
                    break;
                case "iso2countryCode":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "ISO2国家代码", "不能为空"));
                    } else {
                        if (propertyValue.length() != 2 && !propertyValue.equals("-")) {
                            errors.add(new ExcelError(rowIndex, columnIndex, "ISO2国家代码", "长度必须为2"));
                        } else {
                            record.setIso2countryCode(propertyValue);
                        }
                    }
                    break;
                case "iso3countryCode":
                    if (propertyValue.isEmpty()) {
                        errors.add(new ExcelError(rowIndex, columnIndex, "ISO3国家代码", "不能为空"));
                    } else {
                        if (propertyValue.length() != 3 && !propertyValue.equals("-")) {
                            errors.add(new ExcelError(rowIndex, columnIndex, "ISO3国家代码", "长度必须为3"));
                        } else {
                            record.setIso3countryCode(propertyValue);
                        }
                    }
                    break;
                case "oldName":
                    record.setOldName(propertyValue);
                    break;
                case "aka1":
                    record.setAka1(propertyValue);
                    break;
                case "aka2":
                    record.setAka2(propertyValue);
                    break;
                case "aka3":
                    record.setAka3(propertyValue);
                    break;
                case "maintenanceReasons":
                    record.setMaintenanceReasons(propertyValue);
                    break;
                case "controlRequirements":
                    record.setControlRequirements(propertyValue);
                    break;
                case "continent":
                    record.setContinent(propertyValue);
                    break;
                case "region":
                    record.setRegion(propertyValue);
                    break;
                case "startDate":
                    if (propertyValue == null || propertyValue.isEmpty()) {
                        Date date = new Date();
                        Date parse = dateFormat.parse(date.toString());
                        record.setStartDate(parse);
                    } else {
                        try {
                            Date date = dateFormat.parse(propertyValue);
                            record.setStartDate(date);
                        } catch (Exception e) {
                            errors.add(new ExcelError(rowIndex, columnIndex, "生效日期", "格式不合法"));
                        }
                    }
                    break;
                case "expiringDate":
                    if (propertyValue == null || propertyValue.isEmpty()) {
                        record.setExpiringDate(null);
                    } else {
                        try {
                            Date date = dateFormat.parse(propertyValue);
                            record.setExpiringDate(date);
                        } catch (Exception e) {
                            errors.add(new ExcelError(rowIndex, columnIndex, "失效日期", "格式不合法"));
                        }
                    }
                    break;
                case "remark":
                    record.setRemark(propertyValue);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
