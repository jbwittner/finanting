import React from 'react';

type LoginContextType = {
    authenticated: boolean;
    setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
};

export const LoginContext = React.createContext<LoginContextType>({
    authenticated: false,
    setIsAuthenticated: () => {}
});
