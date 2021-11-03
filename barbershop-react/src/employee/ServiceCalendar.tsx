import React from 'react';
import { Button, Form } from 'antd';
import EmplDateSel from './EmplDateSel';
import EmplTimeSel from './EmplTimeSel';

const ServiceCalendar = (): JSX.Element => {
  return (
    <div>
      <h1>Select working hours</h1>
      <Form name="empl-calendar" className="empl-calendar-form" initialValues={{ remember: true }}>
        <Form.Item name="date">
          <div>
            <EmplDateSel />
          </div>
        </Form.Item>
        <Form.Item name="working-hours">
          <div>
            <EmplTimeSel />
          </div>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" className="empl-calendar-button">
            Confirm
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ServiceCalendar;
