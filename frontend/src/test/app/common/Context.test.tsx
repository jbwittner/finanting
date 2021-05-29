import React from 'react'
import {LoginContext} from "../../../app/common/Context";
import {render, screen} from "@testing-library/react";

const LoginContextConsumer = () => {

    const [authenticated, setIsAuthenticated] = React.useState(false)

    return (
    <LoginContext.Provider
        value={{
            authenticated,
            setIsAuthenticated,
        }}
    >
        <LoginContext.Consumer>
            {(value) => (
            <div>
                <button id={'button'} onClick={() => setIsAuthenticated(!authenticated)}>click</button>
                <div id={'test'}>{String(value.authenticated)}</div>
            </div>
            )}
        </LoginContext.Consumer>
    </LoginContext.Provider>
    )
}

test('Test default value of LoginContext', () => {
    const result = render(<LoginContextConsumer />)
    const linkElement = result.container.querySelector('#test')
    expect(linkElement).toHaveTextContent('false')
})

test('Other Test default value of LoginContext', () => {
    const result = render(<LoginContextConsumer />)
    const linkElement = result.container.querySelector('#test')
    const buttonElement = screen.getByText(/click/);

    expect(linkElement).toHaveTextContent('false')
    buttonElement.click()
    expect(linkElement).toHaveTextContent('true')
    buttonElement.click()
    expect(linkElement).toHaveTextContent('true')
})