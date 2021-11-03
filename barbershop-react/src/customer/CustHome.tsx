import React, { useState } from 'react';
import { Row, Col } from 'antd';
import CreateReservation from './CreateReservation';
import CustReservations from './CustReservations';

const CustHome = (): JSX.Element => {
  const [update, setUpdate] = useState<boolean>(false);

  return (
    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
      <Col span={7}>
        <CreateReservation update={update} setUpdate={setUpdate} />
      </Col>
      <Col span={17}>
        <CustReservations update={update} />
      </Col>
    </Row>
  );
};

export default CustHome;
