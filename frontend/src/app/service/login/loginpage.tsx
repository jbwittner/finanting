import {
    Button,
    Grid,
    TextField,
    Container,
    Avatar,
    Typography,
    makeStyles,
    FormControlLabel,
    Checkbox
} from '@material-ui/core';
import { AxiosResponse } from 'axios';
import React from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { AuthenticationApi, ExceptionDTO, LoginDTO, LoginParameter } from '../../../generated';
import {
    getLocalStorage,
    LOCAL_STORAGE_KEY,
    storeLocalStorage,
    clearLocalStorage
} from '../../common/LocalStorage';
import { ErrorSnackBars } from '../../common/SnackBar';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import { PATH } from '../../common/Router';
import { useHistory } from 'react-router-dom';
import { LoginContext } from '../../common/Context';
import { useEffect } from 'react';
import { getAxiosConfiguration } from '../../common/AxiosConfiguration';

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(6),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    }
}));

export const LoginPage = () => {
    const classes = useStyles();
    const history = useHistory();

    const { setIsAuthenticated } = React.useContext(LoginContext);

    const [error, setError] = React.useState<boolean>(false);
    const [errorMessage, setErrorMessage] = React.useState('');

    const [checkRememberMe, setCheckRememberMe] = React.useState<boolean>(false);

    const handleChangeRememberMe = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCheckRememberMe(event.target.checked);
    };

    const {
        handleSubmit,
        control,
        formState: { errors }
    } = useForm<LoginParameter>();

    useEffect(() => {
        const rememberMe: boolean = getLocalStorage(LOCAL_STORAGE_KEY.REMEMBER_ME);

        if (rememberMe === true) {
            const configuration = getAxiosConfiguration();

            if (configuration !== undefined) {
                const api: AuthenticationApi = new AuthenticationApi(configuration);
                api.test()
                    .then(() => {
                        setIsAuthenticated(true);
                        history.push(PATH.HOME_PATH);
                    })
                    .catch(() => {
                        clearLocalStorage();
                    });
            }
        }
    }, []);

    const onSubmit: SubmitHandler<LoginParameter> = (loginParameter: LoginParameter) => {
        const api: AuthenticationApi = new AuthenticationApi();
        api.login(loginParameter)
            .then((response: AxiosResponse<LoginDTO>) => {
                setError(false);
                const loginDTO: LoginDTO = response.data;
                storeLocalStorage(LOCAL_STORAGE_KEY.JWT_TOKEN, loginDTO.jwt);
                storeLocalStorage(LOCAL_STORAGE_KEY.REMEMBER_ME, checkRememberMe);
                setIsAuthenticated(true);
                history.push(PATH.HOME_PATH);
            })
            .catch((error) => {
                const exceptionDTO: ExceptionDTO = error.response.data;
                const message: string = exceptionDTO.message;
                setErrorMessage(message);
                setIsAuthenticated(false);
                setError(true);
            });
    };

    const onCloseError = () => {
        setError(false);
    };

    return (
        <div>
            <ErrorSnackBars open={error} message={errorMessage} onClose={onCloseError} />
            <Container maxWidth="xs">
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h3">
                        Finanting
                    </Typography>
                    <Typography component="h2" variant="h5">
                        Sign in
                    </Typography>
                    <form onSubmit={handleSubmit(onSubmit)} className={classes.form}>
                        <Grid container direction="column" alignItems="stretch" spacing={1}>
                            <Grid item>
                                <Controller
                                    name="userName"
                                    control={control}
                                    rules={{ required: true }}
                                    defaultValue={''}
                                    render={({ field }) => (
                                        <TextField
                                            fullWidth
                                            required
                                            error={errors.userName !== undefined}
                                            id="username"
                                            label="Username"
                                            variant="outlined"
                                            autoComplete="username"
                                            {...field}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid item>
                                <Controller
                                    name="password"
                                    control={control}
                                    rules={{ required: true }}
                                    defaultValue={''}
                                    render={({ field }) => (
                                        <TextField
                                            fullWidth
                                            required
                                            error={errors.password !== undefined}
                                            id="password"
                                            type="password"
                                            label="Password"
                                            variant="outlined"
                                            autoComplete="current-password"
                                            {...field}
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid item>
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            checked={checkRememberMe}
                                            onChange={handleChangeRememberMe}
                                            name="checkRememberMe"
                                        />
                                    }
                                    label="Remember me ?"
                                />
                            </Grid>
                            <Grid item>
                                <Button variant="contained" fullWidth type="submit">
                                    SIGN IN
                                </Button>
                            </Grid>
                            <Grid item>
                                <Button
                                    variant="contained"
                                    fullWidth
                                    onClick={() => history.push(PATH.REGISTRATION_PATH)}>
                                    Sign Up
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </div>
            </Container>
        </div>
    );
};
