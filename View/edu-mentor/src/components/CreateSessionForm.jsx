import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const CreateSessionForm = ({ show, handleClose, handleCreateSession }) => {
  const [newSession, setNewSession] = useState({
    date: '',
    time: '',
    duration: '',
    topic: '',
    mentor: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewSession({ ...newSession, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    handleCreateSession(newSession);
    setNewSession({ date: '', time: '', duration: '', topic: '', mentor: '' });
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Create New Session</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
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

          <Form.Group className="mb-3">
            <Form.Label>Time</Form.Label>
            <Form.Control
              type="text"
              name="time"
              placeholder="e.g., 10:00 AM - 12:00 PM"
              value={newSession.time}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Duration</Form.Label>
            <Form.Control
              type="text"
              name="duration"
              placeholder="e.g., 2 hours"
              value={newSession.duration}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Topic</Form.Label>
            <Form.Control
              type="text"
              name="topic"
              value={newSession.topic}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Assigned Mentor</Form.Label>
            <Form.Control
              type="text"
              name="mentor"
              placeholder="Mentor's name"
              value={newSession.mentor}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Button variant="primary" type="submit" className="w-100">
            Create Session
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default CreateSessionForm;
