import React from 'react';
import { Card, Form } from 'react-bootstrap';

const MentoringHours = ({ taughtHours }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Mentoring Hours</Card.Title>
        <Form>
          <Form.Group>
            <Form.Label>Total Mentored Hours</Form.Label>
            <Form.Control type="text" value={taughtHours} readOnly />
          </Form.Group>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default MentoringHours;
