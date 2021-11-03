import { Table } from 'antd';
import React, { useEffect, useState } from 'react';
import { ADM_GET_CUSTOMERS_API_URI } from '../apiUris';
import { useStore } from '../store';

type Customer = {
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
];

const Customers = (): JSX.Element => {
  const [noCus, setNoCus] = useState(false);
  const [customers, setCustomers] = useState<Customer[] | null>(null);
  const { user } = useStore();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(ADM_GET_CUSTOMERS_API_URI, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setNoCus(true);
        return;
      }
      setNoCus(false);
      setCustomers(await response.json());
    };
    fetchData();
  }, [user.jwt]);

  const data = customers?.map((customer) => ({
    login: customer.login,
    name: `${customer.firstname} ${customer.lastname}`,
    phone: customer.phoneNumber,
    address: `${customer.street ?? ''} ${customer.city ?? ''} ${customer.postalCode ?? ''}`,
  }));

  return (
    <div>
      <h1>Customers</h1>
      {noCus && <h3 style={{ marginLeft: '55%', color: 'green' }}>No customers</h3>}
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default Customers;
