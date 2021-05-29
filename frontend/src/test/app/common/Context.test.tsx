import React from 'react';
import { LoginContext } from '../../../app/common/Context';
import { render, screen } from '@testing-library/react';

const LoginContextConsumer = () => {
    const { isAuthenticated, setIsAuthenticated } = React.useContext(LoginContext)

    return (
        <div>
            <button id={'button'} onClick={() => setIsAuthenticated(!isAuthenticated)}>
                click
            </button>
            <div id={'test'}>{String(isAuthenticated)}</div>
        </div>
    )
}

const LoginContextProvider = () => {
    const [isAuthenticated, setIsAuthenticated] = React.useState(false);

    return (
        <LoginContext.Provider
            value={{
                isAuthenticated,
                setIsAuthenticated
            }}>
            <LoginContext.Consumer>
                {() => (
                    <LoginContextConsumer />
                )}
            </LoginContext.Consumer>
        </LoginContext.Provider>
    );
};

test('Test default value of LoginContext', () => {
    const result = render(<LoginContextProvider />);
    const linkElement = result.container.querySelector('#test');
    expect(linkElement).toHaveTextContent('false');
});

test('Test change value of context', () => {
    const result = render(<LoginContextProvider />);
    const linkElement = result.container.querySelector('#test');
    const buttonElement = screen.getByText(/click/);

    expect(linkElement).toHaveTextContent('false');
    buttonElement.click();
    expect(linkElement).toHaveTextContent('true');
    buttonElement.click();
    expect(linkElement).toHaveTextContent('false');
});
