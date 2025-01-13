import React, { useState } from 'react';
import { Form, Button, Row, Col } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';
import { useAuthContext } from '../context/useAuthContext';
import { validateForm } from '../utils/validation'; 

const roles = ['Mentor', 'Mentee'];

const SignUpForm = () => {
  const { register, loading, errorMessage, successMessage } = useAuthContext();
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('');
  const [agreedToTerms, setAgreedToTerms] = useState(false);
  const [errors, setErrors] = useState({});

  const handleSignUp = async (e) => {
    e.preventDefault();

    // Clear previous errors
    setErrors({});
    
    // Validate form data
    const validationErrors = validateForm(firstName, lastName, email, password, confirmPassword, role, agreedToTerms);
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    try {
      const userType = role === 'Mentor' ? 2 : 3;
      await register(email, password, userType);
    } catch (error) {
      console.error("Signup failed:", error);
    }
  };

  return (
    <Form onSubmit={handleSignUp} className="auth-form">
      <Row>
        {/* Name Section */}
        <Col md={6}>
          <Form.Group className="mb-3" controlId="formBasicFirstName">
            <Form.Label>First Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter first name"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              isInvalid={!!errors.firstName}
            />
            <Form.Control.Feedback type="invalid">{errors.firstName}</Form.Control.Feedback>
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3" controlId="formBasicLastName">
            <Form.Label>Last Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter last name"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              isInvalid={!!errors.lastName}
            />
            <Form.Control.Feedback type="invalid">{errors.lastName}</Form.Control.Feedback>
          </Form.Group>
        </Col>
      </Row>

      {/* Email Section */}
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email Address</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          isInvalid={!!errors.email}
        />
        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
      </Form.Group>

      <Row>
        {/* Password Section */}
        <Col md={6}>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <PasswordToggle password={password} setPassword={setPassword} />
            <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
            <Form.Label>Confirm Password</Form.Label>
            <PasswordToggle password={confirmPassword} setPassword={setConfirmPassword} />
            <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
          </Form.Group>
        </Col>
      </Row>

      {/* Role Section */}
      <Form.Group className="mb-3" controlId="formBasicRole">
        <Form.Label>Role</Form.Label>
        <Form.Select value={role} onChange={(e) => setRole(e.target.value)} required isInvalid={!!errors.role}>
          <option value="">Select role</option>
          {roles.map((roleOption) => (
            <option key={roleOption} value={roleOption}>
              {roleOption}
            </option>
          ))}
        </Form.Select>
        <Form.Control.Feedback type="invalid">{errors.role}</Form.Control.Feedback>
      </Form.Group>

      {/* Terms Section */}
      <Form.Group className="mb-3" controlId="formBasicTerms">
        <Form.Check
          type="checkbox"
          label="I agree to the terms and conditions"
          checked={agreedToTerms}
          onChange={(e) => setAgreedToTerms(e.target.checked)}
          isInvalid={!!errors.agreedToTerms}
        />
        <Form.Control.Feedback type="invalid">{errors.agreedToTerms}</Form.Control.Feedback>
      </Form.Group>

      {/* Feedback Messages */}
      {loading && <p>Loading...</p>}
      {successMessage && <p className="text-success">{successMessage}</p>}
      {errorMessage && <p className="text-danger">{errorMessage}</p>}

      {/* Submit Button */}
      <Button type="submit" className="w-100 home-button" disabled={loading}>
        Sign Up
      </Button>
    </Form>
  );
};

export default SignUpForm;

