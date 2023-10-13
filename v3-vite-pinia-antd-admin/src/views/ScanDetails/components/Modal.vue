<template>
    <a-modal v-model:visible="visible" :closable="false" :maskClosable="true" width="60%" cancel-text="取消" ok-text="确定" @cancel="handleModalClose" @ok="handleModalClose">
        <template #title>详细信息</template>
        <div style="max-height: 450px; overflow: hidden; overflow-y: auto;">
            <a-descriptions size="small" :column="2" bordered>
                <a-descriptions-item label="编号">{{ details?.id }}</a-descriptions-item>
                <a-descriptions-item label="原始状态">{{ details?.status }}</a-descriptions-item>
                <a-descriptions-item label="生效日期">{{ details?.effectiveDate }}</a-descriptions-item>
                <a-descriptions-item label="失效日期">{{ details?.expiringDate }}</a-descriptions-item>
                <a-descriptions-item label="国籍信息" :span="2">
                    <template v-for="item in details?.nationality.split(',')">
                        <a-tag style="margin-right: 5px;">{{ item }}</a-tag>
                    </template>
                </a-descriptions-item>
                <a-descriptions-item label="扫描类型">{{ details?.scanType }}</a-descriptions-item>
                <a-descriptions-item label="审批人">{{ details?.approver }}</a-descriptions-item>
                <a-descriptions-item label="最后维护人">{{ details?.lastMaintainer }}</a-descriptions-item>
                <a-descriptions-item label="最后维护时间">{{ details?.lastMaintenanceTimeL }}</a-descriptions-item>
                <a-descriptions-item label="制裁来源(国家、组织)">{{ details?.sourceOfSanctions }}</a-descriptions-item>
                <a-descriptions-item label="名单类型">{{ details?.TypeOfSanctions }}</a-descriptions-item>
            </a-descriptions>
            <a-tabs v-model:activeKey="activeKey" :tab-position="mode" style="margin-top: 20px;" @change="tabsChange(activeKey)">
                <a-tab-pane v-for="(item, index) in tabs" :key="index + 1" :tab="item.name">
                    <!-- 名称信息/证件信息 -->
                    <a-table v-if="activeKey === NAME_INFO_TAB_INDEX || activeKey === DOCUMENT_INFO_TAB_INDEX" :columns="tabData.columns" :data-source="tabData.data" size="small" :pagination="false" bordered />
                    <!-- 地址信息 -->
                    <a-table v-else-if="activeKey === ADDRESS_INFO_TAB_INDEX" :columns="tabData.columns" :data-source="tabData.data" size="small" :pagination="false" bordered>
                        <template #bodyCell="{ column, record }">
                            <div v-if="column.dataIndex === 'address'" style="text-align: left;">{{ record.address }}</div>
                        </template>
                    </a-table>
                    <!-- 基础信息 -->
                    <a-descriptions :labelStyle="{ width: '150px' }" size="small" :column="2" bordered v-else-if="activeKey === BASIC_INFO_TAB_INDEX">
                        <a-descriptions-item label="名单类型">{{ basic.sanctionType }}</a-descriptions-item>
                        <a-descriptions-item label="活跃状态">{{ basic.activeStatus }}</a-descriptions-item>
                        <a-descriptions-item label="是否死亡">{{ basic.isDeceased }}</a-descriptions-item>
                        <a-descriptions-item label="性别">{{ basic.gender }}</a-descriptions-item>
                        <a-descriptions-item label="出生日期">{{ basic.dateOfBirth }}</a-descriptions-item>
                        <a-descriptions-item label="出生地">{{ basic.placeOfBirth }}</a-descriptions-item>
                        <a-descriptions-item label="死亡日期">{{ basic.dateOfDeath }}</a-descriptions-item>
                        <a-descriptions-item label="国籍">{{ basic.nationality }}</a-descriptions-item>
                        <a-descriptions-item label="居住地" :span="2">
                            <template v-for="item in basic.residence.split(',')">
                                <a-tag style="margin-right: 5px;">{{ item }}</a-tag>
                            </template>
                        </a-descriptions-item>
                        <a-descriptions-item label="名单描述（二级）">{{ basic.twoLevel }}</a-descriptions-item>
                        <a-descriptions-item label="名单描述（三级）">{{ basic.threeLevel }}</a-descriptions-item>
                        <a-descriptions-item label="制裁提示">{{ basic.prompt }}</a-descriptions-item>
                    </a-descriptions>
                    <!-- 船舶信息 -->
                    <a-table v-else :columns="tabData.columns" :data-source="tabData.data" size="small" :pagination="false" bordered />
                </a-tab-pane>
            </a-tabs>
        </div>
    </a-modal>
</template>
  
<script lang="ts" setup>
import type { TabsProps } from "ant-design-vue";
const NAME_INFO_TAB_INDEX = 1;
const DOCUMENT_INFO_TAB_INDEX = 2;
const ADDRESS_INFO_TAB_INDEX = 3;
const BASIC_INFO_TAB_INDEX = 4;

const props = defineProps({
    visible: Boolean,
});

const emit = defineEmits(["update:modalVisible"]);

const BASIC_INFORMATION = {
    id: "wc1007379",
    status: "更新",
    effectiveDate: "2023-06-21 18:00:00",
    expiringDate: "2030-06-21 18:00:00",
    nationality: "CHN,CN,HKG,HK,SGP,SG",
    scanType: "个人",
    sourceOfSanctions: "US",
    approver: "管理员",
    lastMaintainer: "管理员",
    lastMaintenanceTimeL: "2023-06-21 18:00:00",
    TypeOfSanctions: "政要名单",
};

const NAME_INFO_COLUMNS = [
    {
        title: "名称类别",
        dataIndex: "nameType",
        align: "center",
    },
    {
        title: "名称信息",
        dataIndex: "nameInfo",
        align: "center",
    },
];

const NAME_INFO_DATA = [
    {
        nameType: "PRIMARY NAME",
        nameInfo: "Kwong Shing WONG",
    },
    {
        nameType: "ALIAS",
        nameInfo: "HUANG Gang Cheng",
    },
    {
        nameType: "ALIAS",
        nameInfo: "HUANG Gangcheng",
    },
    {
        nameType: "ALIAS",
        nameInfo: "WONG Frank",
    },
    {
        nameType: "ALIAS",
        nameInfo: "WONG Kwong Shing Frank",
    },
    {
        nameType: "FOREIGN_ALIAS",
        nameInfo: "7806 6921 1004",
    },
    {
        nameType: "FOREIGN_ALIAS",
        nameInfo: "黃罁城",
    },
    {
        nameType: "FOREIGN_ALIAS",
        nameInfo: "黄钢城",
    },
];

const DOCUMENT_INFO_COLUMNS = [
    {
        title: "证件类别",
        dataIndex: "nameType",
        align: "center",
    },
    {
        title: "证件信息",
        dataIndex: "nameInfo",
        align: "center",
    },
];

const DOCUMENT_INFO_DATA: any = [];

const ADDRESS_INFO_COLUMNS = [
    {
        title: "地址信息",
        dataIndex: "address",
        align: "center",
    },
];

const ADDRESS_INFO_DATA = [
    {
        address: "Beijing,Beijing,CHINA",
    },
    {
        address: "Hong Kong,HONG KONG",
    },
    {
        address: "Singapore City,Singapore,SINGAPORE",
    },
];

const TAB_BASIC_INFORMATION = {
    sanctionType: "政要名单",
    activeStatus: "活跃",
    isDeceased: "",
    gender: "男",
    dateOfBirth: "1948-01-08",
    placeOfBirth: "",
    dateOfDeath: "",
    nationality: "",
    residence: "CHN,CN,HKG,SGP",
    twoLevel: "INDIVIDUAL",
    threeLevel: "PEP N",
    prompt: "",
};

const visible = ref(props.visible);
const details = ref(BASIC_INFORMATION);
const basic = ref(TAB_BASIC_INFORMATION);

watch(() => props.visible, (newVal) => {
    visible.value = newVal;
    if (newVal) defaultValue();
});

const handleModalClose = () => {
    emit("update:modalVisible", false);
};

const defaultValue = () => {
    activeKey.value = 1;
    tabData.columns = NAME_INFO_COLUMNS;
    tabData.data = NAME_INFO_DATA;
};

const mode = ref<TabsProps["tabPosition"]>("top");
const activeKey = ref(1);

const tabs = [
    { name: "名称信息" },
    { name: "证件信息" },
    { name: "地址信息" },
    { name: "基础信息" },
    { name: "船舶信息" },
    { name: "职务信息" },
    { name: "制裁来源" },
    { name: "关联名单" },
    { name: "备注" },
    { name: "照片" },
];

const tabsData = {
    [NAME_INFO_TAB_INDEX]: {
        columns: NAME_INFO_COLUMNS,
        data: NAME_INFO_DATA,
    },
    [DOCUMENT_INFO_TAB_INDEX]: {
        columns: DOCUMENT_INFO_COLUMNS,
        data: DOCUMENT_INFO_DATA,
    },
    [ADDRESS_INFO_TAB_INDEX]: {
        columns: ADDRESS_INFO_COLUMNS,
        data: ADDRESS_INFO_DATA,
    },
};

let tabData = reactive<any>({
    columns: NAME_INFO_COLUMNS,
    data: NAME_INFO_DATA
});

const tabsChange = (key: number) => {
    tabData = tabsData[key as keyof typeof tabsData] || { columns: [], data: [] };
};
</script>
  
<style lang="scss" scoped></style>
  