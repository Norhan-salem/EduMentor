import React, { useState } from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import SessionTable from '../components/SessionTable';
import { useNavigate } from 'react-router-dom';

const AdminDashboardPage = () => {
    const [sessions, setSessions] = useState([
        { id: 1, date: '2024-11-15', time: '10:00 AM - 12:00 PM', duration: '2 hours', topic: 'Math Tutoring', mentor: 'John Doe' },
        { id: 2, date: '2024-11-18', time: '2:00 PM - 4:00 PM', duration: '2 hours', topic: 'Science Tutoring', mentor: 'Jane Smith' },
      ]);
      
      const navigate = useNavigate();
    
      const handleDeleteSession = (sessionId) => {
        setSessions(sessions.filter(session => session.id !== sessionId));
      };
    
      return (
        <Container className="mt-5">
          <h1 className="text-center mb-4">Admin Dashboard</h1>
    
          <Row className="mb-4">
            <Col className="d-flex justify-content-end">
              <Button variant="primary" onClick={() => navigate('/create-session')}>
                Create New Session
              </Button>
            </Col>
          </Row>
    
          <Row>
            <Col>
              <SessionTable sessions={sessions} onDeleteSession={handleDeleteSession} />
            </Col>
          </Row>
        </Container>
      );
    };

export default AdminDashboardPage;
