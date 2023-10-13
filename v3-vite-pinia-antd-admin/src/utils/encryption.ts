import CryptoJS from "crypto-js";

// 密钥，可以自定义
const secretKey = "WangYuxing";

// 加密函数
export function encryptString(input: string): string {
  const encrypted = CryptoJS.AES.encrypt(input, secretKey).toString();
  return encrypted;
}

// 解密函数
export function decryptString(input: string): string {
  const decryptedBytes = CryptoJS.AES.decrypt(input, secretKey);
  const decrypted = decryptedBytes.toString(CryptoJS.enc.Utf8);
  return decrypted;
}
