import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    console.log('Logging in with', { email, password });
  };

  return (
    <Form onSubmit={handleLogin}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <PasswordToggle password={password} setPassword={setPassword} />
      </Form.Group>
      <Button variant="primary" type="submit" className="w-100">
        Log In
      </Button>
    </Form>
  );
};

export default LoginForm;
