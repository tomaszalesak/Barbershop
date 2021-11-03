import React, { useState } from 'react';
import { Button, Form, Input } from 'antd';
import { CRT_EMPL_API_URI } from '../apiUris';
import { useStore } from '../store';

const AddEmployee = (): JSX.Element => {
  const [wrongCreate, setWrongCreate] = useState(false);
  const [rightCreate, setRightCreate] = useState(false);
  const { user } = useStore();
  const [form] = Form.useForm();

  const onFinish = async () => {
    const response: Response = await fetch(CRT_EMPL_API_URI, {
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
        salary: form.getFieldValue('salary'),
      }),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      setWrongCreate(true);
      setRightCreate(false);
      return;
    }

    setWrongCreate(false);
    setRightCreate(true);
  };

  return (
    <div>
      <h1 style={{ marginLeft: '40%' }}>Add employee</h1>
      {wrongCreate && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Something went wrong with the registration. Try again
        </h3>
      )}
      {rightCreate && <h3 style={{ marginLeft: '40%', color: 'green' }}>Employee created</h3>}
      <Form
        labelCol={{ xs: { span: 24 }, sm: { span: 8 } }}
        wrapperCol={{ xs: { span: 24 }, sm: { span: 16 } }}
        form={form}
        name="add-employee"
        // eslint-disable-next-line no-console
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
            {
              required: true,
              message: 'Please input employee login!',
              whitespace: false,
            },
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
              message: 'Please input employee password!',
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
              message: 'Please confirm password!',
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

        <Form.Item
          name="salary"
          label="Salary"
          rules={[
            {
              type: 'string',
              message: 'The input is not valid number!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          wrapperCol={{
            xs: { span: 24, offset: 0 },
            sm: { span: 16, offset: 8 },
          }}
        >
          <Button type="primary" htmlType="submit">
            Add employee
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default AddEmployee;
