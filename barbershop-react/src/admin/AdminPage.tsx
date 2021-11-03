import React from 'react';
import { Layout, Menu } from 'antd';
import { Header, Content } from 'antd/lib/layout/layout';
import { Link, useRouteMatch, Switch, Route, Redirect } from 'react-router-dom';
import AdminHome from './AdminHome';
import AdminAddEmpl from './AdminAddEmpl';
import AdminAddServ from './AdminAddServ';
import LogOutButton from '../common/LogOutButton';
import { useStore } from '../store';
import AdminEditEmpl from './AdminEditEmpl';
import AdminEditServ from './AdminEditServ';

const AdminPage = (): JSX.Element => {
  const { url, path } = useRouteMatch();
  const { user } = useStore();

  if (user.role !== 'ROLE_ADMIN') {
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
              <Link to={`${url}/add-employee`}>Add employee</Link>
            </Menu.Item>
            <Menu.Item key="3">
              <Link to={`${url}/edit-employee`}>Edit employee</Link>
            </Menu.Item>
            <Menu.Item key="4">
              <Link to={`${url}/add-service`}>Add service</Link>
            </Menu.Item>
            <Menu.Item key="5">
              <Link to={`${url}/edit_service`}>Edit service</Link>
            </Menu.Item>
          </Menu>
          <LogOutButton />
        </Header>
        <Content>
          <div className="site-layout-content">
            <Switch>
              <Route exact path={path}>
                <AdminHome />
              </Route>
              <Route path={`${path}/add-employee`}>
                <AdminAddEmpl />
              </Route>
              <Route path={`${path}/edit-employee`}>
                <AdminEditEmpl />
              </Route>
              <Route path={`${path}/add-service`}>
                <AdminAddServ />
              </Route>
              <Route path={`${path}/edit_service`}>
                <AdminEditServ />
              </Route>
            </Switch>
          </div>
        </Content>
      </Layout>
    </div>
  );
};

export default AdminPage;
