import { Configuration } from '../../generated';
import { getLocalStorage, LOCAL_STORAGE_KEY } from './LocalStorage';

export const getAxiosConfiguration = (): Configuration | undefined => {
    const token: string = getLocalStorage(LOCAL_STORAGE_KEY.JWT_TOKEN);

    if (token !== undefined) {
        const configuration: Configuration = new Configuration({
            baseOptions: {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }
        });

        return configuration;
    }
};
