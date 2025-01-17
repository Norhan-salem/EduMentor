import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import { makeDonation } from "../api/apiClient";
import { useAuthContext } from '../context/useAuthContext';

const stripePromise = loadStripe("pk_test_51QgoFqFsbd6P1vCs6AO0oCP0scQwtbfjS1KpOG0Iw9sIknnpayZJkOAeNcgsKnHbNpg5r18k8DjbjsEm5EfAfifq00BnwJMoM8");

const DonateForm = () => {
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState('USD');
  const [paymentOption, setPaymentOption] = useState('Card');
  const [errors, setErrors] = useState({});
  const stripe = useStripe();
  const elements = useElements();
  const { user } = useAuthContext();

  const handleDonate = async (e) => {
    e.preventDefault();

    const validationErrors = {};
    if (!amount) validationErrors.amount = 'Amount is required';
    if (paymentOption === 'Card' && (!elements.getElement(CardElement))) {
      validationErrors.card = 'Card details are required';
    }

    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      return;
    }

    const donationPayload = {
      amount: parseInt(amount),
      paymentType: paymentOption,
      donorId: user.userID,
      currency: paymentOption === 'Card' ? currency : undefined,
    };

    setErrors({});

    if (paymentOption === 'Card') {
      try {
        const donationResponse = await makeDonation(donationPayload);
        const { error, paymentIntent } = await stripe.confirmCardPayment(
          donationResponse['clientSecret'],
          {
            payment_method: {
              card: elements.getElement(CardElement),
            },
          }
        );

        if (error) {
          alert(`Payment failed: ${error.message}`);
        } else if (paymentIntent.status === 'succeeded') {
          alert('Donation successful!');
        }
      } catch (err) {
        alert('Error processing donation. Please try again.');
      }
    } else {
      alert('Donation submitted for Courier payment');
    }
  };

  return (
    <Form onSubmit={handleDonate} className="donate-form">
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
        <Form.Label>Payment Option</Form.Label>
        <Form.Select
          value={paymentOption}
          onChange={(e) => setPaymentOption(e.target.value)}
        >
          <option value="Card">Card</option>
          <option value="Courier">Courier</option>
        </Form.Select>
      </Form.Group>

      {paymentOption === 'Card' && (
        <>
          <Form.Group className="mb-3">
            <Form.Label>Currency</Form.Label>
            <Form.Select
              value={currency}
              onChange={(e) => setCurrency(e.target.value)}
            >
              <option value="USD">USD</option>
              <option value="EGP">EGP</option>
              <option value="EUR">EUR</option>
              <option value="GBP">GBP</option>
              <option value="CAD">CAD</option>
            </Form.Select>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Card Details</Form.Label>
            <CardElement />
            {errors.card && (
              <div className="invalid-feedback d-block">{errors.card}</div>
            )}
          </Form.Group>
        </>
      )}

      <Button type="submit" className="w-100 home-button">
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