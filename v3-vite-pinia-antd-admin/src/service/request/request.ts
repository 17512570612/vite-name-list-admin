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

export const download = async (url: string): Promise<Blob> => {
  try {
    const response = await axiosInstance.get(url, {
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: response.headers["content-type"],
    });
    return blob;
  } catch (error) {
    throw error;
  }
};
