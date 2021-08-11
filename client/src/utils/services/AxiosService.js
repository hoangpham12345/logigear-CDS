import axios from "axios";
import { API_URL } from "../../constants";

export const signup = (data) => {
  return axios.post(`${API_URL}/auth/signup`, data);
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

var axiosAuth = axios.create({
  headers: {
    Authorization: "Bearer " + localStorage.getItem("token"),
  },
});

export const getEmployees = () => {
  return axiosAuth.get(`${API_URL}/users`);
};

export const getProfie = (id) => {
  return axiosAuth.get(`${API_URL}/users/${id}`);
};

export const updateProfile = (id, data) => {
  return axiosAuth.put(`${API_URL}/users/${id}`, data);
};
