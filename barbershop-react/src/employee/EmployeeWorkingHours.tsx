import React, { useEffect, useState } from 'react';
import { Alert, Button, DatePicker, Table, Row, Col } from 'antd';
import Form, { useForm } from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import moment from 'moment';
import {
  CREATE_WORKING_HOURS_API_URI,
  DELETE_WORKING_HOURS_API_URI,
  GET_CUSTOMER_WORKING_HOURS_API_URI,
} from '../apiUris';
import { useStore } from '../store';

const { RangePicker } = DatePicker;

type WorkingHours = {
  id: number;
  fromTime: string;
  toTime: string;
};

const EmployeeWorkingHours = (): JSX.Element => {
  const [error, setError] = useState('');
  const [showError, setShowError] = useState(false);
  const [workingHours, setWorkingHours] = useState<WorkingHours[]>();
  const { user } = useStore();
  const [rerender, setRerender] = useState(false);
  const [form] = useForm();

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(`${GET_CUSTOMER_WORKING_HOURS_API_URI}/${user.id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setError('Could not get your working hours 😭');
        setShowError(true);
        return;
      }

      setShowError(false);
      setWorkingHours(await response.json());
    };
    fetchData();
  }, [user.id, user.jwt, rerender]);

  const deleteWorkingHours = (text: string, record: WorkingHours): void => {
    const deleteData = async () => {
      const response: Response = await fetch(`${DELETE_WORKING_HOURS_API_URI}/${record.id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setError('Could not delete working hours, you have to work 😭');
        setShowError(true);
        return;
      }
      setRerender(!rerender);
      setShowError(false);
    };
    deleteData();
  };

  const columns = [
    {
      title: 'From',
      dataIndex: 'fromTime',
      key: 'fromTime',
      render: (x: string) => moment(x).format('D. M. YYYY H:mm'),
    },
    {
      title: 'To',
      dataIndex: 'toTime',
      key: 'toTime',
      render: (x: string) => moment(x).format('D. M. YYYY H:mm'),
    },
    {
      title: 'Delete',
      dataIndex: 'delete',
      key: 'delete',
      render(text: string, record: WorkingHours) {
        return (
          <Button danger onClick={() => deleteWorkingHours(text, record)}>
            Delete
          </Button>
        );
      },
    },
  ];

  const addWorkingHours = async () => {
    if (form == null) {
      setError('You need to select datetime values 🧐');
      setShowError(true);
      return;
    }
    if (form.getFieldValue('range') == null) {
      setError('You need to select datetime values 🧐');
      setShowError(true);
      return;
    }
    const xfromTime: moment.Moment = form.getFieldValue('range')[0];
    const xtoTime: moment.Moment = form.getFieldValue('range')[1];

    if (xfromTime == null || xtoTime == null) {
      setError('You need to select datetime values 🧐');
      setShowError(true);
      return;
    }
    const response: Response = await fetch(`${CREATE_WORKING_HOURS_API_URI}/${user.id}`, {
      method: 'POST',
      body: JSON.stringify({
        fromTime: xfromTime.toISOString(),
        toTime: xtoTime.toISOString(),
      }),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${user.jwt}`,
      },
    });

    if (!response.ok) {
      setError('Could not create working hours 😭');
      setShowError(true);
      return;
    }

    setShowError(false);
    setRerender(!rerender);
  };

  return (
    <>
      {showError && <Alert message={error} type="error" style={{ marginBottom: '25px' }} />}
      <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
        <Col className="gutter-row" span={8}>
          <Form form={form}>
            <FormItem name="range">
              <RangePicker showTime />
            </FormItem>
          </Form>
        </Col>
        <Col className="gutter-row" span={4}>
          <Button type="primary" onClick={addWorkingHours}>
            Add working hours
          </Button>
        </Col>

        <Col className="gutter-row" span={12}>
          <>
            <Table dataSource={workingHours} columns={columns} pagination={false} rowKey="id" />
          </>
        </Col>
      </Row>
    </>
  );
};

export default EmployeeWorkingHours;
