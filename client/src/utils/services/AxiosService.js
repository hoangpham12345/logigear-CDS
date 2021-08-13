import axios from "axios";
import { API_URL } from "../../constants";

export const signup = (data) => {
  return axios.post(`${API_URL}/users`, data);
};

export const Login = (modifiedData) => {
  return axios
    .post("http://localhost:8080/auth/login", modifiedData)
    .then((res) => {
      console.log(res.modifiedData);
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("id", res.data.id);
      localStorage.setItem("username", res.data.name);
    });
};

export const getUserById = () => {
  const config = {
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  };

  return axios
    .get("http://localhost:8080/users/" + localStorage.getItem("id"), config)
    .then(
      (res) => {
        localStorage.setItem("username", res.data.username);
      },
      (err) => {
        console.log(err);
      }
    );
};


