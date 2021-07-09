import { Button, Grid, TextField } from "@material-ui/core";
import { AxiosResponse } from "axios";
import React from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { AuthenticationApi, ExceptionDTO, LoginDTO, LoginParameter } from "../../../generated";
import { LOCAL_STORAGE_KEY, storeLocalStorage } from "../../common/LocalStorage";
import { ErrorSnackBars } from "../../common/SnackBar";

const style = {
    divStyle : {
        display: 'flex',
        flexdirection : 'row',
        margin: '4px'
    },
    gridItem: {
        width: '100%'
    }
}

export const LoginPage = () => {

    const api: AuthenticationApi = new AuthenticationApi()

    const [error, setError] = React.useState<boolean>(false);
    const [errorMessage, setErrorMessage] = React.useState("");

    const { handleSubmit, control, formState: { errors }} = useForm<LoginParameter>();

    const onSubmit: SubmitHandler<LoginParameter> = (loginParameter: LoginParameter) => {
        api.login(loginParameter)
        .then((response:AxiosResponse<LoginDTO>) => {
            setError(false);
            const loginDTO:LoginDTO = response.data;
            storeLocalStorage(LOCAL_STORAGE_KEY.JWT_TOKEN, loginDTO.jwt)
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
        <form onSubmit={handleSubmit(onSubmit)} style={style.divStyle}>
            <Grid container direction="column" alignItems="stretch" spacing={1} >
                <Grid item>
                    <Controller
                        name="userName"
                        control={control}
                        rules={{ required: true }}
                        defaultValue={""}
                        render={({ field }) => <TextField style={style.gridItem} error={errors.userName !== undefined} id="outlined-basic" label="Username" variant="outlined" {...field} />}
                    />
                </Grid>
                <Grid item>
                    <Controller
                        name="password"
                        control={control}
                        rules={{ required: true }}
                        defaultValue={""}
                        render={({ field }) => <TextField style={style.gridItem} error={errors.password !== undefined} id="outlined-basic" label="Password" variant="outlined" {...field} />}
                    />
                </Grid>
                <Grid item>
                    <Button variant="contained" type="submit" style={style.gridItem}>Login</Button>
                </Grid>
                <Grid item>
                    <Button onClick={() => console.log("toto")}>link text</Button>
                    <Button variant="contained" style={style.gridItem}>Sign Up</Button>
                </Grid>
            </Grid>
        </form>
    </div>
}