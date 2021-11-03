import React from 'react';
import { Row, Col } from 'antd';
import ServiceCalendar from './ServiceCalendar';
import ProvidedServ from './ProvidedServices';

const EmplMiddleCol = (): JSX.Element => {
  return (
    <div>
      <Row style={{ marginBottom: '100px' }}>
        <Col>
          <ServiceCalendar />
        </Col>
      </Row>
      <Row>
        <Col>
          <ProvidedServ />
        </Col>
      </Row>
    </div>
  );
};

export default EmplMiddleCol;
