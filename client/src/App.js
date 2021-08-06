import React from "react";
import Login from "./components/Login";
import Welcome from "./components/Welcome";
import Home from "./components/Home";
import "./App.css";
import NotFound from "./components/NotFound";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect,
} from "react-router-dom";
import Header from "./components/Header";
import Signup from "./pages/Signup";
import EmployeeList from "./pages/EmployeeList";

function App() {
  const authenticated = true;
  return (
    <div>
      <Router>
        <Header />
        <Switch>
          <Route exact path='/' component={Welcome} />
          <Route exact path='/login' component={Login} />
          <Route exact path='/signup' component={Signup} />
          <Route exact path='/home' component={Home} />
          <Route
            exact
            path='/employees'
            render={() =>
              authenticated ? <EmployeeList /> : <Redirect to='/' />
            }
          />
          <Route exact path='/header' component={Header} />
          {/* <Route exact path='/taskbar' component={TaskBar} /> */}
          <Route exact path='/:somestring' component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
