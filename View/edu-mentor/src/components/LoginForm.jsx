import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';
import { useAuthContext } from '../context/useAuthContext';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const { login, loading} = useAuthContext();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
  
    // Clear previous error message on reattempt
    setErrorMessage('');
  
    // Validate form inputs
    if (!email || !password) {
      setErrorMessage('Both email and password are required.');
      return;
    }
  
    try {
      await login(email, password);
      setSuccessMessage('Login successful!');
      navigate('/');
    } catch (error) {
      setErrorMessage(error.message || 'Login failed. Please try again.');
    }
  };
  

  return (
    <Form onSubmit={handleLogin} className="auth-form">
      <Form.Group className="mb-3" controlId="formEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          isInvalid={!!errorMessage && !email}
        />
        <Form.Control.Feedback type="invalid">
          Email is required.
        </Form.Control.Feedback>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formPassword">
        <Form.Label>Password</Form.Label>
        <PasswordToggle password={password} setPassword={setPassword} />
      </Form.Group>

      {loading && <p>Loading...</p>}
      {successMessage && <p className="text-success">{successMessage}</p>}
      {errorMessage && <p className="text-danger">{errorMessage}</p>}

      <Button type="submit" className="w-100 home-button" disabled={loading}>
        Log In
      </Button>
    </Form>
  );
};

export default LoginForm;
