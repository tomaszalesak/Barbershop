import React, { useEffect, useState } from 'react';
import { Button, Form, Input, Select } from 'antd';
import { Redirect } from 'react-router-dom';
import {
  ADM_GET_SERVICES_API_URI,
  DEL_SERVICE_API_URI,
  UPDT_SERVICE_API_URI,
  ADM_GET_SERV_BY_NAME_API_URI,
} from '../apiUris';
import { useStore } from '../store';

const { Option } = Select;

type Service = {
  id: number;
  name: string;
  durationMinutes: number;
  price: number;
};

const EditService = (): JSX.Element => {
  const [wrongDelete, setWrongDelete] = useState(false);
  const [wrongLoad, setWrongLoad] = useState(false);
  const [wrongUpdate, setWrongUpdate] = useState(false);
  const [rightUpdate, setRightUpdate] = useState(false);
  const [redir, setRedir] = useState(false);
  const [service, setService] = useState<Service | null>(null);
  const [allServ, setAllServ] = useState<Service[] | null>(null);
  const { user } = useStore();
  const [form] = Form.useForm();
  const [form2] = Form.useForm();
  const [form3] = Form.useForm();

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
        setWrongLoad(true);
        return;
      }
      setWrongLoad(false);
      setAllServ(await response.json());
    };
    fetchData();
  }, [user.jwt]);

  useEffect(() => {
    const refreshFields = async () => {
      form.resetFields();
    };
    refreshFields();
  }, [service, form]);

  const onFinish = async () => {
    const servicename = form.getFieldValue('name');
    const names = servicename.split(' ');
    let fullname = '';
    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < names.length; i++) {
      fullname += `${names[i]}_`;
    }
    const retname = fullname.slice(0, -1);
    const response: Response = await fetch(UPDT_SERVICE_API_URI, {
      method: 'PUT',
      body: JSON.stringify({
        id: service?.id,
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
      setWrongUpdate(true);
      setRightUpdate(false);
      return;
    }

    setWrongUpdate(false);
    setRightUpdate(true);
  };

  const onFinish2 = async () => {
    const response: Response = await fetch(DEL_SERVICE_API_URI + service?.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      setWrongDelete(true);
      return;
    }

    setWrongDelete(false);
    setRedir(true);
  };

  const onFieldsChange = async () => {
    const response: Response = await fetch(
      ADM_GET_SERV_BY_NAME_API_URI + form2.getFieldValue('serv_select'),
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      },
    );

    if (!response.ok) {
      return;
    }
    setService(await response.json());
  };

  const names = allServ?.map((serv) => {
    return serv.name;
  });

  if (redir) {
    return <Redirect to="/admin" />;
  }

  return (
    <div>
      <h1 style={{ marginLeft: '50%' }}>Edit service</h1>
      {wrongLoad && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Error: Data could not load, please try to log in again!
        </h3>
      )}
      {wrongDelete && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Error: Please select service before deleting!
        </h3>
      )}
      {wrongUpdate && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>Error: Could not update data!</h3>
      )}
      {rightUpdate && <h3 style={{ marginLeft: '50%', color: 'green' }}>Data updated</h3>}
      <Form form={form2} name="service_form" onValuesChange={onFieldsChange}>
        <Form.Item
          name="serv_select"
          label="Service"
          style={{ width: '230px', position: 'relative', left: '23%' }}
        >
          <Select style={{ width: '140px' }}>
            {names?.map((name) => (
              <Option key={name} value={name}>
                {name}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Form>
      <Form
        labelCol={{ xs: { span: 24 }, sm: { span: 8 } }}
        wrapperCol={{ xs: { span: 24 }, sm: { span: 16 } }}
        form={form}
        name="update_service"
        onFinish={onFinish}
        scrollToFirstError
      >
        <Form.Item
          name="name"
          label="Name"
          initialValue={service?.name}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item name="duration" label="Duration" initialValue={service?.durationMinutes}>
          <Input />
        </Form.Item>

        <Form.Item name="price" label="Price" initialValue={service?.price}>
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
        <Form form={form3} name="delete_service" layout="inline" onFinish={onFinish2}>
          <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
            <Button type="primary" htmlType="submit" danger>
              Delete service
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default EditService;
