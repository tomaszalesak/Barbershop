import React from 'react';
import { Row, Col } from 'antd';
import CustEdit from './CustEdit';

const CustProfile = (): JSX.Element => {
  return (
    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
      <Col span={6} />
      <Col span={8}>
        <CustEdit />
      </Col>
      <Col span={8} />
    </Row>
  );
};

export default CustProfile;
