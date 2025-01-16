import React from 'react';
import { Table, Card, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SessionTable = ({ sessions = [], onDeleteSession, onAssignMentor }) => {
  const navigate = useNavigate();

  const handleViewDetails = (session) => {
    navigate(`/session-details/${session.sessionID}`, { state: { session } });
  };

  return (
      <Card className="mb-4 shadow-sm auth-form">
        <Card.Body>
          <Card.Title>Sessions</Card.Title>
          {sessions.length === 0 ? (
              <p className="text-center">No sessions found</p>
          ) : (
              <Table responsive striped bordered hover>
                <thead>
                <tr>
                  <th>Name</th>
                  <th>Date</th>
                  <th>Duration</th>
                  <th>State</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {sessions.map((session) => (
                    <tr key={session.sessionID}>
                      <td>{session.name}</td>
                      <td>{new Date(session.date).toLocaleDateString()}</td>
                      <td>{session.duration} hours</td>
                      <td>{session.state}</td>
                      <td>
                        <Button
                            size="sm"
                            className="me-2 home-button"
                            onClick={() => onAssignMentor(session)}
                        >
                          Assign Mentor
                        </Button>
                        <Button
                            size="sm"
                            className="me-2 home-button"
                            onClick={() => handleViewDetails(session)}
                        >
                          View Details
                        </Button>
                        <Button
                            size="sm"
                            className="me-2 delete-btn"
                            onClick={() => onDeleteSession(session)}
                        >
                          Delete
                        </Button>
                      </td>
                    </tr>
                ))}
                </tbody>
              </Table>
          )}
        </Card.Body>
      </Card>
  );
};

export default SessionTable;

