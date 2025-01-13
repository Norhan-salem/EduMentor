import React, { useState } from 'react';
import { Container, Row, Col, Card, Tabs, Tab } from 'react-bootstrap';
import LoginForm from '../components/LoginForm';
import SignUpForm from '../components/SignUpForm';
import '../styles/Auth.css';

const LoginPage = () => {
  const [activeTab, setActiveTab] = useState('login');
  
  return (
    <Container className="d-flex justify-content-center align-items-center mt-5 mb-5">
      <Row className="w-100 ">
        <Col lg={6} md={8} sm={10} className="mx-auto">
          <Card className="p-4 shadow-lg auth-form">
            <Card.Body className='auth-form'>
              <Tabs
                id="login-signup-tabs"
                activeKey={activeTab}
                onSelect={(tab) => setActiveTab(tab)}
                className="mb-4 auth-form"
              >
                <Tab eventKey="login" title="Login" className="auth-form">
                  <LoginForm />
                </Tab>
                <Tab eventKey="signup" title="Sign Up" className='auth-form'>
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


