import React from 'react';
import { Layout, Menu } from 'antd';
import { Header, Content } from 'antd/lib/layout/layout';
import { Link, Redirect, Route, Switch, useRouteMatch } from 'react-router-dom';
import LogOutButton from '../common/LogOutButton';
import { useStore } from '../store';
import EmployeeHome from './EmployeeHome';
import EmployeeServices from './EmployeeServices';
import EmployeeWorkingHours from './EmployeeWorkingHours';

const EmployeePage = (): JSX.Element => {
  const { url, path } = useRouteMatch();
  const { user } = useStore();

  if (user.role !== 'ROLE_EMPLOYEE') {
    return <Redirect to="/login" />;
  }

  return (
    <Layout className="layout">
      <Header>
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
          <Menu.Item key="1">
            <Link to={`${url}`}>Home</Link>
          </Menu.Item>
          <Menu.Item key="2">
            <Link to={`${url}/working-hours`}>Working Hours</Link>
          </Menu.Item>
          <Menu.Item key="3">
            <Link to={`${url}/services`}>Services</Link>
          </Menu.Item>
        </Menu>
        <LogOutButton />
      </Header>
      <Content>
        <div className="site-layout-content">
          <Switch>
            <Route exact path={path}>
              <EmployeeHome />
            </Route>
            <Route path={`${path}/working-hours`}>
              <EmployeeWorkingHours />
            </Route>
            <Route path={`${path}/services`}>
              <EmployeeServices />
            </Route>
          </Switch>
        </div>
      </Content>
    </Layout>
  );
};

export default EmployeePage;
