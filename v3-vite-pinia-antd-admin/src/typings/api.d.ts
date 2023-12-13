/** 返回体相关类型 */
declare interface response<T = any> {
  code: number;
  status: string;
  message: string;
  result: T;
  timestamp: number;
}

declare interface Page<T> {
  countId: string;
  current: number;
  maxLimit: number;
  optimizeCountSql: boolean;
  orders: string[];
  pages: number;
  records: T[];
  searchCount: boolean;
  size: number;
  total: number;
}

/** 用户相关API类型定义 */
declare namespace UserApi {
  interface UserInfo {
    token: string;
    user: User;
  }
  interface User {
    id: string;
    name: string;
    password: string;
    username: string;
  }
}
