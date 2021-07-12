import { Button, Grid, TextField, Container, Avatar, Typography, makeStyles } from "@material-ui/core";
import { AxiosResponse } from "axios";
import React from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { ExceptionDTO, UserApi, UserDTO, UserRegistrationParameter } from "../../../generated";
import { ErrorSnackBars } from "../../common/SnackBar";
import PersonAddOutlinedIcon from '@material-ui/icons/PersonAddOutlined';
import { useHistory } from "react-router-dom";
import { PATH } from "../../common/Router";

const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(6),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(1),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  }));

export const RegistrationPage = () => {

    const classes = useStyles();

    const history = useHistory();

    const api: UserApi = new UserApi()

    const [error, setError] = React.useState<boolean>(false);
    const [errorMessage, setErrorMessage] = React.useState("");

    const { handleSubmit, control, formState: { errors }} = useForm<UserRegistrationParameter>();

    const onSubmit: SubmitHandler<UserRegistrationParameter> = (userRegistrationParameter: UserRegistrationParameter) => {
        api.userRegistration(userRegistrationParameter)
        .then((response:AxiosResponse<UserDTO>) => {
            setError(false);
            const userDTO:UserDTO = response.data;
            console.log(userDTO)
        })
        .catch((error) => {
            const exceptionDTO: ExceptionDTO = error.response.data;
            const message: string = exceptionDTO.message;
            setErrorMessage(message)
            setError(true);
        })
    }

    const onCloseError = () => {
        setError(false);
    }

    return <div>
        <ErrorSnackBars open={error} message={errorMessage} onClose={onCloseError}/>
        <Container maxWidth="xs">
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <PersonAddOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h3">
                    Finanting
                </Typography>
                <Typography component="h2" variant="h5">
                    Registration
                </Typography>
                <form onSubmit={handleSubmit(onSubmit)} className={classes.form}>
                    <Grid container direction="column" alignItems="stretch" spacing={1} >
                        <Grid item>
                            <Controller
                                name="userName"
                                control={control}
                                rules={{ required: true }}
                                defaultValue={""}
                                render={({ field }) => 
                                    <TextField
                                        fullWidth
                                        required
                                        error={errors.userName !== undefined}
                                        id="username"
                                        label="Username"
                                        variant="outlined"
                                        autoComplete="username"
                                        {...field}
                                    />}
                            />
                        </Grid>
                        <Grid item>
                            <Controller
                                name="email"
                                control={control}
                                rules={{ required: true }}
                                defaultValue={""}
                                render={({ field }) => 
                                    <TextField
                                        fullWidth
                                        required
                                        error={errors.userName !== undefined}
                                        id="email"
                                        label="Email"
                                        variant="outlined"
                                        autoComplete="email"
                                        {...field}
                                    />}
                            />
                        </Grid>
                        <Grid item>
                            <Controller
                                name="firstName"
                                control={control}
                                defaultValue={""}
                                render={({ field }) => 
                                    <TextField
                                        fullWidth
                                        error={errors.userName !== undefined}
                                        id="firstName"
                                        label="First Name"
                                        variant="outlined"
                                        autoComplete="firstName"
                                        {...field}
                                    />}
                            />
                        </Grid>
                        <Grid item>
                            <Controller
                                name="lastName"
                                control={control}
                                rules={{ required: true }}
                                defaultValue={""}
                                render={({ field }) => 
                                    <TextField
                                        fullWidth
                                        required
                                        error={errors.userName !== undefined}
                                        id="lastName"
                                        label="Last Name"
                                        variant="outlined"
                                        autoComplete="lastName"
                                        {...field}
                                    />}
                            />
                        </Grid>
                        <Grid item>
                            <Controller
                                name="password"
                                control={control}
                                rules={{ required: true }}
                                defaultValue={""}
                                render={({ field }) =>
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
                                    />}
                            />
                        </Grid>
                        <Grid container item spacing={1}>
                            <Grid item xs={6}>
                                <Button variant="contained" fullWidth type="submit">SIGN UP</Button>
                            </Grid>
                            <Grid item xs={6}>
                                <Button variant="contained" fullWidth onClick={() => history.push(PATH.LOGIN_PATH)}>CANCEL</Button>
                            </Grid>
                        </Grid>
                    </Grid>
                </form>
            </div>
        </Container>
    </div>
}