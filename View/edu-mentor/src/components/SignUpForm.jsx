import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';
import { validateForm } from '../utils/validation';
import axios from 'axios';

const roles = ['Mentor', 'Mentee'];

const SignUpForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('');
  const [agreedToTerms, setAgreedToTerms] = useState(false);
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Handle form submission
  const handleSignUp = async (e) => {
    console.log('sebeeeni fe 7ali b2a')
    e.preventDefault();

    // commented 34an zhe2t bs
    // Validate form fields
    /* const validationErrors = validateForm(firstName, lastName, email, password, confirmPassword, role, agreedToTerms);
    if (Object.values(validationErrors).some((error) => error)) {
      setErrors(validationErrors);
      return;
    } */

    // Clear previous errors
    setErrors({});
    setLoading(true);
    setErrorMessage('');
    setSuccessMessage('');

    // Request body structure for signup
    const requestBody = {
      firstName,
      lastName,
      credentials: {
        email,
        password,
      },
      role: role === 'Mentor' ? 1 : 2,
    };

    // *** Axios request to the backend ***
    try {
      const response = await axios.post('http://localhost:8080/api/auth/signup', requestBody);
      setSuccessMessage('Signup successful!');
      console.log('Response:', response.data);
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Signup failed. Please try again.');
      console.error('Error during signup:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Form onSubmit={handleSignUp}>
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
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          isInvalid={!!errors.email}
        />
        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <PasswordToggle password={password} setPassword={setPassword} />
        <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
        <Form.Label>Confirm Password</Form.Label>
        <PasswordToggle password={confirmPassword} setPassword={setConfirmPassword} />
        <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
      </Form.Group>
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
      <Form.Group className="mb-3" controlId="formBasicTerms">
      <Form.Check
        type="checkbox"
        label="I agree to the terms and conditions"
        checked={agreedToTerms}
        onChange={(e) => setAgreedToTerms(e.target.checked)}
        isInvalid={!!errors.agreedToTerms}
      />
      <Form.Control.Feedback type="invalid">
        {errors.agreedToTerms}
      </Form.Control.Feedback>
      </Form.Group>


      {/* Loading, success, and error messages */}
      {loading && <p>Loading...</p>}
      {successMessage && <p className="text-success">{successMessage}</p>}
      {errorMessage && <p className="text-danger">{errorMessage}</p>}

      {/* Submit Button */}
      <Button variant="primary" type="submit" className="w-100" disabled={loading}>
        Sign Up
      </Button>
    </Form>
  );
};

export default SignUpForm;


