import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { getSessionFeedback, getSessionMentors, getSessionMentees } from '../api/apiClient';
import { Container, Card, Table, Row, Col } from 'react-bootstrap';

const SessionDetailsPage = () => {
  const location = useLocation();
  const session = location.state?.session;
  const [feedback, setFeedback] = useState([]);
  const [mentors, setMentors] = useState([]);
  const [mentees, setMentees] = useState([]);

  useEffect(() => {
    const fetchSessionDetails = async () => {
      try {
        const feedbackData = await getSessionFeedback(session);
        const mentorsData = await getSessionMentors(session);
        const menteesData = await getSessionMentees(session);

        setFeedback(feedbackData);
        setMentors(mentorsData);
        setMentees(menteesData);
      } catch (error) {
        console.error('Error fetching session details:', error);
      }
    };

    fetchSessionDetails();
  }, [session]);

  return (
    <Container className="mt-5">
      <h2 className="text-center mb-4">{session?.name} - Session Details</h2>

      <Row>
        <Col md={12}>
          <Card className="mb-4 auth-form">
            <Card.Body>
              <Card.Title>Session Information</Card.Title>
              <p><strong>Session ID:</strong> {session?.sessionID}</p>
              <p><strong>Date:</strong> {new Date(session?.date).toLocaleDateString()}</p>
              <p><strong>Duration:</strong> {session?.duration}</p>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col md={6}>
          <Card className="mb-4 auth-form">
            <Card.Body>
              <Card.Title>Mentors</Card.Title>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                  </tr>
                </thead>
                <tbody>
                  {mentors.map((mentor) => (
                    <tr key={mentor.userID}>
                      <td>{mentor.firstName} {mentor.lastName}</td>
                      <td>{mentor.email}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6}>
          <Card className="mb-4 auth-form">
            <Card.Body>
              <Card.Title>Mentees</Card.Title>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                  </tr>
                </thead>
                <tbody>
                  {mentees.map((mentee) => (
                    <tr key={mentee.userID}>
                      <td>{mentee.firstName} {mentee.lastName}</td>
                      <td>{mentee.email}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col md={12}>
          <Card className="mb-4 auth-form">
            <Card.Body>
              <Card.Title>Feedback</Card.Title>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Comment</th>
                    <th>Rating</th>
                  </tr>
                </thead>
                <tbody>
                  {feedback.map((item, index) => (
                    <tr key={index}>
                      <td>{item.comment}</td>
                      <td>{item.rating}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default SessionDetailsPage;
