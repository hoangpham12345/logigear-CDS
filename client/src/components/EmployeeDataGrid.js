import React from "react";
import { DataGrid, GridToolbar } from "@material-ui/data-grid";

import dayjs from "dayjs";

import * as AxiosService from "../utils/services/AxiosService";

// import { data } from "../data";

const columns = [
  { field: "id", headerName: "ID", type: "number", width: 90 },
  {
    field: "fullname",
    headerName: "Fullname",
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
    field: "address",
    headerName: "Address",
    flex: 1,
    minWidth: 150,
    editable: true,
  },
  {
    field: "phone",
    headerName: "Phone",
    flex: 1,
    minWidth: 150,
    editable: true,
  },
  {
    field: "birthday",
    headerName: "Birthday",
    flex: 1,
    minWidth: 150,
    type: "date",
    editable: true,
  },
  {
    field: "employee",
    headerName: "isEmployee",
    flex: 0.75,
    type: "boolean",
    minWidth: 130,
    editable: false,
  },
  {
    field: "manager",
    headerName: "isManager",
    flex: 0.8,
    type: "boolean",
    minWidth: 130,
    editable: false,
  },
];

const EmployeeDataGrid = () => {
  const [pageSize, setPageSize] = React.useState(5);
  const [page, setPage] = React.useState(0);
  const [data, setData] = React.useState([]);

  React.useEffect(() => {
    let users = [];
    AxiosService.getEmployees()
      .then((response) => response.data)
      .then((data) => {
        data.forEach((obj) => {
          let roles = [];
          obj.roles.forEach((role) => roles.push(role.name));
          console.log(roles);
          let user = {
            id: obj.id,
            fullname: obj.userDetails.fullname,
            email: obj.email,
            address: obj.userDetails.address,
            phone: obj.userDetails.phone,
            birthday: dayjs(obj.userDetails.birthday).format("YYYY-MM-DD"),
            employee: roles.includes("employee") ? true : false,
            manager: roles.includes("manager") ? true : false,
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
