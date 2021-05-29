import React, { useState } from 'react';
import { LoginContext } from './common/Context';
import { MainRouter } from './common/Router';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

    return (
        <div className="App">
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
