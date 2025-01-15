import React from 'react';
import { Card, Button, Table, Form } from 'react-bootstrap';

const AvailabilitySchedule = ({ availability, handleAddAvailability, handleScheduleChange, handleSaveAvailability, handleDeleteAvailability }) => {

  const handleSave = (entry, index) => {
    const combinedDateTime = `${entry.date}T${entry.time}:00.000Z`;
    const payload = {
      time: combinedDateTime, 
      duration: entry.duration
    };
    handleSaveAvailability(payload, index);
  };

  const handleDelete = (entry, index) => {
    const combinedDateTime = `${entry.date}T${entry.time}:00.000Z`;
    const payload = {
      time: combinedDateTime,
      duration: entry.duration
    };
    handleDeleteAvailability(payload, index);
  };

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
              <th>Save</th>
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
                    value={entry.date || ''}
                    onChange={(e) => handleScheduleChange(e, index, 'date')}
                    className="schedule-input"
                  />
                </td>
                <td>
                  <Form.Control
                    type="time"
                    name="time"
                    value={entry.time || ''}
                    onChange={(e) => handleScheduleChange(e, index, 'time')}
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
                  <Button
                    onClick={() => handleSave(entry, index)}
                    className="home-button"
                  >
                    Save
                  </Button>
                </td>
                <td>
                  <Button
                    onClick={() => handleDelete(entry, index)}
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

