import React from "react";

import ProfileForm from "../components/ProfileForm";

const Profile = () => {
  return (
    <div style={{ width: "80%", margin: "10px auto" }}>
      <h1 style={{ textAlign: "center", fontSize: "30px" }}>Update Info</h1>

      <ProfileForm />
    </div>
  );
};

export default Profile;
