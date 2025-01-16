import React from 'react';
import '../styles/Donate.css';

const Invoice = ({ invoice }) => {
  if (!invoice) return null;

  return (
    <div className="invoice-card p-4 shadow-sm rounded">
      <h3 className="invoice-title">Invoice</h3>
      <div className="invoice-details">
        <p className="invoice-item">
          <strong>Amount:</strong> {invoice.originalAmount} {invoice.currency}
        </p>
        <p className="invoice-item">
          <strong>Final Amount:</strong> {invoice.calculatedTotal} 'USD'
        </p>
      </div>
    </div>
  );
};

export default Invoice;
