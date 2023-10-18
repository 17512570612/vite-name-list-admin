# 项目介绍

- 西力科技反洗钱名单检测系统

- 基于 Vite 构建的 Vue3 后台管理系统
- 用到的技术栈包含
  - Vue3
  - TypeScript
  - Vue Router@4
  - Pinia
  - Axios
  - Ant Desgin Vue@4.x

## 目录结构

- node_modules ---------- // 依赖包
- src ------------------- // 源码目录
  - assets -------------- // 静态资源
  - components ---------- // 公共组件
  - config -------------- // 配置文件
  - hooks --------------- // 公共 Hooks
  - layout -------------- // 布局组件
  - router -------------- // 路由(静态路由、路由守卫、动态路由)
  - service ------------- // 请求(拦截器、请求封装、接口地址)
  - store --------------- // Pinia 仓库
  - style --------------- // 公共样式
  - typings ------------- // 类型声明
  - utils --------------- // 公共工具
  - views --------------- // 页面组件
  - App.vue ------------- // 根组件
  - main.ts ------------- // 入口文件
- index.html ------------ // 入口 HTML
- package-lock.json ----- // 依赖包的确切版本
- package.json ---------- // 项目核心文件(包含项目元数据及所需的依赖项)
- tsconfig.json --------- // ts 配置文件
- vite.config.ts -------- // vite 配置文件

### 安装依赖

- npm install
- yarn

#### 项目启动

- npm run dev
- yarn run dev
