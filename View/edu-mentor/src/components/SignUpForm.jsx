import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import PasswordToggle from '../utils/PasswordToggle';
import { validateForm } from '../utils/validation';

const roles = ['Mentor', 'Mentee'];
const interestOptions = [
  'Technology',
  'History',
  'Health',
  'Business',
  'Art',
  'Science',
];

const SignUpForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('');
  const [interests, setInterests] = useState([]);
  const [agreedToTerms, setAgreedToTerms] = useState(false);
  const [errors, setErrors] = useState({});

  const handleSignUp = (e) => {
    e.preventDefault();
    const validationErrors = validateForm(firstName, lastName, email, password, confirmPassword, role, interests, agreedToTerms);

    if (Object.values(validationErrors).some(error => error)) {
      setErrors(validationErrors);
      return;
    }

    setErrors({});
    // Sign-up logic here
    console.log('Signing up with', { firstName, lastName, email, password, role, interests });
  };

  const handleInterestChange = (e) => {
    const selectedValues = Array.from(e.target.selectedOptions, (option) => option.value);
    if (selectedValues.length <= 3) {
      setInterests(selectedValues);
      if (selectedValues.length > 0) {
        setErrors((prev) => ({ ...prev, interests: '' }));
      }
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
      <Form.Group className="mb-3" controlId="formBasicInterests">
        <Form.Label>Interests</Form.Label>
        <Form.Select multiple value={interests} onChange={handleInterestChange} required isInvalid={!!errors.interests}>
          <option value="">Select interests (up to 3)</option>
          {interestOptions.map((interest) => (
            <option key={interest} value={interest}>
              {interest}
            </option>
          ))}
        </Form.Select>
        <Form.Control.Feedback type="invalid">{errors.interests}</Form.Control.Feedback>
        <Form.Text className="text-muted">
          Hold down the Ctrl (Windows) or Command (Mac) button to select multiple options.
        </Form.Text>
      </Form.Group>
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
      <Button variant="primary" type="submit" className="w-100">
        Sign Up
      </Button>
    </Form>
  );
};

export default SignUpForm;
