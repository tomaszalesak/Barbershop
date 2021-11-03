import React from 'react';
import { Col, Layout, Menu, Row } from 'antd';
import { Header, Content } from 'antd/lib/layout/layout';
import { useRouteMatch, Link } from 'react-router-dom';
import RegisterForm from './RegisterForm';

const RegLayout = (): JSX.Element => {
  const { url } = useRouteMatch();

  return (
    <div>
      <Layout className="layout">
        <Header>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['2']}>
            <Menu.Item key="1">
              <Link to="/login">Log in</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to={`${url}`}>Register</Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Content>
          <div className="site-layout-content">
            <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
              <Col span={6} />
              <Col span={8}>
                <RegisterForm />
              </Col>
              <Col span={8} />
            </Row>
          </div>
        </Content>
      </Layout>
    </div>
  );
};

export default RegLayout;
