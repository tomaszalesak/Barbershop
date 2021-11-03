import React, { useEffect, useState } from 'react';
import { Button, Form, Input } from 'antd';
import { GET_CUSTOMER_API_URI, UPDT_CUSTOMER_API_URI } from '../apiUris';
import { useStore } from '../store';
import CustDelete from './CustDelete';

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

const CustEdit = (): JSX.Element => {
  const [wrongLoad, setWrongLoad] = useState(false);
  const [wrongUpdate, setWrongUpdate] = useState(false);
  const [rightUpdate, setRightUpdate] = useState(false);
  const [refresh, setRefresh] = useState(false);
  const [customer, setCustomer] = useState<Customer | null>(null);
  const { user } = useStore();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(GET_CUSTOMER_API_URI + user.id, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setWrongLoad(true);
        return;
      }
      setWrongLoad(false);
      setCustomer(await response.json());
      form.resetFields();
    };
    fetchData();
  }, [user.id, user.jwt, refresh, form]);

  const onFinish = async () => {
    const response: Response = await fetch(UPDT_CUSTOMER_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        id: user.id,
        login: customer?.login,
        password: customer?.password,
        firstname: form.getFieldValue('firstname'),
        lastname: form.getFieldValue('lastname'),
        phoneNumber: form.getFieldValue('phone'),
        city: form.getFieldValue('city'),
        street: form.getFieldValue('street'),
        postalCode: form.getFieldValue('postalcode'),
        role: customer?.role,
      }),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      setWrongUpdate(true);
      setRightUpdate(false);
      return;
    }

    setWrongUpdate(false);
    setRightUpdate(true);
    if (refresh === false) {
      setRefresh(true);
    } else {
      setRefresh(false);
    }
  };

  return (
    <div>
      <h1 style={{ marginLeft: '60%' }}>Profile</h1>
      {wrongLoad && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Error: Data could not load, please try to log in again!
        </h3>
      )}
      {wrongUpdate && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>Error: Could not update data!</h3>
      )}
      {rightUpdate && <h3 style={{ marginLeft: '55%', color: 'green' }}>Data updated</h3>}
      <Form
        labelCol={{ xs: { span: 24 }, sm: { span: 8 } }}
        wrapperCol={{ xs: { span: 24 }, sm: { span: 16 } }}
        form={form}
        name="register"
        onFinish={onFinish}
        scrollToFirstError
      >
        <Form.Item
          name="firstname"
          label="First name"
          initialValue={customer?.firstname}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="lastname"
          label="Last name"
          initialValue={customer?.lastname}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item name="phone" label="Phone Number" initialValue={customer?.phoneNumber}>
          <Input />
        </Form.Item>

        <Form.Item
          name="city"
          label="City"
          initialValue={customer?.city}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="street"
          label="Street"
          initialValue={customer?.street}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="postalcode"
          label="Postal Code"
          initialValue={customer?.postalCode}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
          <div style={{ textAlign: 'center' }}>
            <Button type="primary" htmlType="submit">
              Update
            </Button>
          </div>
        </Form.Item>
      </Form>
      <div style={{ position: 'absolute', top: '110%', left: '90%' }}>
        <CustDelete />
      </div>
    </div>
  );
};

export default CustEdit;
