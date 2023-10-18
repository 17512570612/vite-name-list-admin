/**
 * #验证函数
 * @param value
 * @returns
 */

// 验证非空字段
export const required = (value: string): boolean => {
  return !!value.trim();
};

// 验证电子邮件
export const validateEmail = (email: string): boolean => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
};

// 验证手机号码
export const validatePhoneNumber = (phoneNumber: string): boolean => {
  const regex = /^1[3456789]\d{9}$/;
  return regex.test(phoneNumber);
};

/**
 * #格式化函数
 * @param date
 * @returns
 */

// 日期格式化
export const formatDate = (date: Date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}/${month}/${day}`;
};

// 日期时间格式化
export const formatDateTime = (date: Date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");
  return `${year}/${month}/${day} ${hours}:${minutes}:${seconds}`;
};

// 中文日期格式化
export const formatChineseDate = (date: Date): string => {
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "long",
    day: "numeric",
  };
  return new Intl.DateTimeFormat("zh-CN", options).format(date);
};

// 中文日期时间格式化
export const formatChineseDateTime = (date: Date): string => {
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    second: "numeric",
  };
  return new Intl.DateTimeFormat("zh-CN", options).format(date);
};

// 格式化货币
export const formatCurrency = (value: number): string => {
  const formatter = new Intl.NumberFormat("zh-CN", {
    style: "currency",
    currency: "CNY",
  });
  return formatter.format(value);
};

// 格式化数字为百分比
export const formatPercentage = (value: number): string => {
  return `${(value * 100).toFixed(2)}%`;
};

/**
 * #辅助函数
 * @param obj
 * @returns
 */

// 深度克隆对象
export const deepClone = <T>(obj: T): T => {
  return JSON.parse(JSON.stringify(obj));
};

// 生成随机字符串
export const generateRandomString = (length: number): string => {
  const characters =
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  let result = "";
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * characters.length));
  }
  return result;
};

// 延迟执行函数
export const delay = (ms: number): Promise<void> => {
  return new Promise((resolve) => setTimeout(resolve, ms));
};

/**
 * #字符串处理函数
 * @param str
 * @returns
 */

// 首字母大写
export const capitalizeFirstLetter = (str: string): string => {
  return str.charAt(0).toUpperCase() + str.slice(1);
};

// 检查字符串是否包含某个子串
export const containsSubstring = (str: string, substring: string): boolean => {
  return str.includes(substring);
};

// 移除字符串两端的空格
export const trimString = (str: string): string => {
  return str.trim();
};

/**
 * #数组处理函数
 * @param arr
 * @returns
 */

// 数组去重
export const removeDuplicates = <T>(arr: T[]): T[] => {
  return [...new Set(arr)];
};

// 数组元素随机排序
export const shuffleArray = <T>(arr: T[]): T[] => {
  const newArr = [...arr];
  for (let i = newArr.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [newArr[i], newArr[j]] = [newArr[j], newArr[i]];
  }
  return newArr;
};

// 数组平均值
export const calculateAverage = (arr: number[]): number => {
  const sum = arr.reduce((acc, curr) => acc + curr, 0);
  return sum / arr.length;
};

/**
 * #异步处理函数
 * @param tasks
 * @returns
 */

// 并行执行异步任务
export const parallelAsyncTasks = async (
  tasks: (() => Promise<any>)[]
): Promise<any[]> => {
  const results = await Promise.all(tasks.map((task) => task()));
  return results;
};

// 串行执行异步任务
export const seriesAsyncTasks = async (
  tasks: (() => Promise<any>)[]
): Promise<any[]> => {
  const results: any[] = [];
  for (const task of tasks) {
    const result = await task();
    results.push(result);
  }
  return results;
};
