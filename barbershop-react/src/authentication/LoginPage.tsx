import React from 'react';
import { Col, Layout, Menu, Row } from 'antd';
import { Header, Content } from 'antd/lib/layout/layout';
import { useRouteMatch, Link } from 'react-router-dom';
import LoginForm from './LoginForm';

const LogLayout = (): JSX.Element => {
  const { url } = useRouteMatch();

  return (
    <div>
      <Layout className="layout">
        <Header>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
            <Menu.Item key="1" isSelected>
              <Link to={`${url}`}>Log in</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to="/register">Register</Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Content>
          <div className="site-layout-content">
            <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
              <Col span={6} />
              <Col span={8}>
                <LoginForm />
              </Col>
              <Col span={8} />
            </Row>
          </div>
        </Content>
      </Layout>
    </div>
  );
};

export default LogLayout;
