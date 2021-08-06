import React from "react";
import { DataGrid, GridToolbar } from "@material-ui/data-grid";
import axios from "axios";
import { API_URL } from "../constants";
// import { data } from "../data";

const columns = [
  { field: "id", headerName: "ID", type: "number", width: 90 },
  {
    field: "name",
    headerName: "Name",
    flex: 1,
    minWidth: 150,
    editable: true,
  },
  {
    field: "email",
    headerName: "Email",
    flex: 1,
    minWidth: 150,
    editable: true,
  },
  {
    field: "user",
    headerName: "isUser",
    flex: 0.75,
    type: "boolean",
    minWidth: 110,
    editable: false,
  },
  {
    field: "admin",
    headerName: "isAdmin",
    flex: 0.8,
    type: "boolean",
    minWidth: 110,
    editable: false,
  },
];

const EmployeeDataGrid = () => {
  const [pageSize, setPageSize] = React.useState(5);
  const [page, setPage] = React.useState(0);
  const [data, setData] = React.useState([]);

  React.useEffect(() => {
    let users = [];
    axios
      .get(`${API_URL}/users`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((response) => response.data)
      .then((data) => {
        data.forEach((obj) => {
          let roles = [];
          obj.roles.forEach((role) => roles.push(role.name));
          let user = {
            id: obj.id,
            name: obj.username,
            email: obj.email,
            user: roles.includes("user") ? true : false,
            admin: roles.includes("admin") ? true : false,
          };
          users.push(user);
        });
      })
      .then(() => {
        setData(users);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <div
      style={{
        display: "flex",
        height: 600,
        width: "100%",
      }}
    >
      <DataGrid
        page={page}
        onPageChange={(newPage) => setPage(newPage)}
        pageSize={pageSize}
        onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
        rowsPerPageOptions={[5, 10, 20]}
        checkboxSelection
        disableSelectionOnClick
        pagination
        components={{
          Toolbar: GridToolbar,
        }}
        rows={data}
        columns={columns}
      />
    </div>
  );
};

export default EmployeeDataGrid;
