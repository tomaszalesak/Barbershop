import React from 'react';
import { Row, Col } from 'antd';
import Customers from './Customers';
import Employees from './Employees';
import Services from './Services';

const AdminHome = (): JSX.Element => {
  return (
    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
      <Col span={8}>
        <Employees />
      </Col>
      <Col span={8}>
        <Services />
      </Col>
      <Col span={8}>
        <Customers />
      </Col>
    </Row>
  );
};

export default AdminHome;
