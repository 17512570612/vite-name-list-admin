<template>
    <div class="header-btns">
        <a-button class="go-back" @click="useGo()">
            <template #icon>
                <LeftOutlined />
            </template>
            返回
        </a-button>
        <a-button type="primary" class="export">
            <template #icon>
                <DownloadOutlined />
            </template>
            导出
        </a-button>
    </div>
    <div style="margin-bottom: 20px;" v-for="(item, index) in mork" :key="index">
        <a-table :columns="columns" :data-source="item.data" :pagination="false" size="small" bordered>
            <template #title>
                <b>扫描内容 {{ index + 1 }}: [<i style="color: blue;">{{ item.type }}</i>] 黄钢城</b>
            </template>
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'attribute'">
                    <a-badge dot v-if="record.attribute">
                        <a-tag color="#3b5999">{{ record.attribute }}</a-tag>
                    </a-badge>
                </template>
                <template v-if="column.key === 'matching'">
                    <div style="color: red;">{{ record.matching }}</div>
                </template>
                <template v-if="column.key === 'country'">
                    <a-tooltip placement="topLeft">
                        <template #title>{{ record.country }}</template>
                        <a-tag style="margin-right: 5px;" v-for="item of record.country.split(',')">{{ item }}</a-tag>
                    </a-tooltip>
                </template>
                <template v-if="column.key === 'action'">
                    <a-button type="link" @click="showModal()">查看</a-button>
                </template>
            </template>
            <template #emptyText>没有找到符合条件的记录</template>
        </a-table>
    </div>
    <Modal :visible.sync="visible" @update:modal-visible="updateVisible"></Modal>
</template>

<script setup lang="ts">
import Modal from './components/Modal.vue'
import { ColumnGroupType, ColumnType } from "ant-design-vue/es/table";

const columns: (ColumnGroupType<any> | ColumnType<any>)[] = [
    {
        title: '序号',
        dataIndex: 'key',
        align: 'center'
    },
    {
        title: '名单属性',
        key: 'attribute',
        dataIndex: 'attribute',
        align: 'center'
    },
    {
        title: '名单编号',
        dataIndex: 'id',
        align: 'center'
    },
    {
        title: '名单类型',
        dataIndex: 'types',
        align: 'center'
    },
    {
        title: '相似度',
        dataIndex: 'similarity',
        align: 'center'
    },
    {
        title: '名单匹配内容',
        key: 'matching',
        dataIndex: 'matching',
        align: 'center'
    },
    {
        title: '国别',
        key: 'country',
        dataIndex: 'country',
        align: 'center',
        ellipsis: true
    },
    {
        title: '操作',
        key: 'action',
        align: 'center'
    },
];

const mork = [
    {
        type: 'NAME',
        data: [
            {
                key: '1',
                attribute: 'black',
                id: 'wc1007379',
                types: '政要名单',
                similarity: 1,
                matching: '黄钢城',
                country: 'CHN,CN,HKG,HK,SGP,SG'
            }
        ]
    },
    {
        type: 'ID',
        data: []
    },
    {
        type: 'COUNTRY',
        data: []
    },
    {
        type: 'ADDRESS',
        data: []
    },
    {
        type: 'RECORDID',
        data: []
    }
];

const router = useRouter()

const useGo = () => {
    router.back()
}

const visible = ref(false);

const showModal = () => {
    visible.value = true;
};

const updateVisible = (newVisible: boolean) => {
    visible.value = newVisible;
};
</script>

<style scoped>
.header-btns {
    width: 100%;
    margin-bottom: 20px;

    .go-back {
        margin-right: 10px;
    }
}

.ant-tag {
    margin: 0;
}
</style>