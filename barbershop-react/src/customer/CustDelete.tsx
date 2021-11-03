import React from 'react';
import { Button, Form } from 'antd';
import { Redirect } from 'react-router-dom';
import { useStore } from '../store';
import { DEL_CUSTOMER_API_URI } from '../apiUris';

const CustDelete = (): JSX.Element => {
  const [form] = Form.useForm();
  const { user, setUser } = useStore();

  const onFinish = async () => {
    const response: Response = await fetch(DEL_CUSTOMER_API_URI + user.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      return;
    }

    setUser({
      id: null,
      login: null,
      role: 'UNKNOWN',
      jwt: null,
    });

    <Redirect to="/login" />;
  };

  return (
    <Form form={form} name="delete_customer" layout="inline" onFinish={onFinish}>
      <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
        <Button type="primary" htmlType="submit" danger>
          Delete profile
        </Button>
      </Form.Item>
    </Form>
  );
};

export default CustDelete;
