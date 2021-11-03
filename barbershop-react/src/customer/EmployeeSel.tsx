import React from 'react';
import { Select } from 'antd';

const EmployeeSel = (): JSX.Element => {
  return (
    <Select style={{ width: 130 }}>
      <Select.Option value="any" selected>
        Any
      </Select.Option>
    </Select>
  );
};

export default EmployeeSel;
