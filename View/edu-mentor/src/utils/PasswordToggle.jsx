import React, { useState } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';

const PasswordToggle = ({ password, setPassword }) => {
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <InputGroup className="mb-3">
      <Form.Control
        type={showPassword ? "text" : "password"}
        placeholder="Enter password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <Button variant="outline-secondary" onClick={togglePasswordVisibility}>
        {showPassword ? 'Hide' : 'Show'}
      </Button>
    </InputGroup>
  );
};

export default PasswordToggle;
