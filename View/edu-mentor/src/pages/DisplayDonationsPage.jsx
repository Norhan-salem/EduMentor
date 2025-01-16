import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card, ListGroup } from 'react-bootstrap';
import { useAuthContext } from '../context/useAuthContext';
import '../styles/Donate.css';

const DonationsPage = () => {
  const { user } = useAuthContext();
  const [donations, setDonations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDonations = async () => {
      // try {
      //   const response = await axios.get('/api/getDonations', {
      //     params: { donor: { email: user.email } },
      //   });
      //   setDonations(response.data);
      // } catch (err) {
      //   setError('Failed to fetch donations');
      // } finally {
      //   setLoading(false);
      // }
    };

    if (user) fetchDonations();
  }, [user]);

  return (
    <div className="donations-page">
      <h2 className="text-center mb-4">Your Donations</h2>
      {loading ? (
        <div className="loading">Loading...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : (
        <div className="donations-list">
          <ListGroup>
            {donations.map((donation) => (
              <ListGroup.Item key={donation.donationID} className="donation-card">
                <Card className="donation-card-body">
                  <Card.Body>
                    <Card.Title>Donation #{donation.donationID}</Card.Title>
                    <Card.Text>Amount: ${donation.amount}</Card.Text>
                    <Card.Text>Payment Method: {donation.paymentType}</Card.Text>
                  </Card.Body>
                </Card>
              </ListGroup.Item>
            ))}
          </ListGroup>
        </div>
      )}
    </div>
  );
};

export default DonationsPage;
