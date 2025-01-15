import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';
import { assignMentor, getAvailableMentors } from '../services/api';

const AssignMentorPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const session = location.state?.session;
  const [mentors, setMentors] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMentors = async () => {
      try {
        if (session) {
          const data = await getAvailableMentors(session);
          setMentors(data);
        }
      } catch (error) {
        console.error('Error fetching mentors:', error);
      } finally {
        setLoading(false);
      }
    };

    if (session) {
      fetchMentors();
    } else {
      setLoading(false);
    }
  }, [session]);

  const handleAssign = async (mentorId) => {
    try {
        await assignMentor(session, mentorId);
        alert('Mentor successfully assigned to the session!');
        navigate('/admin-dashboard');
    } catch (error) {
      console.error('Error assigning mentor:', error);
    }
  };

  if (loading) {
    return <div>Loading...</div>; // Show loading until mentors are fetched or session is confirmed
  }

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">
        Assign Mentor to {session ? session.name : 'Session'}
      </h1>
      {mentors.length === 0 ? (
        <div className="text-center">
          <h3>No mentors are available for this session.</h3>
        </div>
      ) : (
        <Row>
          {mentors.map((mentor) => (
            <Col key={mentor.userID} md={4} className="mb-4">
              <Card className="shadow-sm auth-form">
                <Card.Body>
                  <Card.Title>{`${mentor.firstName} ${mentor.lastName}`}</Card.Title>
                  <Button
                    className="home-button"
                    onClick={() => handleAssign(mentor.userID)}
                  >
                    Assign Mentor
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
  );
};

export default AssignMentorPage;
