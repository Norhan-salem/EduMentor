import React, { useState } from 'react';
import { Container, Row, Col, Button, Form } from 'react-bootstrap';
import SessionTable from '../components/SessionTable';
import { useNavigate } from 'react-router-dom';

const AdminDashboardPage = () => {
  const [sessions, setSessions] = useState([
    { id: 1, date: '2024-11-15', time: '10:00 AM - 12:00 PM', duration: '2 hours', topic: 'Math Tutoring', mentor: 'John Doe' },
    { id: 2, date: '2024-11-18', time: '2:00 PM - 4:00 PM', duration: '2 hours', topic: 'Science Tutoring', mentor: 'Jane Smith' },
  ]);

  const [searchQuery, setSearchQuery] = useState('');
  
  const navigate = useNavigate();

  const handleDeleteSession = (sessionId) => {
    setSessions(sessions.filter(session => session.id !== sessionId));
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    // Filter the sessions based on the search query
    if (searchQuery) {
      const filteredSessions = sessions.filter(session => 
        session.topic.toLowerCase().includes(searchQuery.toLowerCase()) ||
        session.mentor.toLowerCase().includes(searchQuery.toLowerCase())
      );
      setSessions(filteredSessions);
    } else {
      // Reset sessions if the search query is cleared
      setSessions([
        { id: 1, date: '2024-11-15', time: '10:00 AM - 12:00 PM', duration: '2 hours', topic: 'Math Tutoring', mentor: 'John Doe' },
        { id: 2, date: '2024-11-18', time: '2:00 PM - 4:00 PM', duration: '2 hours', topic: 'Science Tutoring', mentor: 'Jane Smith' },
      ]);
    }
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Admin Dashboard</h1>

      <Row className="mb-2">
        <Col className="d-flex justify-content-end">
          <Button className='home-button' onClick={() => navigate('/create-session')}>
            Create New Session
          </Button>
        </Col>
      </Row>

      {/* Search Bar */}
      <Container className="py-3">
        <Form onSubmit={handleSearchSubmit} className="home-form">
          <Form.Control
            type="text"
            placeholder="Search for a session..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="home-form-input"
          />
          <Button type="submit" className="home-button">
            <i className="bi bi-search"></i>
          </Button>
        </Form>
      </Container>

      <Row>
        <Col>
          <SessionTable sessions={sessions} onDeleteSession={handleDeleteSession} />
        </Col>
      </Row>
    </Container>
  );
};

export default AdminDashboardPage;

