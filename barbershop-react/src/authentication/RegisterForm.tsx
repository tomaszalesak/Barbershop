import React, { useState } from 'react';
import { Button, Form, Input } from 'antd';
import { Redirect } from 'react-router-dom';
import { REGISTER_API_URI, SIGN_IN_API_URI } from '../apiUris';
import { useStore } from '../store';

const RegisterForm = (): JSX.Element => {
  const [wrongRegister, setWrongRegister] = useState(false);
  const [customerRedirect, setCustomerRedirect] = useState(false);
  const { setUser } = useStore();
  const [form] = Form.useForm();

  const onFinish = async () => {
    const response: Response = await fetch(REGISTER_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        id: null,
        login: form.getFieldValue('login'),
        password: form.getFieldValue('password'),
        firstname: form.getFieldValue('firstname'),
        lastname: form.getFieldValue('lastname'),
        phoneNumber: form.getFieldValue('phone'),
        city: form.getFieldValue('city'),
        street: form.getFieldValue('street'),
        postalCode: form.getFieldValue('postalcode'),
      }),
      headers: { 'Content-Type': 'application/json; charset=UTF-8' },
    });

    if (!response.ok) {
      setWrongRegister(true);
      return;
    }

    setWrongRegister(false);

    const response2: Response = await fetch(SIGN_IN_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        login: form.getFieldValue('login'),
        password: form.getFieldValue('password'),
      }),
      headers: { 'Content-Type': 'application/json; charset=UTF-8' },
    });

    if (!response.ok) {
      // eslint-disable-next-line no-console
      console.log('LOGIN NOK');
      return;
    }

    const data = await response2.json();

    if (data.roles[0] === 'ROLE_CUSTOMER') {
      setCustomerRedirect(true);
    } else {
      setWrongRegister(true);
    }

    setUser({
      id: data.id,
      login: data.username,
      role: data.roles[0],
      jwt: data.accessToken,
    });
  };

  return (
    <div>
      {customerRedirect && <Redirect to="/customer" />}
      <h1 style={{ marginLeft: '50%' }}>Register</h1>
      {wrongRegister && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Something went wrong with the registration. Try again
        </h3>
      )}
      <Form
        labelCol={{ xs: { span: 24 }, sm: { span: 8 } }}
        wrapperCol={{ xs: { span: 24 }, sm: { span: 16 } }}
        form={form}
        name="register"
        onFinish={onFinish}
        initialValues={{
          prefix: '+420',
        }}
        scrollToFirstError
      >
        <Form.Item
          name="login"
          label="Login"
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
            { required: true, message: 'Please input your login!', whitespace: false },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="password"
          label="Password"
          rules={[
            {
              required: true,
              message: 'Please input your password!',
            },
          ]}
          hasFeedback
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name="confirm"
          label="Confirm Password"
          dependencies={['password']}
          hasFeedback
          rules={[
            {
              required: true,
              message: 'Please confirm your password!',
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue('password') === value) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error('The two passwords that you entered do not match!'),
                );
              },
            }),
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name="firstname"
          label="First name"
          rules={[
            {
              required: true,
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
          rules={[
            {
              required: true,
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item name="phone" label="Phone Number">
          <Input style={{ width: '100%' }} />
        </Form.Item>

        <Form.Item
          name="city"
          label="City"
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
              Register
            </Button>
          </div>
        </Form.Item>
      </Form>
    </div>
  );
};

export default RegisterForm;
