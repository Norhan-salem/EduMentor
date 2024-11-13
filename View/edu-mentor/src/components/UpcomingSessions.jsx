import React from 'react';
import { Card, Table, Button } from 'react-bootstrap';

const UpcomingSessions = ({ sessions, handleRegister }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Upcoming Sessions</Card.Title>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Duration</th>
              <th>Topic</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {sessions.map((session, index) => (
              <tr key={index}>
                <td>{session.date}</td>
                <td>{session.duration}</td>
                <td>{session.topic}</td>
                <td>
                  <Button
                    onClick={() => handleRegister(session)}
                    variant="primary"
                  >
                    Register
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

export default UpcomingSessions;

