import React from 'react';
import DonateForm from '../components/DonateForm';
import '../styles/Donate.css';
import { Button } from 'react-bootstrap';

const DonatePage = () => {
  return (
    <div className="container mt-5">
      <h2 className="text-center">Make a Difference</h2>
      <p className="text-center">Your support helps us provide better educational support to students around the world.</p>
      <p className="text-center">Donations go towards our organization.</p>
      <DonateForm />
    </div>
  );
};

export default DonatePage;


