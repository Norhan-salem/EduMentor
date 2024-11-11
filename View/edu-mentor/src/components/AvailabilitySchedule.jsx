import React from 'react';
import { Card, Button, Table, Form } from 'react-bootstrap';

const AvailabilitySchedule = ({ availability, handleAddAvailability, handleScheduleChange }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Availability Schedule</Card.Title>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {availability.map((entry, index) => (
              <tr key={index}>
                <td>
                  <Form.Control
                    type="date"
                    name="date"
                    value={entry.date}
                    onChange={(e) => handleScheduleChange(e, index)}
                  />
                </td>
                <td>
                  <Form.Control
                    type="text"
                    name="time"
                    value={entry.time}
                    onChange={(e) => handleScheduleChange(e, index)}
                    placeholder="e.g., 9:00 AM - 11:00 AM"
                  />
                </td>
                <td>
                  <Form.Control
                    as="select"
                    name="status"
                    value={entry.status}
                    onChange={(e) => handleScheduleChange(e, index)}
                  >
                    <option>Free</option>
                    <option>Booked</option>
                  </Form.Control>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
        <Button onClick={handleAddAvailability} variant="outline-primary" className="mb-3">
          Add Availability
        </Button>
      </Card.Body>
    </Card>
  );
};

export default AvailabilitySchedule;
