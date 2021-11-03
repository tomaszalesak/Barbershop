import React from 'react';
import { TimePicker } from 'antd';

const format = 'HH:mm';

const EmplTimeSel = (): JSX.Element => {
  return (
    <span>
      <span>Time: </span>
      <TimePicker.RangePicker format={format} />
    </span>
  );
};

export default EmplTimeSel;
