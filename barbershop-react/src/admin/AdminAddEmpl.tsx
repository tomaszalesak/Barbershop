import React from 'react';
import { Row, Col } from 'antd';
import AddEmployee from './AddEmployee';

const AdminAddEmpl = (): JSX.Element => {
  return (
    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
      <Col span={6} />
      <Col span={8}>
        <AddEmployee />
      </Col>
      <Col span={8} />
    </Row>
  );
};

export default AdminAddEmpl;
