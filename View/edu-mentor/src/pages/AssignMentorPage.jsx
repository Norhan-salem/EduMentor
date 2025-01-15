import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';
import { assignMentor, getAvailableMentors } from '../services/api';

/*
get all mentors that are available on the session's date
*/
const AssignMentorPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const session = location.state?.session;
  const [mentors, setMentors] = useState([]);

  useEffect(() => {
    const fetchMentors = async () => {
      try {
        const data = await getAvailableMentors(session);
        setMentors(data);
      } catch (error) {
        console.error('Error fetching mentors:', error);
      }
    };
    fetchMentors();
  }, []);

  const handleAssign = async (mentorId) => {
    try {
        await assignMentor(session, mentorId);
        alert('Mentor successfully assigned to the session!');
      navigate('/admin-dashboard');
    } catch (error) {
      console.error('Error assigning mentor:', error);
    }
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Assign Mentor to Session</h1>
      <h5 className="mb-4">Session: {session.name}</h5>
      <Row>
        {mentors.map((mentor) => (
          <Col key={mentor.userID} md={4} className="mb-4">
            <Card className="shadow-sm">
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
    </Container>
  );
};

export default AssignMentorPage;
