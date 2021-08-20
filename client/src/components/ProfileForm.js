import React from "react";

import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";

import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core/styles";

import dayjs from "dayjs";

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
  fullname: Yup.string().required(),
  email: Yup.string().email().max(50).required(),
  address: Yup.string().required(),
  phone: Yup.string()
    .required()
    .test("len", "Must be exactly 10 characters", (val) => val.length === 10),
  birthday: Yup.date()
    .max(
      // new Date(Date.now() - 18 * 365 * 24 * 3600 * 1000),
      dayjs().set("year", dayjs().get("year") - 18),
      "You must be older than 18 years old to work at the company."
    )
    .required(),
});

const ProfileForm = () => {
  const classes = useStyles();
  const uid = localStorage.getItem("id");

  const [isSubmitting, setIsSubmitting] = React.useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: {
      fullname: "",
      email: "",
      address: "",
      phone: "",
      birthday: null,
    },
  });

  const onSubmit = (data) => {
    setIsSubmitting(true);
    const modifiedData = {
      // email: data.email,
      // userDetails: {
      //   fullname: data.fullname,
      //   address: data.address,
      //   phone: data.phone,
      //   birthday: dayjs(data.birthday)
      //     // .set("date", dayjs(data.birthday).get("date") + 1)
      //     .format("YYYY-MM-DD"),
      // },
      ...data,
      birthday: dayjs(data.birthday).format("YYYY-MM-DD"),
    };
    console.log(JSON.stringify(modifiedData));
    AxiosService.updateProfile(uid, modifiedData)
      .then((response) => alert("Update successfully"))
      .catch((error) => console.log(error));
    setIsSubmitting(false);
  };

  React.useEffect(() => {
    AxiosService.getProfie(uid)
      .then((response) => response.data)
      .then((data) => {
        const modifiedData = {
          fullname: data.userDetails.fullname,
          email: data.email,
          address: data.userDetails.address,
          phone: data.userDetails.phone,
          birthday: dayjs(data.userDetails.birthday).format("YYYY-MM-DD"),
        };
        reset(modifiedData);
        console.log(modifiedData);
      })
      .catch((error) => console.log(error));
  }, [reset, uid]);

  return (
    <form className={classes.profileForm} onSubmit={handleSubmit(onSubmit)}>
      <div className={classes.divField}>
        <label className={classes.labelText}>Fullname*</label>
        <input
          placeholder='Fullname'
          className={classes.inputText}
          {...register("fullname")}
        />
        <p className={classes.errorText}>{errors.fullname?.message}</p>
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
      <div className={classes.divField}>
        <label className={classes.labelText}>Address*</label>
        <input
          placeholder='Address'
          className={classes.inputText}
          {...register("address")}
        />
        <p className={classes.errorText}>{errors.address?.message}</p>
      </div>
      <div className={classes.divField}>
        <label className={classes.labelText}>Phone*</label>
        <input
          placeholder='Phone'
          className={classes.inputText}
          {...register("phone")}
        />
        <p className={classes.errorText}>{errors.phone?.message}</p>
      </div>
      <div className={classes.divField}>
        <label className={classes.labelText}>Birthday*</label>
        <input
          type='date'
          placeholder='Birthday'
          className={classes.inputText}
          {...register("birthday")}
        />
        <p className={classes.errorText}>{errors.birthday?.message}</p>
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
