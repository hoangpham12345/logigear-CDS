import React from "react";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";

import { useHistory } from "react-router-dom";

import Button from "@material-ui/core/Button";
import Checkbox from "@material-ui/core/Checkbox";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Link from "@material-ui/core/Link";
import { makeStyles } from "@material-ui/core/styles";

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
  fullname: Yup.string().required(),
  email: Yup.string().email().max(50).required(),
  password: Yup.string().required().min(8).max(21),
  confirmPassword: Yup.string()
    .required()
    .oneOf([Yup.ref("password"), null]),
  acceptTerm: Yup.bool().oneOf([true], "Accept Terms is required"),
});

const SignupForm = ({ handleOpen }) => {
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
      username: data.fullname,
      password: data.password,
      email: data.email,
    };
    // alert(JSON.stringify(modifiedData))
    AxiosService.signup(modifiedData)
      // .then(history.push(''))
      .then(() => {
        alert("Sign up success. Close this to go back to login page.");
        history.push("login");
      })
      // .catch(error => alert(`${handleError(error)}`))
      .catch((error) => console.log(error));
  };

  // const handleError = (error) => {
  //   let errorMessage = '';
  //   if(error.message)
  //     errorMessage += errorMessage.message

  //   if (error.response && error.response.data)
  //     errorMessage += error.response.data.message
  //   return errorMessage
  // }

  return (
    <form className={classes.signupForm} onSubmit={handleSubmit(onSubmit)}>
      <div className={classes.divField}>
        <label className={classes.labelText}>Username*</label>
        <input
          placeholder="Username"
          className={classes.inputText}
          {...register("fullname")}
        />
        <p className={classes.errorText}>{errors.fullname?.message}</p>
      </div>
      <div className={classes.divField}>
        <label className={classes.labelText}>Email*</label>
        <input
          placeholder="Email"
          className={classes.inputText}
          {...register("email")}
        />
        <p className={classes.errorText}>{errors.email?.message}</p>
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
      <div className={classes.divField}>
        <label className={classes.labelText}>Confirm password*</label>
        <input
          type="password"
          placeholder="Confirm Password"
          className={classes.inputText}
          {...register("confirmPassword")}
        />
        <p className={classes.errorText}>{errors.confirmPassword?.message}</p>
      </div>
      <FormControlLabel
        control={
          <Controller
            name="acceptTerm"
            control={control}
            defaultValue={false}
            render={({ field }) => <Checkbox {...field} color="primary" />}
          />
        }
        label={
          // <span>I agree to the MOWEDE <Link component={RouterLink} to="term" target="_blank" rel="noopener noreferrer">Terms</Link></span>
          <span>
            I agree to the MOWEDE{" "}
            <Link component="span" variant="body1" onClick={handleOpen}>
              Terms
            </Link>
          </span>
        }
      />
      {/* We will use React modal to display the Terms later */}
      <p className={classes.errorText}>{errors.acceptTerm?.message}</p>
      <Button type="submit" fullWidth variant="contained" color="primary">
        Register
      </Button>
    </form>
  );
};

export default SignupForm;
