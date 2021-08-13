import React from "react";
import LoginForm from "../components/LoginForm";
import SignupImage from "../assets/images/time_management.png";
import { Link as RouterLink } from "react-router-dom";
import Link from "@material-ui/core/Link";
import { makeStyles } from "@material-ui/core/styles";
import TermsModal from "../components/TermsModal";
const useStyles = makeStyles({
  root: {
    // backgroundImage: "linear-gradient(#f6f6f6, #ffffff)",
    backgroundColor: "#f9f9f9",
    display: "flex",
    justifyContent: "space-around",
    alignItems: "center",
  },
  container: {
    padding: "20px",
  },
  flexItem: {
    flex: "1",
  },
  tab: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  biggerText: {
    fontSize: "20px",
    fontWeight: "bold",
  },
});

const LoginNew = () => {
  const classes = useStyles();

  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div className={classes.root}>
      <div className={classes.flexItem}>
        <img src={SignupImage} alt="login" width="90%" height="auto" />
      </div>
      <div className={classes.container + " " + classes.flexItem}>
        <div className={classes.tab}>
          <span className={classes.biggerText}>Login</span>
          <span>
            or{" "}
            <Link component={RouterLink} to="signup">
              Sign up
            </Link>
          </span>
        </div>
        <p></p>
        <LoginForm handleOpen={handleOpen} />
        <TermsModal open={open} handleClose={handleClose} />
      </div>
    </div>
  );
};

export default LoginNew;
