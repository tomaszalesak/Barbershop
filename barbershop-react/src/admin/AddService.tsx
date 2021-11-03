import React, { useState } from 'react';
import { Button, Form, Input } from 'antd';
import { CRT_SERV_API_URI } from '../apiUris';
import { useStore } from '../store';

const AddService = (): JSX.Element => {
  const [wrongCreate, setWrongCreate] = useState(false);
  const [rightCreate, setRightCreate] = useState(false);
  const { user } = useStore();
  const [form] = Form.useForm();

  const onFinish = async () => {
    const servicename = form.getFieldValue('name');
    const names = servicename.split(' ');
    let fullname = '';
    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < names.length; i++) {
      fullname += `${names[i]}_`;
    }
    const retname = fullname.slice(0, -1);
    const response: Response = await fetch(CRT_SERV_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        id: null,
        name: retname,
        durationMinutes: form.getFieldValue('duration'),
        price: form.getFieldValue('price'),
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
      <h1 style={{ marginLeft: '40%' }}>Add service</h1>
      {wrongCreate && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Something went wrong with the creation. Try again
        </h3>
      )}
      {rightCreate && <h3 style={{ marginLeft: '40%', color: 'green' }}>Service created</h3>}
      <Form
        labelCol={{ xs: { span: 24 }, sm: { span: 8 } }}
        wrapperCol={{ xs: { span: 24 }, sm: { span: 16 } }}
        form={form}
        name="add-service"
        // eslint-disable-next-line no-console
        onFinish={onFinish}
        scrollToFirstError
      >
        <Form.Item
          name="name"
          label="Name"
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
            { required: true, message: 'Please input name of the service!' },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="duration"
          label="Duration minutes"
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
            {
              required: true,
              message: 'Please input duration in minutes!',
              whitespace: false,
            },
          ]}
        >
          <Input style={{ width: '50%' }} />
        </Form.Item>

        <Form.Item
          name="price"
          label="Price"
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
            {
              required: true,
              message: 'Please input price!',
              whitespace: false,
            },
          ]}
        >
          <Input style={{ width: '50%' }} />
        </Form.Item>

        <Form.Item
          wrapperCol={{
            xs: { span: 24, offset: 0 },
            sm: { span: 16, offset: 8 },
          }}
        >
          <Button type="primary" htmlType="submit">
            Add service
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default AddService;
