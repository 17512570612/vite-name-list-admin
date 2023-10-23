import axiosInstance from "./interce";

export const get = async <T>(url: string, params?: any): Promise<T> => {
  try {
    const response = await axiosInstance.get(url, { params });
    return response as T;
  } catch (error) {
    throw error;
  }
};

export const post = async <T>(url: string, data?: any): Promise<T> => {
  try {
    const response = await axiosInstance.post(url, data);
    return response as T;
  } catch (error) {
    throw error;
  }
};

export const put = async <T>(url: string, data?: any): Promise<T> => {
  try {
    const response = await axiosInstance.put(url, data);
    return response as T;
  } catch (error) {
    throw error;
  }
};

export const remove = async <T>(url: string, data?: any): Promise<T> => {
  try {
    const response = await axiosInstance.delete(url, data);
    return response as T;
  } catch (error) {
    throw error;
  }
};

export const download = async (url: string): Promise<any> => {
  try {
    const response = await axiosInstance.get(url, {
      headers: { "Content-Type": "application/json; charset=UTF-8" },
      responseType: "blob",
    });
    return response;
  } catch (error) {
    throw error;
  }
};
