import React from 'react';
import { Card, Button, Table, Form } from 'react-bootstrap';

const AvailabilitySchedule = ({ availability, handleAddAvailability, handleScheduleChange, handleSaveAvailability, handleDeleteAvailability }) => {
  return (
    <Card className="mb-4 shadow-lg auth-form">
      <Card.Body>
        <Card.Title className="mb-4">Availability Schedule</Card.Title>
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Date</th>
              <th>Time</th>
              <th>Duration (hrs)</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {availability.map((entry, index) => (
              <tr key={index}>
                <td>
                  <Form.Control
                    type="date"
                    name="date"
                    value={entry.date || ''}
                    onChange={(e) => handleScheduleChange(e, index, 'date')}
                    className="schedule-input"
                  />
                </td>
                <td>
                  <Form.Control
                    type="text"
                    name="time"
                    value={entry.time || ''}
                    onChange={(e) => handleScheduleChange(e, index, 'time')}
                    placeholder="e.g., 9:00 AM - 11:00 AM"
                    className="schedule-input"
                  />
                </td>
                <td>
                  <Form.Control
                    type="number"
                    name="duration"
                    value={entry.duration || ''}
                    onChange={(e) => handleScheduleChange(e, index, 'duration')}
                    placeholder="Duration in hours"
                    className="schedule-input"
                  />
                </td>
                <td>
                  {entry.isNew ? (
                    <Button
                      onClick={() => handleSaveAvailability(entry, index)}
                      className="home-button"
                    >
                      Save
                    </Button>
                  ) : (
                    <Button
                      onClick={() => handleDeleteAvailability(index)}
                      className="delete-btn"
                    >
                      Delete
                    </Button>
                  )}
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
