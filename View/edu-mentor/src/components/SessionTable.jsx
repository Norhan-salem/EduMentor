import React from 'react';
import { Table, Card, Button } from 'react-bootstrap';

const SessionTable = ({ sessions, onDeleteSession }) => {
  return (
    <Card className="mb-4 shadow-sm auth-form">
      <Card.Body>
        <Card.Title>Upcoming Sessions</Card.Title>
        <Table responsive striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Time</th>
              <th>Duration</th>
              <th>Topic</th>
              <th>Assigned Mentor</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sessions.map((session) => (
              <tr key={session.id}>
                <td>{session.date}</td>
                <td>{session.time}</td>
                <td>{session.duration}</td>
                <td>{session.topic}</td>
                <td>{session.mentor}</td>
                <td>
                  <Button className = 'delete-btn' size="sm" onClick={() => onDeleteSession(session.id)}>
                    Delete
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

export default SessionTable;

