import React, { useState } from 'react';
import { Container, Row, Col, Card, Tab, Tabs } from 'react-bootstrap';
import LoginForm from '../components/LoginForm';
import SignUpForm from '../components/SignUpForm';
import '../css/LoginPage.css';

const LoginPage = () => {
  const [activeTab, setActiveTab] = useState('login');
  
  return (
    <Container className="container">
      <Row>
        <Col>
          <Card className="p-4 shadow-lg">
            <Card.Body>
              <Tabs
                id="login-signup-tabs"
                activeKey={activeTab}
                onSelect={(tab) => setActiveTab(tab)}
                className="mb-4"
              >
                <Tab eventKey="login" title="Login">
                  <LoginForm />
                </Tab>
                <Tab eventKey="signup" title="Sign Up">
                  <SignUpForm />
                </Tab>
              </Tabs>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginPage;
