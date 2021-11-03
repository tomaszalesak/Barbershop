import { Table } from 'antd';
import React, { useEffect, useState } from 'react';
import { ADM_GET_EMPLOYEES_API_URI } from '../apiUris';
import { useStore } from '../store';

type Service = {
  id: number;
  name: string;
  durationMinutes: number;
  price: number;
};

type Employee = {
  id: number;
  login: string;
  password: string;
  firstname: string | null;
  lastname: string | null;
  phoneNumber: string | null;
  city: string | null;
  street: string | null;
  postalCode: string | null;
  role: string | null;
  salary: number | null;
  serviceDTOs: Service[] | null;
};

const columns = [
  {
    title: 'Login',
    dataIndex: 'login',
    key: 'login',
  },
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: 'Phone number',
    dataIndex: 'phone',
    key: 'phone',
  },
  {
    title: 'Address',
    dataIndex: 'address',
    key: 'address',
  },
  {
    title: 'Salary',
    dataIndex: 'salary',
    key: 'salary',
  },
];

const Employees = (): JSX.Element => {
  const [noEm, setNoEm] = useState(false);
  const [employees, setEmployees] = useState<Employee[] | null>(null);
  const { user } = useStore();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(ADM_GET_EMPLOYEES_API_URI, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setNoEm(true);
        return;
      }
      setNoEm(false);
      setEmployees(await response.json());
    };
    fetchData();
  }, [user.jwt]);

  const data = employees?.map((employee) => ({
    login: employee.login,
    name: `${employee.firstname} ${employee.lastname}`,
    phone: employee.phoneNumber,
    address: `${employee.street ?? ''} ${employee.city ?? ''} ${employee.postalCode ?? ''}`,
    salary: employee.salary ? `${employee.salary}Kč` : '0Kč',
  }));

  return (
    <div>
      <h1>Employees</h1>
      {noEm && <h3 style={{ marginLeft: '55%', color: 'green' }}>No Employees</h3>}
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default Employees;
