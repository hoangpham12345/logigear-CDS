import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import AccountCircle from "@material-ui/icons/AccountCircle";
// import Switch from '@material-ui/core/Switch';
// import FormControlLabel from '@material-ui/core/FormControlLabel';
// import FormGroup from '@material-ui/core/FormGroup';
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Grid from "@material-ui/core/Grid";
// import { Route, Router } from 'react-router';
// import TaskBar from './TaskBar'
import Button from "@material-ui/core/Button";
// import * as AxiosService from "../utils/services/AxiosService";
//import { MenuItem } from "@material-ui/core";

const styles = {
  root: {
    flexGrow: 1,
  },
  grow: {
    flexGrow: 1,
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
  },
};

class Home extends React.Component {
  state = {
    roles: null,
    auth: true,
    anchorEl: null,
  };

  componentDidMount() {
    let roles = JSON.parse(localStorage.getItem("roles"));
    let user = localStorage.getItem("username");
    this.setState({ ...this.state, roles: roles, user: user }, () =>
      console.log(this.state)
    );
  }

  handleChange = (event) => {
    this.setState({ auth: event.target.checked });
  };

  handleMenu = (event) => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleClose = () => {
    this.setState({ anchorEl: null });
  };

  handleLogout = () => {
    this.setState({ anchorEl: null });
    localStorage.clear();
  };

  LoginCheckStatus() {
    if (this.state.user) {
      return "Hello " + localStorage.getItem("username");
    } else {
      return "You are not logged ";
    }
  }

  LoginCheckButton() {
    if (this.state.user) {
      return "logout";
    } else {
      return "login";
    }
  }

  loginCheckHref() {
    if (this.state.user) {
      return "/";
    } else {
      return "/login";
    }
  }

  render() {
    const { classes } = this.props;
    const { auth, anchorEl } = this.state;
    const open = Boolean(anchorEl);

    return (
      <div className={classes.root}>
        <AppBar position='static'>
          <Toolbar>
            <IconButton
              className={classes.menuButton}
              color='inherit'
              aria-label='Menu'
            >
              <MenuIcon />
            </IconButton>
            <Typography variant='h6' color='inherit' className={classes.grow}>
              Home Page
            </Typography>
            {auth && (
              <div>
                <IconButton
                  aria-owns={open ? "menu-appbar" : undefined}
                  aria-haspopup='true'
                  onClick={this.handleMenu}
                  color='inherit'
                >
                  <AccountCircle />
                </IconButton>
                <Menu
                  id='menu-appbar'
                  anchorEl={anchorEl}
                  anchorOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  transformOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  open={open}
                  onClose={this.handleClose}
                >
                  <MenuItem>
                    <Button
                      href={this.loginCheckHref()}
                      color='inherit'
                      onClick={this.handleLogout}
                    >
                      {this.LoginCheckButton()}
                    </Button>
                  </MenuItem>
                  <MenuItem>
                    <Button href='/profile' color='inherit'>
                      Profile
                    </Button>
                  </MenuItem>
                  {this.state.roles?.includes("manager") && (
                    <MenuItem>
                      <Button href='/employees' color='inherit'>
                        Employees List
                      </Button>
                    </MenuItem>
                  )}
                </Menu>
              </div>
            )}
          </Toolbar>
        </AppBar>
        <Grid item xs={12} md={12} className='App' style={{ fontSize: "50px" }}>
          {this.LoginCheckStatus()}
        </Grid>
      </div>
    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);
