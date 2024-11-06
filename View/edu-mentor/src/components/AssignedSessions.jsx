import React from 'react';
import { Card, Table } from 'react-bootstrap';

const AssignedSessions = ({ sessions }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Assigned Sessions</Card.Title>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Duration</th>
              <th>Topic</th>
            </tr>
          </thead>
          <tbody>
            {sessions.map((session, index) => (
              <tr key={index}>
                <td>{session.date}</td>
                <td>{session.duration}</td>
                <td>{session.topic}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Card.Body>
    </Card>
  );
};

export default AssignedSessions;
