import axios from 'axios'
import { API_URL } from '../../constants'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

export const signup = (data) => {
  return axios.post(`${API_URL}/users`, data)
}
export const Login = (username, password) => {
  return axios
    .post('http://localhost:8080/auth/login', {
      username,
      password,
    })
    // .then(res => {
    //   console.log(res.data)
    //   localStorage.setItem('token', res.data)  
    //        return res.data;
    // })
    // .catch(err =>{
    //   console.log(err)
    // });
    .then((response) => {
        // localStorage.setItem("user", JSON.stringify(response.data));
        localStorage.setItem('token', response.data.jwtToken)    
      return response.data;
    });
};

// export const executeJwtAuthenticationService = (username, password) => {
//   return axios.post(`${API_URL}/authenticate`,{
//     username,
//     password  
//   })
// }

export function registerSuccessfulLoginForJwt(username, token){
  sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  setupAxiosInterceptors(createJWTToken(token))
}

const createJWTToken = (token) => {
  return 'Bearer ' + token
}

const setupAxiosInterceptors = (token) => {
  axios.interceptors.request.use(
    (config) => {
      if(this.isUserLoggedIn()){
        config.headers.authorization = token
      }
      return config
    }
  )
}