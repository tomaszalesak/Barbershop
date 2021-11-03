import React from 'react';
import { DatePicker, Space } from 'antd';

function onChange(date: unknown, dateString: unknown) {
  // eslint-disable-next-line no-console
  console.log(date, dateString);
}

const CustDateSel = (): JSX.Element => {
  return (
    <Space direction="vertical">
      <DatePicker onChange={onChange} />
    </Space>
  );
};

export default CustDateSel;
