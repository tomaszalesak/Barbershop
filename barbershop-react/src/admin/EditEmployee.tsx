import React, { useEffect, useState } from 'react';
import { Button, Form, Input, Select } from 'antd';
import { Redirect } from 'react-router-dom';
import {
  ADM_GET_EMPLOYEES_API_URI,
  DEL_EMPLOYEE_API_URI,
  UPDT_EMPLOYEE_API_URI,
  ADM_GET_EMPL_BY_NAME_API_URI,
} from '../apiUris';
import { useStore } from '../store';

const { Option } = Select;

type Service = {
  id: number;
  name: string;
  durationMinutes: number;
  price: number;
};

type Employee = {
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
  salary: number | null;
  serviceDTOs: Service[] | null;
};

const EditEmployee = (): JSX.Element => {
  const [wrongDelete, setWrongDelete] = useState(false);
  const [wrongLoad, setWrongLoad] = useState(false);
  const [wrongUpdate, setWrongUpdate] = useState(false);
  const [rightUpdate, setRightUpdate] = useState(false);
  const [redir, setRedir] = useState(false);
  const [employee, setEmployee] = useState<Employee | null>(null);
  const [allEmpl, setAllEmpl] = useState<Employee[] | null>(null);
  const { user } = useStore();
  const [form] = Form.useForm();
  const [form2] = Form.useForm();
  const [form3] = Form.useForm();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(ADM_GET_EMPLOYEES_API_URI, {
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
      setAllEmpl(await response.json());
    };
    fetchData();
  }, [user.jwt]);

  useEffect(() => {
    const refreshFields = async () => {
      form.resetFields();
    };
    refreshFields();
  }, [employee, form]);

  const onFinish = async () => {
    const response: Response = await fetch(UPDT_EMPLOYEE_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        id: employee?.id,
        login: employee?.login,
        password: employee?.password,
        firstname: form.getFieldValue('firstname'),
        lastname: form.getFieldValue('lastname'),
        phoneNumber: form.getFieldValue('phone'),
        city: form.getFieldValue('city'),
        street: form.getFieldValue('street'),
        postalCode: form.getFieldValue('postalcode'),
        role: employee?.role,
        salary: form.getFieldValue('salary'),
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
    const response: Response = await fetch(DEL_EMPLOYEE_API_URI + employee?.id, {
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
    const selname = form2.getFieldValue('empl_select');
    const fname = selname.split(' ');
    const response: Response = await fetch(
      `${ADM_GET_EMPL_BY_NAME_API_URI + fname[0]}/${fname[1]}`,
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
    setEmployee(await response.json());
  };

  const names = allEmpl?.map((empl) => {
    return `${empl.firstname} ${empl.lastname}`;
  });

  if (redir) {
    return <Redirect to="/admin" />;
  }

  return (
    <div>
      <h1 style={{ marginLeft: '50%' }}>Edit employee</h1>
      {wrongLoad && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Error: Data could not load, please try to log in again!
        </h3>
      )}
      {wrongDelete && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>
          Error: Please select employee before deleting!
        </h3>
      )}
      {wrongUpdate && (
        <h3 style={{ marginLeft: '25%', color: 'red' }}>Error: Could not update data!</h3>
      )}
      {rightUpdate && <h3 style={{ marginLeft: '55%', color: 'green' }}>Data updated</h3>}
      <Form form={form2} name="feedback_form" onValuesChange={onFieldsChange}>
        <Form.Item
          name="empl_select"
          label="Employee"
          style={{ width: '230px', position: 'relative', left: '20%' }}
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
        name="register"
        onFinish={onFinish}
        scrollToFirstError
      >
        <Form.Item
          name="firstname"
          label="First name"
          initialValue={employee?.firstname}
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
          initialValue={employee?.lastname}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item name="phone" label="Phone Number" initialValue={employee?.phoneNumber}>
          <Input />
        </Form.Item>

        <Form.Item
          name="city"
          label="City"
          initialValue={employee?.city}
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
          initialValue={employee?.street}
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
          initialValue={employee?.postalCode}
          rules={[
            {
              type: 'string',
              message: 'The input is not valid string!',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item name="salary" label="Salary" initialValue={employee?.salary}>
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
        <Form form={form3} name="delete_employee" layout="inline" onFinish={onFinish2}>
          <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
            <Button type="primary" htmlType="submit" danger>
              Delete profile
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default EditEmployee;
