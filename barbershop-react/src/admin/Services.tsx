import { Table } from 'antd';
import React, { useEffect, useState } from 'react';
import { ADM_GET_SERVICES_API_URI } from '../apiUris';
import { useStore } from '../store';

type Service = {
  id: number;
  name: string;
  durationMinutes: number;
  price: number;
};

const columns = [
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: 'Duration',
    dataIndex: 'duration',
    key: 'duration',
  },
  {
    title: 'Price',
    dataIndex: 'price',
    key: 'price',
  },
];

const Services = (): JSX.Element => {
  const [noService, setNoService] = useState(false);
  const [services, setServices] = useState<Service[] | null>(null);
  const { user } = useStore();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(ADM_GET_SERVICES_API_URI, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setNoService(true);
        return;
      }
      setNoService(false);
      setServices(await response.json());
    };
    fetchData();
  }, [user.jwt]);

  const data = services?.map((service) => ({
    name: service.name,
    duration: service.durationMinutes,
    price: `${service.price}Kč`,
  }));

  return (
    <div>
      <h1>Services</h1>
      {noService && <h3 style={{ marginLeft: '55%', color: 'green' }}>No Services</h3>}
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default Services;
