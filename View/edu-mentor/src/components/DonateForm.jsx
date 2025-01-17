import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import {makeDonation} from "../api/apiClient";
import { useAuthContext } from '../context/useAuthContext';

const stripePromise = loadStripe("pk_test_51QgoFqFsbd6P1vCs6AO0oCP0scQwtbfjS1KpOG0Iw9sIknnpayZJkOAeNcgsKnHbNpg5r18k8DjbjsEm5EfAfifq00BnwJMoM8");

const DonateForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState('USD');
  const [errors, setErrors] = useState({});
  const stripe = useStripe();
  const elements = useElements();
  const { user } = useAuthContext();

  const handleDonate = async (e) => {
    e.preventDefault();

    const validationErrors = {};
    if (!firstName) validationErrors.firstName = 'First name is required';
    if (!lastName) validationErrors.lastName = 'Last name is required';
    if (!email) validationErrors.email = 'Email is required';
    if (!amount) validationErrors.amount = 'Amount is required';

    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      return;
    }

    const donationPayload = {
      amount : parseInt(amount),
      paymentType: "Card", // to be replaced with payment option once full integration is done
      firstName: firstName,
      lastName: lastName,
      email: email,
      donorId: user.userID // to be replaced with the actual user id
    }
    setErrors({});

    const donationResponse =  await makeDonation(donationPayload);

    const { error, paymentIntent } = await stripe.confirmCardPayment(
      donationResponse['clientSecret'], // Replace with actual client secret
        {
          payment_method: {
            card: elements.getElement(CardElement),
            billing_details: {
              name: `${firstName} ${lastName}`,
              email,
            },
          },
        }
    );

    if (error) {
      alert(`Payment failed: ${error.message}`);
    } else if (paymentIntent.status === 'succeeded') {
      alert('Donation successful!');
    }
  };

  return (
      <Form onSubmit={handleDonate} className="donate-form">
        <Form.Group className="mb-3">
          <Form.Label>First Name</Form.Label>
          <Form.Control
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              isInvalid={!!errors.firstName}
          />
          <Form.Control.Feedback type="invalid">{errors.firstName}</Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Last Name</Form.Label>
          <Form.Control
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              isInvalid={!!errors.lastName}
          />
          <Form.Control.Feedback type="invalid">{errors.lastName}</Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              isInvalid={!!errors.email}
          />
          <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Amount</Form.Label>
          <Form.Control
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              isInvalid={!!errors.amount}
          />
          <Form.Control.Feedback type="invalid">{errors.amount}</Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Card Details</Form.Label>
          <CardElement />
        </Form.Group>

        <Button type="submit" className="w-100">
          Donate
        </Button>
      </Form>
  );
};

const DonatePage = () => (
    <Elements stripe={stripePromise}>
      <DonateForm />
    </Elements>
);

export default DonatePage;
