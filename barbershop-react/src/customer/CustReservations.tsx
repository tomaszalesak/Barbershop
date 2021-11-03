import { Button, Col, Form, Row, Table, Input, Select } from 'antd';
import React, { useEffect, useState } from 'react';
import {
  CUST_GET_RESERVATIONS_API_URI,
  DELETE_RESERVATION_API_URI,
  UPDATE_RESERVATION_FEEDBACK_API_URI,
} from '../apiUris';
import { Reservation } from '../common/types';
import { useStore } from '../store';

const { TextArea } = Input;
const { Option } = Select;
interface ICustReservations {
  update: boolean;
}

const CustReservations = (update: ICustReservations): JSX.Element => {
  const [noRes, setNoRes] = useState(false);
  const [wrongUpdate, setWrongUpdate] = useState(false);
  const [rightUpdate, setRightUpdate] = useState(false);
  const [wrongDelete, setWrongDelete] = useState(false);
  const [redir, setRedir] = useState(false);
  const [reservations, setReservations] = useState<Reservation[] | null>(null);
  const { user } = useStore();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(CUST_GET_RESERVATIONS_API_URI + user.id, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setNoRes(true);
        return;
      }
      setNoRes(false);
      setReservations(await response.json());
    };
    fetchData();
  }, [user.id, user.jwt, redir, update]);

  const deleteReservation = (id: string): void => {
    const deleteData = async () => {
      const response: Response = await fetch(DELETE_RESERVATION_API_URI + id, {
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
      if (!redir) {
        setRedir(true);
      } else {
        setRedir(false);
      }
    };
    deleteData();
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Employee',
      dataIndex: 'employee',
      key: 'employee',
    },
    {
      title: 'Time',
      dataIndex: 'time',
      key: 'time',
    },
    {
      title: 'Feedback',
      dataIndex: 'feedback',
      key: 'feedback',
    },
    {
      title: ' ',
      dataIndex: 'delete',
      key: 'delete',
      render(id: string) {
        return (
          <Button type="primary" shape="circle" danger onClick={() => deleteReservation(id)}>
            X
          </Button>
        );
      },
    },
  ];

  const data = reservations?.map((reservation) => ({
    id: reservation.id,
    employee: `${reservation.employeeDTO.firstname} ${reservation.employeeDTO.lastname}`,
    time: reservation.fromTime,
    feedback: reservation.feedbackText,
    delete: reservation.id,
  }));

  const ids = reservations?.map((reservation) => {
    return reservation.id;
  });

  const onFinish = async () => {
    const response: Response = await fetch(UPDATE_RESERVATION_FEEDBACK_API_URI, {
      method: 'PUT',
      body: JSON.stringify({
        id: form.getFieldValue('id_select'),
        feedbackText: form.getFieldValue('textfield'),
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
    if (!redir) {
      setRedir(true);
    } else {
      setRedir(false);
    }
  };

  return (
    <div>
      <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
        <Col span={12}>
          <h1>Reservations</h1>
          {noRes && <h3 style={{ marginLeft: '15%', color: 'green' }}>No reservations</h3>}
          {wrongDelete && (
            <h3 style={{ marginLeft: '15%', color: 'green' }}>Could not delete reservation!</h3>
          )}
          <Table columns={columns} dataSource={data} />
        </Col>
        <Col span={8}>
          <h1>Feedback</h1>
          {wrongUpdate && (
            <h3 style={{ marginLeft: '0', color: 'red' }}>Failed! Feedback not posted.</h3>
          )}
          {rightUpdate && (
            <h3 style={{ marginLeft: '0', color: 'green' }}>Feedback posted successfully!</h3>
          )}
          <Form form={form} name="feedback_form" onFinish={onFinish}>
            <Form.Item name="id_select" label="Reservation ID">
              <Select>
                {ids?.map((id) => (
                  <Option key={id} value={id}>
                    {id}
                  </Option>
                ))}
              </Select>
            </Form.Item>
            <Form.Item name="textfield">
              <TextArea rows={6} placeholder="Write your feedback" />
            </Form.Item>

            <Form.Item wrapperCol={{ xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } }}>
              <Button type="primary" htmlType="submit">
                Submit
              </Button>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default CustReservations;
