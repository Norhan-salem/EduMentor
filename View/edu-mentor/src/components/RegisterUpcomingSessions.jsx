import React from 'react';
import { Card, Table, Button } from 'react-bootstrap';

const RegisterUpcomingSessions = ({ sessions, handleRegister, registeredSessions }) => {
  const isRegistered = (session) => {
    return registeredSessions.some((registeredSession) => registeredSession.sessionID === session.sessionID);
  };

  return (
    <Card className="mb-4 auth-form">
      <Card.Body>
        <Card.Title>Upcoming Sessions</Card.Title>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Name</th>
              <th>Date</th>
              <th>Duration</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {sessions.map((session, index) => (
              <tr key={index}>
                <td>{session.name}</td>
                <td>{new Date(session.date).toLocaleDateString()}</td>
                <td>{session.duration}</td>
                <td>
                  <Button
                    onClick={() => handleRegister(session)}
                    className='home-button'
                    disabled={isRegistered(session)}
                  >
                    {isRegistered(session) ? 'Registered' : 'Register'}
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Card.Body>
    </Card>
  );
};

export default RegisterUpcomingSessions;

