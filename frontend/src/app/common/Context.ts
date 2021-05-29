import React from 'react';

type LoginContextType = {
    isAuthenticated: boolean;
    setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
};

export const LoginContext = React.createContext<LoginContextType>({
    isAuthenticated: false,
    setIsAuthenticated: () => {}
});
