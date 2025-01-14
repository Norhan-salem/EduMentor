import React, { useState } from 'react';
import { Container, Button, Row, Col, Card, Spinner } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';
import { FaSortAlphaDown, FaSortNumericDown } from 'react-icons/fa';
import { sortMentors } from '../services/api';
import AOS from 'aos';
import 'aos/dist/aos.css';

AOS.init();

const ResultsPage = () => {
  const location = useLocation();
  const initialUsers = location.state?.users || [];
  const [users, setUsers] = useState(initialUsers);
  const [sortOption, setSortOption] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSort = async (option) => {
    setSortOption(option);
    setLoading(true);
    try {
      const sortedUsers = await sortMentors(users, option);
      setUsers(sortedUsers);
    } catch (error) {
      console.error('Error sorting users:', error);
      alert('Failed to sort users. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="py-5">
      <h1 className="mb-4 text-center">Search Results</h1>
      <Row className="mb-4">
        <Col className="d-flex justify-content-end">
          <Button
            onClick={() => handleSort('alphabetical')}
            className="me-2 home-button"
            disabled={loading}
          >
            <FaSortAlphaDown /> Sort by Name
          </Button>
          <Button
            onClick={() => handleSort('hours')}
            className="home-button"
            disabled={loading}
          >
            <FaSortNumericDown /> Sort by Hours
          </Button>
        </Col>
      </Row>

      {loading && (
        <div className="text-center mb-3">
          <Spinner animation="border" /> Sorting...
        </div>
      )}

      <Row data-aos="fade-up">
        {users.map((user, index) => (
          <Col key={index} md={4} className="mb-4">
            <Card className="shadow-sm hover-shadow-lg border-0">
              <Card.Body className="p-4 auth-form">
                <div className="d-flex align-items-center mb-3">
                  <div>
                    <Card.Title>{`${user.firstName} ${user.lastName}`}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">{user.email}</Card.Subtitle>
                  </div>
                </div>
                <Card.Text className="mb-2">Mentoring Hours: {user.totalHours}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {!loading && users.length === 0 && (
        <div className="text-center">
          <h4>No mentors found matching your criteria.</h4>
        </div>
      )}
    </Container>
  );
};

export default ResultsPage;
