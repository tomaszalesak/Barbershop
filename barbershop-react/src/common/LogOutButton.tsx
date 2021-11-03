import React from 'react';
import { Button } from 'antd';
import { Link } from 'react-router-dom';
import { useStore } from '../store';

const LogOutButton = (): JSX.Element => {
  const { setUser } = useStore();
  return (
    <Button
      type="primary"
      htmlType="submit"
      style={{ float: 'right', top: '-50px' }}
      onClick={() => {
        setUser({
          id: null,
          login: null,
          role: 'UNKNOWN',
          jwt: null,
        });
      }}
    >
      <Link to="/login">Log out</Link>
    </Button>
  );
};

export default LogOutButton;
