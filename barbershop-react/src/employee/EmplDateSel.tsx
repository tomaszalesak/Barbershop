import React from 'react';
import { DatePicker, Space } from 'antd';

function onChange(date: unknown, dateString: unknown) {
  // eslint-disable-next-line no-console
  console.log(date, dateString);
}

const EmplDateSel = (): JSX.Element => {
  return (
    <span>
      <span>Date: </span>
      <Space direction="vertical">
        <DatePicker onChange={onChange} />
      </Space>
    </span>
  );
};

export default EmplDateSel;
