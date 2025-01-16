import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import Invoice from './invoice';
import { makeDonation, getInvoiceDetails } from '../services/api';

const currencies = ['EGP', 'USD', 'EUR', 'GBP', 'CAD'];
const predefinedAmounts = [10, 25, 50, 100];
const paymentOptions = ['VISA', 'COURIER'];

const DonateForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState(currencies[0]);
  const [customAmount, setCustomAmount] = useState('');
  const [isCustomAmount, setIsCustomAmount] = useState(false);
  const [paymentOption, setPaymentOption] = useState(paymentOptions[0]);
  const [cardNumber, setCardNumber] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [cvv, setCvv] = useState('');
  const [errors, setErrors] = useState({});
  const [invoice, setInvoice] = useState(null);

  // Dummy function to simulate fetching invoice details
  const showInvoice = (donationId) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          donationId,
          amount,
          currency,
          date: new Date().toLocaleString(),
        });
      }, 1000);
    });
  };

  const handleDonate = async (e) => {
    e.preventDefault();
    const validationErrors = {};

    if (!firstName) validationErrors.firstName = 'First name is required';
    if (!lastName) validationErrors.lastName = 'Last name is required';
    if (!email) validationErrors.email = 'Email is required';
    if (!amount) validationErrors.amount = 'Amount is required';
    if (!currency) validationErrors.currency = 'Currency is required';
    if ((paymentOption === 'Visa' || paymentOption === 'MasterCard') && !cardNumber)
      validationErrors.cardNumber = 'Card number is required';
    if ((paymentOption === 'Visa' || paymentOption === 'MasterCard') && !expiryDate)
      validationErrors.expiryDate = 'Expiry date is required';
    if ((paymentOption === 'Visa' || paymentOption === 'MasterCard') && !cvv)
      validationErrors.cvv = 'CVV is required';

    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      return;
    }

    const donationPayload = {
          onlineDonation: {
            // faen el id hena bta3 el donation
            paymentType: paymentOption,
            amount: parseFloat(amount),
          },
          onlineDonor: {
            firstName,
            lastName,
            email,
          },
          paymentType: paymentOption,
          amount: parseFloat(amount),
        };

    setErrors({});

    try {
      const donationResponse = await makeDonation(donationPayload);

      if (donationResponse.success) {
        const invoiceDetails = await getInvoiceDetails({
          // ana 3aiza el id hena bta3 el donation
          donation: { donationID: donationResponse.donationId, paymentType: paymentOption, amount },
          targetCurrency: currency,
          includeTax: true,
        });
        setInvoice(invoiceDetails);
      } else {
        alert('Donation failed');
      }
    } catch (error) {
      console.error('Donation process failed:', error);
      alert('An error occurred while processing the donation.');
    }
  };
  const handleAmountChange = (e) => {
    const value = e.target.value;
    if (value === 'custom') {
      setIsCustomAmount(true);
      setAmount('');
    } else {
      setIsCustomAmount(false);
      setAmount(value);
    }
  };

  return (
    <Form onSubmit={handleDonate} className="donate-form">
      {/* Form fields */}
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
        <Form.Label>Email</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          isInvalid={!!errors.email}
        />
        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicCurrency">
        <Form.Label>Currency</Form.Label>
        <Form.Select
          value={currency}
          onChange={(e) => setCurrency(e.target.value)}
          required
        >
          {currencies.map((cur) => (
            <option key={cur} value={cur}>
              {cur}
            </option>
          ))}
        </Form.Select>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicAmount">
        <Form.Label>Donation Amount</Form.Label>
        <Form.Select value={amount} onChange={handleAmountChange} required>
          <option value="">Select amount</option>
          {predefinedAmounts.map((amountOption) => (
            <option key={amountOption} value={amountOption}>
              ${amountOption}
            </option>
          ))}
          <option value="custom">Other</option>
        </Form.Select>
        {isCustomAmount && (
          <Form.Control
            type="number"
            placeholder="Enter custom amount"
            value={customAmount}
            onChange={(e) => {
              setCustomAmount(e.target.value);
              setAmount(e.target.value);
            }}
          />
        )}
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPaymentOption">
        <Form.Label>Payment Option</Form.Label>
        <Form.Select
          value={paymentOption}
          onChange={(e) => setPaymentOption(e.target.value)}
          required
        >
          {paymentOptions.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </Form.Select>
      </Form.Group>

      {(paymentOption === 'Card') && (
        <>
          <Form.Group className="mb-3" controlId="formBasicCardNumber">
            <Form.Label>Card Number</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter card number"
              value={cardNumber}
              onChange={(e) => setCardNumber(e.target.value)}
              isInvalid={!!errors.cardNumber}
            />
            <Form.Control.Feedback type="invalid">{errors.cardNumber}</Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicExpiryDate">
            <Form.Label>Expiry Date (MM/YY)</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter expiry date"
              value={expiryDate}
              onChange={(e) => setExpiryDate(e.target.value)}
              isInvalid={!!errors.expiryDate}
            />
            <Form.Control.Feedback type="invalid">{errors.expiryDate}</Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicCvv">
            <Form.Label>CVV</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter CVV"
              value={cvv}
              onChange={(e) => setCvv(e.target.value)}
              isInvalid={!!errors.cvv}
            />
            <Form.Control.Feedback type="invalid">{errors.cvv}</Form.Control.Feedback>
          </Form.Group>
        </>
      )}

      {errors.amount && <div className="text-danger">{errors.amount}</div>}
      {errors.currency && <div className="text-danger">{errors.currency}</div>}

      <Button type="submit" className="w-100 home-button">
        Donate
      </Button>

      {/* Display invoice after donation */}
      <Invoice invoice={invoice} />
    </Form>
  );
};

export default DonateForm;

