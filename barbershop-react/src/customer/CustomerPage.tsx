import React from 'react';
import { Layout, Menu } from 'antd';
import { Header, Content } from 'antd/lib/layout/layout';
import { useRouteMatch, Route, Switch, Link, Redirect } from 'react-router-dom';
import CustHome from './CustHome';
import CustProfile from './CustProfile';
import LogOutButton from '../common/LogOutButton';
import { useStore } from '../store';

const CustomerPage = (): JSX.Element => {
  const { url, path } = useRouteMatch();
  const { user } = useStore();

  if (user.role !== 'ROLE_CUSTOMER') {
    return <Redirect to="/login" />;
  }

  return (
    <div>
      <Layout className="layout">
        <Header>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
            <Menu.Item key="1">
              <Link to={`${url}`}>Home</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to={`${url}/profile`}>Profile</Link>
            </Menu.Item>
          </Menu>
          <LogOutButton />
        </Header>
        <Content>
          <div className="site-layout-content">
            <Switch>
              <Route exact path={path}>
                <CustHome />
              </Route>
              <Route path={`${path}/profile`}>
                <CustProfile />
              </Route>
            </Switch>
          </div>
        </Content>
      </Layout>
    </div>
  );
};

export default CustomerPage;
