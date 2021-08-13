export function runLogoutTimer(timer) {
  setTimeout(() => {
    localStorage.clear();
    alert("token is expired, please login again!");
    window.location.href = "/";
  }, timer);
}

export function authenticated() {
  const authenticated = false;
  const token = localStorage.getItem("token");
  runLogoutTimer(10000);
    if (token) {
      const authenticated = true;
      console.log(authenticated)
      return authenticated;
    } 
    console.log(authenticated)
    return authenticated;
}

