import React from 'react';
import { Route, Redirect, RouteProps, Switch, HashRouter, useHistory } from 'react-router-dom';
import { LoginPage } from '../service/login/loginpage';
import { RegistrationPage } from '../service/registration/registrationpage';
import { LoginContext } from './Context';

/* eslint-disable no-unused-vars */
export enum PATH {
    LOGIN_PATH = '/',
    REGISTRATION_PATH = '/registration',
    HOME_PATH = '/home'
}

interface PrivateRouteProps extends RouteProps {
    component: any;
}

const PrivateRoute = (props: PrivateRouteProps) => {
    const { component: Component, ...rest } = props;

    const { isAuthenticated } = React.useContext(LoginContext);

    return (
        // Show the component only when the user is logged in
        // Otherwise, redirect the user to / page
        <Route
            {...rest}
            render={(props) =>
                isAuthenticated ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{ pathname: PATH.LOGIN_PATH, state: { from: props.location } }} />
                )
            }
        />
    );
};

export function nextPath(path: PATH) {
    const history = useHistory();
    history.push(path);
}

export function MainRouter() {
    return (
        <HashRouter hashType={'noslash'}>
            <Switch>
                <Route exact path={PATH.LOGIN_PATH} component={LoginPage} />
                <Route exact path={PATH.REGISTRATION_PATH} component={RegistrationPage} />
                <PrivateRoute exact path={PATH.HOME_PATH} component={React.Fragment} />
            </Switch>
        </HashRouter>
    );
}
