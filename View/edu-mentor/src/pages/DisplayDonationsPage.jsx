import React, { useEffect, useState } from 'react';
import '../styles/Donate.css';
import { Card, Spinner, Alert, Container, Row, Col } from 'react-bootstrap';
import { useAuthContext } from '../context/useAuthContext';
import { getDonations } from '../api/apiClient';


const DonationsPage = () => {
  const { user } = useAuthContext();
  const [donations, setDonations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDonations = async () => {
      try {
        const response = await getDonations(user);
        setDonations(response);
      } catch (err) {
        setError('Failed to fetch donations. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    if (user) fetchDonations();
  }, [user]);

  return (
    <Container className="donations-page auth-form py-4">
      <h2 className="text-center my-4">Your Donations</h2>

      {loading ? (
        <div className="text-center">
          <Spinner animation="border"/>
        </div>
      ) : error ? (
        <Alert variant="danger" className="text-center">
          {error}
        </Alert>
      ) : donations.length === 0 ? (
        <Alert variant="info" className="text-center">
          You haven't made any donations yet.
        </Alert>
      ) : (
        <Row>
          {donations.map((donation) => (
            <Col md={6} lg={4} key={donation.donationID} className="mb-4">
              <Card className="shadow-sm">
                <Card.Body>
                  <Card.Title className="donation-card-title">
                    Donation #{donation.donationID}
                  </Card.Title>
                  <Card.Text>
                    <strong>Amount:</strong> ${donation.amount}
                  </Card.Text>
                  <Card.Text>
                    <strong>Payment Method:</strong> {donation.paymentType}
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
  );
};

export default DonationsPage;
