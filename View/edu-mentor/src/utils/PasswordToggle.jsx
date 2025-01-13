import React, { useState } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
import { BsEye, BsEyeSlash } from 'react-icons/bs';

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
      <Button className = 'home-button' onClick={togglePasswordVisibility}>
        {showPassword ? <BsEyeSlash /> : <BsEye />}
      </Button>
    </InputGroup>
  );
};

export default PasswordToggle;

