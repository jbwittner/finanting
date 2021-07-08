/* eslint-disable no-unused-vars */
export enum LOCAL_STORAGE_KEY {
    JWT_TOKEN = "JWT_TOKEN" //
}
/* eslint-enable no-unused-vars */

export const storeLocalStorage = (key:LOCAL_STORAGE_KEY, value:any) => {
    localStorage.setItem(key, JSON.stringify(value));
}

export const getLocalStorage = (key:LOCAL_STORAGE_KEY) => {
    const value = localStorage.getItem(key);
    var object
    if(value){
        object = JSON.parse(value)
        return object;
    }
}