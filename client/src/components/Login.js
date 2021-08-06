import React from "react";
// import Avatar from '@material-ui/core/Avatar';
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
// import Paper from '@material-ui/core/Paper';
// import Box from '@material-ui/core/Box';
import Grid from "@material-ui/core/Grid";
// import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
// import { MicNone } from '@material-ui/icons';
import useLogin from "../useLogin";
// import { useFormik } from "formik";
// import { validateYupSchema } from 'formik';
// import * as Yup from "yup";
// import { useForm } from "react-hook-form";
// import { useValues } from "react-hook-form";
// import { useState, useEffect } from "react";
import SignupImage from "../assets/images/time_management.png";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  root: {
    height: "100vh",
  },

  paper: {
    margin: theme.spacing(10, 10),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function Login(props) {
  const classes = useStyles();
  const {
    handleChange,
    values,
    formErrors,
    formValues,
    validate,
    intialValues,
    handleSubmit,
  } = useLogin();

  const handleLogin = () => {
    const data = {
      username: formValues.username,
      password: formValues.password,
    };

    axios
      .post("http://localhost:8080/authenticate", data)
      .then((res) => {
        console.log(res.data);
        localStorage.setItem("token", res.data);
        // setTimeout(() => {
        //   props.history.push('/home')
        // }, 500);
      })
      .then(() => {
        props.history.push("/home");
      })
      .catch((err) => {
        console.log(err);
        if (
          formValues.username.length == 0 ||
          formValues.password.length == 0
        ) {
          return "";
        } else {
          alert("Invalid username or password");
        }
      });
  };

  return (
    <Grid
      container
      component='main'
      style={{ maxHeight: "100px" }}
      className={classes.root}
    >
      <CssBaseline />

      <Grid item xs={12} sm={8} md={6} elevation={6} square>
        <div className={classes.paper}>
          <img src='img/time_management.png' style={{ width: "110%" }} />
        </div>
      </Grid>

      <Grid item xs={12} sm={8} md={6} elevation={6} square>
        <div className={classes.paper}>
          <Grid container>
            <Typography item xs={12} sm={8} md={6} component='h1' variant='h5'>
              Sign in
            </Typography>

            <Grid item xs={false} sm={8} md={3} style={{ marginLeft: "315px" }}>
              <Link href='/signup' variant='body2'>
                Create an Account
              </Link>
            </Grid>
          </Grid>

          <form className={classes.form} onSubmit={handleSubmit} noValidate>
            <TextField
              variant='outlined'
              margin='normal'
              required
              fullWidth
              id='username'
              label='Username'
              name='username'
              autoComplete='username'
              autoFocus
              value={formValues.username}
              onChange={handleChange}
              className={formErrors.username && "input-error"}
            />

            <Grid xs={12} md={4}>
              {formErrors.username && (
                <span className='error' style={{ color: "red" }}>
                  {formErrors.username}
                </span>
              )}
            </Grid>

            <TextField
              variant='outlined'
              margin='normal'
              required
              fullWidth
              name='password'
              label='Password'
              type='password'
              id='password'
              autoComplete='current-password'
              value={formValues.password}
              onChange={handleChange}
              className={formErrors.password && "input-error"}
            />
            <Grid xs={12} md={4}>
              {formErrors.password && (
                <span className='error' style={{ color: "red" }}>
                  {formErrors.password}
                </span>
              )}
            </Grid>

            <Grid xs={12} md={3}>
              <FormControlLabel
                control={<Checkbox value='remember' color='primary' />}
                label='Remember'
              />
            </Grid>
            <Button
              style={{ marginTop: "5px" }}
              type='submit'
              fullWidth
              variant='contained'
              color='primary'
              className={classes.submit}
              onClick={handleLogin}
            >
              Sign In
            </Button>

            <Grid item xs={12} md={5}>
              <Link href='#' variant='h6'>
                Forgot your password?
              </Link>
            </Grid>
          </form>
        </div>
      </Grid>
    </Grid>
  );
}
