import { Button, TextField } from "@material-ui/core";
import { AxiosResponse } from "axios";
import React from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { AuthenticationApi, ExceptionDTO, LoginDTO, LoginParameter } from "../../../generated";
import { LOCAL_STORAGE_KEY, storeLocalStorage } from "../../common/LocalStorage";

const style = {
    divStyle : {
        display: 'flex',
        flexdirection : 'column',
        margin: '4px'
    }
}

export const LoginPage = () => {

    const api: AuthenticationApi = new AuthenticationApi()

    const { handleSubmit, control, formState: { errors }} = useForm<LoginParameter>();

    const onSubmit: SubmitHandler<LoginParameter> = (loginParameter: LoginParameter) => {
        api.login(loginParameter)
        .then((response:AxiosResponse<LoginDTO>) => {
            const loginDTO:LoginDTO = response.data;
            storeLocalStorage(LOCAL_STORAGE_KEY.JWT_TOKEN, loginDTO.jwt)
        })
        .catch((error) => {
            const exceptionDTO: ExceptionDTO = error.response.data;
            console.log(exceptionDTO)
        })
    }

    return <div style={style.divStyle}>
        <form onSubmit={handleSubmit(onSubmit)}>
            <Controller
                name="userName"
                control={control}
                rules={{ required: true }}
                render={({ field }) => <TextField error={errors.userName !== undefined} id="outlined-basic" label="Username" variant="outlined" {...field} />}
            />
            <Controller
                name="password"
                control={control}
                rules={{ required: true }}
                render={({ field }) => <TextField error={errors.password !== undefined} id="outlined-basic" label="Password" variant="outlined" {...field} />}
            />
            <Button variant="contained" type="submit">Login</Button>
        </form>
    </div>
}