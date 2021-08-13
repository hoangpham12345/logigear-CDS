import React from "react";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";

import { Link as RouterLink, useHistory } from "react-router-dom";
import Button from "@material-ui/core/Button";
import Checkbox from "@material-ui/core/Checkbox";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Link from "@material-ui/core/Link";
import { makeStyles } from "@material-ui/core/styles";
import axios from "axios";
import * as AxiosService from "../utils/services/AxiosService";


const useStyles = makeStyles({
  signupForm: {
    marginTop: "20px",
  },
  divField: {
    padding: "1vh 0",
  },
  labelText: {
    fontSize: "15px",
    fontWeight: "bold",
    display: "block",
  },
  inputText: {
    padding: "1vh 1vw",
    width: "calc(100% - 2vw)",
    margin: "0 auto",
    border: "1px solid lightgray",
    fontSize: "15px",
  },
  errorText: {
    color: "red",
  },
});

const schema = Yup.object().shape({
  username: Yup.string().required(),
  password: Yup.string().required().min(8).max(21),
});

const LoginForm = ({ handleOpen }) => {
  const history = useHistory();

  const classes = useStyles();

  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm({
    resolver: yupResolver(schema),
  });
  const onSubmit = (data) => {
    let modifiedData = {
      username: data.username,
      password: data.password,
    };
    console.log(data);
    AxiosService.Login(modifiedData)
      .then(() => {
        setTimeout(() => {
          history.push("/home");
        }, 300);
      })
      .catch((error) => alert("Invalid Username or Password !!!"));
  };
  return (
    <form className={classes.LoginForm} onSubmit={handleSubmit(onSubmit)}>
      <div className={classes.divField}>
        <label className={classes.labelText}>Username*</label>
        <input
          placeholder="Username"
          className={classes.inputText}
          {...register("username")}
        />
        <p className={classes.errorText}>{errors.username?.message}</p>
      </div>
      <div className={classes.divField}>
        <label className={classes.labelText}>Password*</label>
        <input
          type="password"
          placeholder="Password"
          className={classes.inputText}
          {...register("password")}
        />
        <p className={classes.errorText}>{errors.password?.message}</p>
      </div>
      <FormControlLabel
        control={
          <Controller
            name="remember"
            control={control}
            defaultValue={false}
            render={({ field }) => <Checkbox {...field} color="primary" />}
          />
        }
        label={<span>Remember me</span>}
      />
      <p></p>
      <Button type="submit" fullWidth variant="contained" color="primary">
        Login
      </Button>
    </form>
  );
};

export default LoginForm;
