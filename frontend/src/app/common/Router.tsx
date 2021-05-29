import React from 'react';
import { Route, Redirect, RouteProps, Switch, HashRouter } from 'react-router-dom'
import {LoginContext} from "./Context";

export const INDEX_PATH = '/'
export const HOME_PATH  = '/home'

interface PrivateRouteProps extends RouteProps {
    component: any // eslint-disable-line @typescript-eslint/no-explicit-any
}

const PrivateRoute = (props: PrivateRouteProps): JSX.Element => {
    const { component: Component, ...rest } = props

    const { isAuthenticated } = React.useContext(LoginContext)

    return (
        // Show the component only when the user is logged in
        // Otherwise, redirect the user to / page
        <Route
            {...rest}
            render={(props) =>
                isAuthenticated ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{ pathname: INDEX_PATH, state: { from: props.location } }} />
                )
            }
        />
    )
}

export function MainRouter() {
    return <HashRouter hashType={'noslash'}>
        <Switch>
            <Route exact path={INDEX_PATH} component={React.Fragment} />
            <PrivateRoute exact path={HOME_PATH} component={React.Fragment} />
        </Switch>
    </HashRouter>;
}
