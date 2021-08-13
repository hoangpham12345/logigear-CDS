import React from "react";
import Welcome from "./components/Welcome";
import Home from "./components/Home";
import "./App.css";
import NotFound from "./components/NotFound";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import Header from "./components/Header";
import Signup from "./pages/Signup";
import LoginNew from "./pages/LoginNew";
import * as AuthService from "./utils/services/AuthService";
import EmployeeList from "./pages/EmployeeList";
import Profile from "./pages/Profile";

const App = () => {
  AuthService.authenticated();

  return (
    <div>
      <Router>
        <Header />
        <Switch>
          <Route exact path='/' component={Welcome} />
          <Route exact path='/login' component={LoginNew} />
          <Route exact path='/signup' component={Signup} />
          <Route exact path='/home' component={Home} />
          <Route
            exact
            path='/employees'
            render={() =>
              AuthService.authenticated ? <EmployeeList /> : <Redirect to='/' />
            }
          />
          <Route
            exact
            path='/profile'
            render={() =>
              AuthService.authenticated ? <Profile /> : <Redirect to='/' />
            }
          />
          <Route exact path='/header' component={Header} />
          {/* <Route exact path='/taskbar' component={TaskBar} /> */}
          <Route exact path='/:somestring' component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
};
export default App;
