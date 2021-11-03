import { Alert } from 'antd';
import Table from 'antd/lib/table';
import React, { useEffect, useState } from 'react';
import { GET_EMPLOYEE_SERVICES_API_URI } from '../apiUris';
import { Service } from '../common/types';
import { useStore } from '../store';

const EmployeeServices = (): JSX.Element => {
  const [error, setError] = useState('');
  const [showError, setShowError] = useState(false);
  const { user } = useStore();
  const [services, setServices] = useState<Service[] | undefined>(undefined);

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(`${GET_EMPLOYEE_SERVICES_API_URI}/${user.id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setError('Could not get your services 😭');
        setShowError(true);
        return;
      }

      setShowError(false);
      setServices(await response.json());
    };
    fetchData();
  }, [user.id, user.jwt]);

  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Duration in minutes',
      dataIndex: 'durationMinutes',
      key: 'durationMinutes',
    },
    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      render: (x: string) => `${x} Kč`,
    },
  ];

  return (
    <>
      <h1>Your services</h1>
      {showError && <Alert message={error} type="error" style={{ marginBottom: '25px' }} />}
      <Table dataSource={services} columns={columns} pagination={false} rowKey="id" />
    </>
  );
};

export default EmployeeServices;
