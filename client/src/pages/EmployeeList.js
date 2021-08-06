import React from "react";
import EmployeeDataGrid from "../components/EmployeeDataGrid";

const EmployeeList = () => {
  return (
    <div style={{ width: "80%", margin: "10px auto" }}>
      <h1 style={{ textAlign: "center", fontSize: "20px" }}>Employee List</h1>

      <EmployeeDataGrid />
    </div>
  );
};

export default EmployeeList;
