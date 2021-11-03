/* eslint-disable no-else-return */
/* eslint-disable no-continue */
import React, { useEffect, useState } from 'react';
import { Button, Checkbox, Col, DatePicker, Form, Input, Row, Select, Table } from 'antd';
import {
  ADM_GET_EMPLOYEES_API_URI,
  ADM_GET_EMPL_BY_NAME_API_URI,
  RESERVATION_GET_WORKHOURS_API_URI,
  CREATE_RESERVATION_API_URI,
  GET_CUSTOMER_API_URI,
} from '../apiUris';
import { useStore } from '../store';
import { Customer, Employee, Service } from '../common/types';

const { Option } = Select;

type WorkingHours = {
  fromTime: string;
  toTime: string;
};
interface ICreateReservation {
  setUpdate: (update: boolean) => void;
  update: boolean;
}

const CreateReservation = (props: ICreateReservation): JSX.Element => {
  const [wrongLoad, setWrongLoad] = useState(false);
  const [resOK, setResOK] = useState(false);
  const [resNOK, setResNOK] = useState(false);
  const [selSer, setSelSer] = useState(false);
  const [selTime, setSelTime] = useState(false);
  const [selWhours, setSelWhours] = useState(false);
  const [validTime, setValidTime] = useState(false);
  const [setEmpl, setSetEmpl] = useState(false);
  const [employee, setEmployee] = useState<Employee | null>(null);
  const [whours, setWhours] = useState<WorkingHours[] | null>(null);
  const [allEmpl, setAllEmpl] = useState<Employee[] | null>(null);
  const [customer, setCustomer] = useState<Customer | null>(null);
  const [form] = Form.useForm();
  const [form2] = Form.useForm();
  const [form3] = Form.useForm();
  const { user } = useStore();
  const { update } = props;

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
  }, [user.jwt, update]);

  useEffect(() => {
    const refreshFields = async () => {
      form.resetFields();
    };
    refreshFields();
  }, [employee, form, update]);

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

  const onFieldsChange2 = async () => {
    if (form3.getFieldValue('date-picker') == null) {
      return;
    }
    const dat = form3.getFieldValue('date-picker').format('yyyy-MM-DD');
    const response: Response = await fetch(
      `${RESERVATION_GET_WORKHOURS_API_URI + employee?.id}/${dat}T00:00:00/${dat}T23:59:59`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      },
    );

    if (!response.ok) {
      setWrongLoad(true);
      setWhours(null);
      return;
    }
    setWrongLoad(false);
    setWhours(await response.json());
  };

  const onFinish = async () => {
    if (whours?.[0] === undefined) {
      setSelWhours(true);
      return;
    }
    if (employee == null) {
      setSetEmpl(true);
      return;
    }
    setSetEmpl(false);
    const services = form.getFieldValue('serv-select');
    if (services == null) {
      setSelSer(true);
      return;
    }
    setSelSer(false);
    const sDTOs = [];
    let mintotal = 0;
    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < services.length; i++) {
      const servicesSplit = services[i].split(', ');
      const tmpmin = servicesSplit[2].split(' ');
      mintotal += Number(tmpmin[0]);
      const tmpprice = servicesSplit[3].split(' ');
      const sDTO: Service = {
        id: Number(servicesSplit[0]),
        name: servicesSplit[1],
        durationMinutes: Number(tmpmin[0]),
        price: Number(tmpprice[0]),
      };
      sDTOs.push(sDTO);
    }

    if (form3.getFieldValue('timesel') == null) {
      setSelTime(true);
      return;
    }
    setSelTime(false);
    const totime = form3.getFieldValue('timesel').split(':');
    let hh = Number(totime[0]);
    const hhs = hh;
    let mm = Number(totime[1]);
    const mms = mm;
    mm += mintotal;
    if (mm >= 60 && mm < 120) {
      mm -= 60;
      hh += 1;
    } else if (mm >= 120 && mm < 180) {
      mm -= 120;
      hh += 2;
    }

    if (whours != null) {
      setSelWhours(false);
      // eslint-disable-next-line no-plusplus
      for (let i = whours.length - 1; i >= 0; i--) {
        const aux = whours[i].toTime.split('T');
        const aux2 = aux[1];
        const aux3 = aux2.split(':');
        const tmp = whours[i].fromTime.split('T');
        const tmp2 = tmp[1];
        const tmp3 = tmp2.split(':');
        if (hh > Number(aux3[0]) && i === 0) {
          setValidTime(true);
          return;
        } else if (hh > Number(aux3[0]) && i > 0) {
          continue;
        } else if (hh === Number(aux3[0]) && mm > Number(aux3[1]) && i === 0) {
          setValidTime(true);
          return;
        } else if (hh === Number(aux3[0]) && mm > Number(aux3[1]) && i > 0) {
          continue;
        } else if (hhs < Number(tmp3[0]) && i === 0) {
          setValidTime(true);
          return;
        } else if (hhs < Number(tmp3[0]) && i > 0) {
          continue;
        } else if (hhs === Number(tmp3[0]) && mms < Number(tmp3[1]) && i === 0) {
          setValidTime(true);
          return;
        } else if (hhs === Number(tmp3[0]) && mms < Number(tmp3[1]) && i > 0) {
          continue;
        } else {
          setValidTime(false);
          break;
        }
      }
    } else {
      setSelWhours(true);
      return;
    }

    const response: Response = await fetch(GET_CUSTOMER_API_URI + user.id, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      return;
    }
    setCustomer(await response.json());

    const response2: Response = await fetch(CREATE_RESERVATION_API_URI, {
      method: 'POST',
      body: JSON.stringify({
        id: null,
        fromTime: `${form3.getFieldValue('date-picker').format('yyyy-MM-DD')}T${form3.getFieldValue(
          'timesel',
        )}:00.000000`,
        toTime: `${form3.getFieldValue('date-picker').format('yyyy-MM-DD')}T${String(hh).padStart(
          2,
          '0',
        )}:${String(mm).padStart(2, '0')}:00.000000`,
        customerDTO: {
          id: user.id,
          login: customer?.login,
          password: customer?.password,
          firstname: customer?.firstname,
          lastname: customer?.lastname,
          phoneNumber: customer?.phoneNumber,
          city: customer?.city,
          street: customer?.street,
          postalCode: customer?.postalCode,
          role: null,
        },
        employeeDTO: {
          id: employee?.id,
          login: employee?.login,
          password: employee?.password,
          firstname: employee?.firstname,
          lastname: employee?.lastname,
          phoneNumber: employee?.phoneNumber,
          city: employee?.city,
          street: employee?.street,
          postalCode: employee?.postalCode,
          role: null,
          salary: employee?.salary,
          serviceDTOs: employee?.serviceDTOs,
        },
        serviceDTOs: sDTOs,
        done: false,
        feedbackText: null,
      }),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response2.ok) {
      setResNOK(true);
      setResOK(false);
      return;
    }
    setResNOK(false);
    setResOK(true);
    onFieldsChange2();
    props.setUpdate(!props.update);
  };

  const columns = [
    {
      title: 'From',
      dataIndex: 'from',
      key: 'from',
    },
    {
      title: 'To',
      dataIndex: 'to',
      key: 'to',
    },
  ];

  const data = whours?.map((workinghour) => ({
    from: workinghour.fromTime,
    to: workinghour.toTime,
  }));

  const names = allEmpl?.map((empl) => {
    return `${empl.firstname} ${empl.lastname}`;
  });

  const snames = employee?.serviceDTOs?.map((serv) => {
    return `${serv.id}, ${serv.name}, ${serv.durationMinutes} min, ${serv.price} Kč`;
  });

  return (
    <div>
      <h1>Reservation of service</h1>
      {wrongLoad && (
        <h3 style={{ color: 'red' }}>Error: Data could not load, please try to log in again!</h3>
      )}
      {selSer && <h3 style={{ color: 'red' }}>Please select services!</h3>}
      {selTime && <h3 style={{ color: 'red' }}>Please select time for your reservation!</h3>}
      {selWhours && (
        <h3 style={{ color: 'red' }}>Please select a date when the employee is working!</h3>
      )}
      {validTime && <h3 style={{ color: 'red' }}>Services time exceeded working hours!</h3>}
      {setEmpl && <h3 style={{ color: 'red' }}>Please select employee!</h3>}
      {resNOK && <h3 style={{ color: 'red' }}>Error: Reservation not created!</h3>}
      {resOK && <h3 style={{ color: 'green' }}>Reservation created successfully.</h3>}
      <Form form={form2} name="feedback_form" onValuesChange={onFieldsChange}>
        <Form.Item name="empl_select" label="Employee" style={{ width: '230px' }}>
          <Select style={{ width: '140px' }}>
            {names?.map((name) => (
              <Option key={name} value={name}>
                {name}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Form>
      <Form form={form} name="form2">
        <Form.Item name="serv-select" label="Services">
          <Checkbox.Group>
            {snames?.map((name) => (
              // eslint-disable-next-line react/jsx-key
              <Row>
                <Col span={50}>
                  <Checkbox key={name} value={name} style={{ lineHeight: '32px' }}>
                    {name}
                  </Checkbox>
                </Col>
              </Row>
            ))}
          </Checkbox.Group>
        </Form.Item>
      </Form>
      <Form form={form3} name="form3" onValuesChange={onFieldsChange2} onFinish={onFinish}>
        <Form.Item name="date-picker" label="Date">
          <DatePicker style={{ marginLeft: '30px' }} />
        </Form.Item>
        <h3>Free working hours</h3>
        <Table columns={columns} dataSource={data} />
        <Form.Item
          name="timesel"
          label="Reservation time"
          rules={[
            {
              type: 'string',
              min: 5,
              max: 5,
              message: 'The input is not valid! Do you have the right format?',
            },
          ]}
        >
          <Input placeholder="Format: HH:mm" style={{ width: '50%' }} />
        </Form.Item>
        <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
          <div style={{ position: 'relative', left: '-27%' }}>
            <Button type="primary" htmlType="submit">
              Create reservation
            </Button>
          </div>
        </Form.Item>
      </Form>
    </div>
  );
};

export default CreateReservation;
