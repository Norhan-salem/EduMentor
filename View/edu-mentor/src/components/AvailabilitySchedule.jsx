import React from 'react';
import { Card, Button, Table, Form } from 'react-bootstrap';

const AvailabilitySchedule = ({ availability, handleAddAvailability, handleScheduleChange, handleDeleteAvailability }) => {
  return (
    <Card className="mb-4 shadow-lg auth-form">
      <Card.Body>
        <Card.Title className=" mb-4">Availability Schedule</Card.Title>
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
              <th>Delete</th>
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
                    className="schedule-input"
                  />
                </td>
                <td>
                  <Form.Control
                    type="text"
                    name="time"
                    value={entry.time}
                    onChange={(e) => handleScheduleChange(e, index)}
                    placeholder="e.g., 9:00 AM - 11:00 AM"
                    className="schedule-input"
                  />
                </td>
                <td>
                  <Form.Control
                    as="select"
                    name="status"
                    value={entry.status}
                    onChange={(e) => handleScheduleChange(e, index)}
                    className="schedule-input"
                  >
                    <option>Free</option>
                    <option>Booked</option>
                  </Form.Control>
                </td>
                <td>
                  <Button
                    onClick={() => handleDeleteAvailability(index)}
                    className="delete-btn"
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
        <div>
          <Button onClick={handleAddAvailability} className="home-button">
            Add Availability
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
};

export default AvailabilitySchedule;

