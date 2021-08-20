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
          <Route
            exact
            path='/'
            render={() => (AuthService.authenticated ? <Home /> : <Welcome />)}
          />
          <Route
            exact
            path='/login'
            render={() =>
              AuthService.authenticated ? <LoginNew /> : <Redirect to='/' />
            }
          />
          <Route
            exact
            path='/signup'
            render={() =>
              AuthService.authenticated ? <Signup /> : <Redirect to='/' />
            }
          />
          <Route
            exact
            path='/home'
            render={() =>
              AuthService.authenticated ? <Home /> : <Redirect to='/' />
            }
          />
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
