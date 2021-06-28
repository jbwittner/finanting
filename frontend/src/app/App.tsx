import { AxiosResponse } from 'axios';
import React, { useState } from 'react';
import { AuthenticationApi, Configuration, LoginDTO, LoginParameter, UserApi } from '../generated';
import { LoginContext } from './common/Context';
import { MainRouter } from './common/Router';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

    const authenticationApi: AuthenticationApi = new AuthenticationApi();
    let jwt: string;

    const loginParameter: LoginParameter = {
        userName: 'jbwittner',
        password: 'minim cupidatat Lorem'
    };

    async function test() {
        authenticationApi
            .login(loginParameter)
            .then((r: AxiosResponse<LoginDTO>) => {
                console.log(r.data.jwt);
                if (r.data.jwt !== undefined) {
                    jwt = r.data.jwt;
                }
            })
            .catch((r) => console.log(r));
    }

    async function test2() {
        let config: Configuration = new Configuration();
        config.baseOptions = {
            headers: {
                Authorization: 'Bearer ' + jwt
            }
        };
        const userApi: UserApi = new UserApi(config);
        userApi
            .userGet()
            .then((r) => {
                console.log(r);
            })
            .catch((r) => console.log(r));
    }

    return (
        <div className="App">
            <button onClick={test}>Test</button>
            <button onClick={test2}>Test2</button>
            <header className="App-header">
                <LoginContext.Provider
                    value={{
                        isAuthenticated,
                        setIsAuthenticated
                    }}>
                    <LoginContext.Consumer>{() => <MainRouter />}</LoginContext.Consumer>
                </LoginContext.Provider>
            </header>
        </div>
    );
}

export default App;
