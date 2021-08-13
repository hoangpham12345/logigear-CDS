import React from "react";

import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";

import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core/styles";

import * as AxiosService from "../utils/services/AxiosService";

const useStyles = makeStyles({
  profileForm: {
    margin: "20px auto 0px auto",
    width: 400,
    padding: "5px",
    border: "1px solid black",
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
    boxSizing: "border-box",
    padding: "1vh 1vw",
    width: "100%",
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
  email: Yup.string().email().max(50).required(),
});

const ProfileForm = () => {
  const classes = useStyles();
  const uid = localStorage.getItem("id"); // This is for testing, will be changed to the real userid

  const [isSubmitting, setIsSubmitting] = React.useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: { username: "", email: "" },
  });

  const onSubmit = (data) => {
    setIsSubmitting(true);
    console.log(data);
    AxiosService.updateProfile(uid, data)
      .then(alert("Update successfully"))
      .catch((error) => console.log(error));
    setIsSubmitting(false);
  };

  React.useEffect(() => {
    AxiosService.getProfie(uid)
      .then((response) => {
        console.log(response.data);
        reset(response.data);
      })
      .catch((error) => console.log(error));
  }, [reset]);

  return (
    <form className={classes.profileForm} onSubmit={handleSubmit(onSubmit)}>
      <div className={classes.divField}>
        <label className={classes.labelText}>Username*</label>
        <input
          placeholder='Username'
          className={classes.inputText}
          {...register("username")}
        />
        <p className={classes.errorText}>{errors.username?.message}</p>
      </div>
      <div className={classes.divField}>
        <label className={classes.labelText}>Email*</label>
        <input
          placeholder='Email'
          className={classes.inputText}
          {...register("email")}
        />
        <p className={classes.errorText}>{errors.email?.message}</p>
      </div>
      <Button
        type='submit'
        fullWidth
        variant='contained'
        color='primary'
        disabled={isSubmitting}
      >
        Update
      </Button>
    </form>
  );
};

export default ProfileForm;
