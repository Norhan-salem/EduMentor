import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Form } from 'react-bootstrap';
import SessionTable from '../components/SessionTable';
import { useNavigate } from 'react-router-dom';
import { cancelSession, searchSessions, getSessions } from '../services/api';

const AdminDashboardPage = () => {
  const [sessions, setSessions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSessions = async () => {
      try {
        setLoading(true);
        const data = await getSessions();
        setSessions(data || []); // Add fallback to empty array
      } catch (error) {
        console.error('Error fetching sessions:', error);
        setSessions([]); // Set empty array on error
      } finally {
        setLoading(false);
      }
    };

    fetchSessions();
  }, []);

  const handleDeleteSession = async (session) => {
    try {
      await cancelSession(session);
      setSessions(sessions.filter((s) => s.sessionID !== session.sessionID));
    } catch (error) {
      console.error('Error deleting session:', error);
    }
  };

  const handleSearchSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const data = await searchSessions(searchQuery);
      setSessions(data || []);
    } catch (error) {
      console.error('Error searching sessions:', error);
      setSessions([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAssignMentor = (session) => {
    navigate('/assign-mentor', { state: { session } });
  };

  return (
      <Container className="mt-5">
        <h1 className="text-center mb-4">Admin Dashboard</h1>

        <Row className="mb-2">
          <Col className="d-flex justify-content-end">
            <Button className="home-button" onClick={() => navigate('/create-session')}>
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
            {loading ? (
                <div className="text-center">Loading...</div>
            ) : (
                <SessionTable
                    sessions={sessions}
                    onDeleteSession={handleDeleteSession}
                    onAssignMentor={handleAssignMentor}
                />
            )}
          </Col>
        </Row>
      </Container>
  );
};

export default AdminDashboardPage;

