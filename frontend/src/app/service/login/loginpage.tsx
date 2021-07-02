import { TextField } from "@material-ui/core";
import React from "react";

export const LoginPage = () => {

    return <React.Fragment>
        <TextField id="outlined-basic" label="Username" variant="outlined" />
        <TextField id="outlined-basic" label="Password" variant="outlined" />
    </React.Fragment>
}