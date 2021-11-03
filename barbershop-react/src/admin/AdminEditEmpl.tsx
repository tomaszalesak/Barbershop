import React from 'react';
import { Row, Col } from 'antd';
import EditEmployee from './EditEmployee';

const AdminEditEmpl = (): JSX.Element => {
  return (
    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
      <Col span={5} />
      <Col span={8}>
        <EditEmployee />
      </Col>
      <Col span={8} />
    </Row>
  );
};

export default AdminEditEmpl;
