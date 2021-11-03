import React, { useEffect, useState } from 'react';
import { Alert, Button, Table } from 'antd';
import { useStore } from '../store';
import { Reservation } from '../common/types';
import { GET_EMPLOYEE_RESERVATIONS_API_URI, UPDATE_RESERVATION_STATUS_API_URI } from '../apiUris';

const EmployeeHome = (): JSX.Element => {
  const [error, setError] = useState('');
  const [showError, setShowError] = useState(false);
  const [reservations, setReservations] = useState<Reservation[]>();
  const { user } = useStore();
  const [updateUI, setUpdateUI] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      const response: Response = await fetch(`${GET_EMPLOYEE_RESERVATIONS_API_URI}/${user.id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
      });

      if (!response.ok) {
        setError('There are no reservations for you 😁');
        setShowError(true);
        return;
      }

      setShowError(false);
      setReservations(await response.json());
    };
    fetchData();
  }, [user.id, user.jwt, updateUI]);

  const setAsDoneReservation = (id: string): void => {
    const setData = async () => {
      const response: Response = await fetch(UPDATE_RESERVATION_STATUS_API_URI, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Authorization: `Bearer ${user.jwt}`,
        },
        body: JSON.stringify({
          id,
          status: true,
        }),
      });

      if (!response.ok) {
        setError('Could not set as done the reservation 😕');
        setShowError(true);
        return;
      }
      setShowError(false);
      setUpdateUI(!updateUI);
    };
    setData();
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
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
      title: 'Done',
      dataIndex: 'done',
      key: 'done',
    },
    {
      title: ' ',
      dataIndex: 'setasdone',
      key: 'setasdone',
      render(id: string) {
        return (
          <Button type="primary" onClick={() => setAsDoneReservation(id)}>
            Set as Done
          </Button>
        );
      },
    },
  ];

  const data = reservations?.map((reservation) => ({
    id: reservation.id,
    time: reservation.fromTime,
    feedback: reservation.feedbackText,
    done: reservation.done?.valueOf().toString(),
    setasdone: reservation.id,
  }));

  return (
    <>
      <h1>Reservations</h1>
      {showError && <Alert message={error} type="error" style={{ marginBottom: '25px' }} />}
      <Table columns={columns} dataSource={data} />
    </>
  );
};

export default EmployeeHome;
