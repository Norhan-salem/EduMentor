import React from 'react';
import { Card, Form } from 'react-bootstrap';

const AttendedHours = ({ attendedHours }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Attended Hours</Card.Title>
        <Form>
          <Form.Group>
            <Form.Label>Total Attended Hours</Form.Label>
            <Form.Control type="text" value={attendedHours} readOnly />
          </Form.Group>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default AttendedHours;
