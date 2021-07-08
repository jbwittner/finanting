import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert, { AlertProps, Color } from '@material-ui/lab/Alert';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { useEffect } from 'react';

function Alert(props: AlertProps) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    width: '100%',
    '& > * + *': {
      marginTop: theme.spacing(2),
    },
  },
}));

export interface AlertSnackBarProps {
  severity: Color
  open: boolean
  message: string
  onClose: () => void
}

export interface ErrorSnackBarProps {
  open: boolean
  message: string
  onClose: () => void
} 

const AlertSnackbars = React.memo(function(alertSnackBarProps: AlertSnackBarProps) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(alertSnackBarProps.open);
  const [message, setMessage] = React.useState("");

  useEffect(() => {
    console.log(alertSnackBarProps)
    setMessage(alertSnackBarProps.message)
    setOpen(alertSnackBarProps.open)
  },[alertSnackBarProps.open, alertSnackBarProps.message])

  const handleClose = (event?: React.SyntheticEvent, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    alertSnackBarProps.onClose()
  };

  return (
    <div className={classes.root}>
      <Snackbar open={open} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert onClose={handleClose} severity={alertSnackBarProps.severity}>
          {message}
        </Alert>
      </Snackbar>
    </div>
  );
})

const ErrorSnackBars = React.memo(function(errorSnackBarProps: ErrorSnackBarProps) {

  return(
    <AlertSnackbars
      open={errorSnackBarProps.open}
      severity={"error"}
      message={errorSnackBarProps.message}
      onClose={errorSnackBarProps.onClose}
      />
  )

})

export {AlertSnackbars, ErrorSnackBars}