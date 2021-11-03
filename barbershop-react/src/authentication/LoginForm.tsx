import React, { useState } from 'react';
import { Form, Input, Button } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { Link, Redirect } from 'react-router-dom';
import { useStore } from '../store';
import { SIGN_IN_API_URI } from '../apiUris';

const LoginForm = (): JSX.Element => {
  const [wrongLogin, setWrongLogin] = useState(false);
  const { user, setUser } = useStore();
  const [form] = Form.useForm();

  const onFinish = async () => {
    const response: Response = await fetch(SIGN_IN_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        login: form.getFieldValue('username'),
        password: form.getFieldValue('password'),
      }),
      headers: { 'Content-Type': 'application/json; charset=UTF-8' },
    });

    if (!response.ok) {
      setWrongLogin(true);
      return;
    }

    setWrongLogin(false);

    const data = await response.json();

    setUser({
      id: data.id,
      login: data.username,
      role: data.roles[0],
      jwt: data.accessToken,
    });
  };

  const renderLogin = (): JSX.Element => (
    <div>
      <h1 style={{ marginLeft: '40%' }}>Login</h1>
      {wrongLogin && <h3 style={{ marginLeft: '25%', color: 'red' }}>Wrong login or password</h3>}
      <Form
        form={form}
        name="login"
        className="login-form"
        initialValues={{ remember: true }}
        onFinish={onFinish}
      >
        <Form.Item
          name="username"
          rules={[{ required: true, message: 'Please input your Username!' }]}
        >
          <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[{ required: true, message: 'Please input your Password!' }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item>
          <div style={{ textAlign: 'center' }}>
            <Button type="primary" htmlType="submit" className="login-form-button">
              Log in
            </Button>
            <span> Or </span>
            <Link to="/register">register now!</Link>
          </div>
        </Form.Item>
      </Form>
    </div>
  );

  return (
    <div>
      {user.role === 'ROLE_ADMIN' && <Redirect to="/admin" />}
      {user.role === 'ROLE_EMPLOYEE' && <Redirect to="/employee" />}
      {user.role === 'ROLE_CUSTOMER' && <Redirect to="/customer" />}
      {user.role === 'UNKNOWN' && renderLogin()}
    </div>
  );
};

export default LoginForm;
