import React, { useState } from 'react';
import { AuthenticationApi, LoginParameter } from '../generated';
import { LoginContext } from './common/Context';
import { MainRouter } from './common/Router';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

    const authenticationApi: AuthenticationApi = new AuthenticationApi();

    const loginParameter: LoginParameter = {
        userName: 'jbwittner',
        password: 'minim cupidatat Lorem'
    };

    async function test() {
        authenticationApi
            .login(loginParameter)
            .then((r) => {
                console.log(r);
            })
            .then((r) => console.log(r));
    }

    authenticationApi.login(loginParameter);

    return (
        <div className="App">
            <button onClick={test}>Test</button>
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
