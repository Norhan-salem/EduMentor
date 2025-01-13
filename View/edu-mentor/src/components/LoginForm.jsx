import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';
import axios from 'axios';
import config from "../config";

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    // Clear errors
    setErrorMessage('');
    setSuccessMessage('');
    setLoading(true);

    // Validate form inputs
    if (!email || !password) {
      setErrorMessage('Both email and password are required.');
      setLoading(false);
      return;
    }

    // Request body for login
    const requestBody = {
      email,
      password,
    };

    // Send login request to backend
    try {
      const response = await axios.post(`${config.backendUrl}/api/auth/login`, requestBody);
      console.log('Login response:', response.data);
      setSuccessMessage('Login successful!');
      // localStorage.setItem('authToken', response.data.token);
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Login failed. Please try again.');
      console.error('Error during login:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Form onSubmit={handleLogin} className='auth-form'>
      <Form.Group className="mb-3 auth-form" controlId="formBasicEmail">
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
      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <PasswordToggle password={password} setPassword={setPassword} />
        <Form.Control.Feedback type="invalid">
          Password is required.
        </Form.Control.Feedback>
      </Form.Group>

      {/* Loading, success, and error messages */}
      {loading && <p>Loading...</p>}
      {errorMessage && <p className="text-danger">{errorMessage}</p>}
      {successMessage && <p className="text-success">{successMessage}</p>}

      <Button type="submit" className="w-100 home-button" disabled={loading}>
        Log In
      </Button>
    </Form>
  );
};

export default LoginForm;
