import React, { useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { createSession } from '../api/apiClient';
import { useAuthContext } from '../context/useAuthContext';

const CreateSessionPage = () => {
  const [newSession, setNewSession] = useState({
    date: '',
    time: '',
    duration: '',
    name: '',
  });

  const {user} = useAuthContext();

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewSession({ ...newSession, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate inputs
    if (isNaN(newSession.duration) || newSession.duration <= 0) {
      alert('Duration must be a positive number.');
      return;
    }

    if (!newSession.time.match(/^([01]\d|2[0-3]):([0-5]\d)$/)) {
      alert('Invalid time format. Use HH:MM in 24-hour format.');
      return;
    }

    const sessionDateTime = `${newSession.date}T${newSession.time}:00`;

    const sessionToSubmit = {
      date: sessionDateTime,
      duration: parseFloat(newSession.duration),
      name: newSession.name.trim(),
      adminId: user.userID,
    };

    try {
      await createSession(sessionToSubmit);
      alert('Session created successfully!');
      navigate('/admin-dashboard');
    } catch (error) {
      alert('Failed to create session. Please try again later.');
    }
  };

  return (
    <Container className="mt-5">
      <h2 className="text-center mb-4">Create New Session</h2>
      <Form onSubmit={handleSubmit} className="donate-form">
        {/* Date Field */}
        <Form.Group className="mb-3">
          <Form.Label>Date</Form.Label>
          <Form.Control
            type="date"
            name="date"
            value={newSession.date}
            onChange={handleChange}
            required
          />
        </Form.Group>

        {/* Time Field */}
        <Form.Group className="mb-3">
          <Form.Label>Time</Form.Label>
          <Form.Control
            type="time"
            name="time"
            value={newSession.time}
            onChange={handleChange}
            required
          />
        </Form.Group>

        {/* Duration Field */}
        <Form.Group className="mb-3">
          <Form.Label>Duration (in hours)</Form.Label>
          <Form.Control
            type="number"
            name="duration"
            min="0.1"
            step="0.1"
            value={newSession.duration}
            onChange={handleChange}
            placeholder="Enter duration in hours"
            required
          />
        </Form.Group>

        {/* Session Name Field */}
        <Form.Group className="mb-3">
          <Form.Label>Session Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={newSession.name}
            onChange={handleChange}
            placeholder="Enter session name (e.g., Math Tutoring)"
            required
          />
        </Form.Group>

        {/* Submit Button */}
        <Button type="submit" className="w-100 home-button">
          Create Session
        </Button>
      </Form>
    </Container>
  );
};

export default CreateSessionPage;
