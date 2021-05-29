import React from 'react'

type LoginContextType = {
    authenticated: boolean,
    setAuthenticated: () => void
}

export const LoginContext = React.createContext<LoginContextType>({
    authenticated: false,
    setAuthenticated: () => {}
})