import React from 'react';
import { Card, Table } from 'react-bootstrap';

const RegisteredSessions = ({ sessions }) => {
  return (
    <Card className="mb-4 auth-form">
      <Card.Body>
        <Card.Title>Registered Sessions</Card.Title>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Name</th>
              <th>Date</th>
              <th>Duration</th>
            </tr>
          </thead>
          <tbody>
            {sessions.map((session, index) => (
              <tr key={index}>
                <td>{session.name}</td>
                <td>{session.date}</td>
                <td>{session.duration}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Card.Body>
    </Card>
  );
};

export default RegisteredSessions;
