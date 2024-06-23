import CryptoJS from 'crypto-js';
const secret_key = "asdjyhdretwfdsfwerw"

export function encryptData(data) {
    // Use a library like CryptoJS to encrypt the data
    return CryptoJS.AES.encrypt(data, secret_key).toString();
}

export function decryptData(data) {
    // Use a library like CryptoJS to decrypt the data
    return CryptoJS.AES.decrypt(data, secret_key).toString(CryptoJS.enc.Utf8);
}