import { Button, TextField } from "@material-ui/core";
import { AxiosResponse } from "axios";
import React from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { AuthenticationApi, LoginDTO, LoginParameter } from "../../../generated";

const style = {
    divStyle : {
        display: 'flex',
        flexdirection : 'column',
        margin: '4px'
    }
}

export interface exceptionDTO {
    details: string;
    exception: string;
    message: string;
    timestamp: string;
}

export const LoginPage = () => {

    const api: AuthenticationApi = new AuthenticationApi()

    const { handleSubmit, control, formState: { errors }} = useForm<LoginParameter>();

    const onSubmit: SubmitHandler<LoginParameter> = (data: LoginParameter) => {
        console.log(data)

        api.login(data)
        .then((out:AxiosResponse<LoginDTO>) => {
            const loginDTO:LoginDTO = out.data;
            console.log(loginDTO)
        })
        .catch((error) => {
            const exceptionDTOddd: exceptionDTO = error.response.data;
            console.log(exceptionDTOddd)
            console.log(exceptionDTOddd.timestamp)
            const datess = new Date(exceptionDTOddd.timestamp)
            console.log(datess.getFullYear())
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
            <Button variant="contained" type="submit">Default</Button>
        </form>
    </div>
}