import React, { useState, useEffect } from 'react';
import { Form, Button, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const mentors = [
  { id: 1, name: 'John Doe', availableDates: ['2024-11-01', '2024-11-05', '2024-11-15'] },
  { id: 2, name: 'Jane Smith', availableDates: ['2024-11-01', '2024-11-10'] },
  { id: 3, name: 'Alice Johnson', availableDates: ['2024-11-15', '2024-11-20'] },
];

const CreateSessionPage = ({ addSession }) => {
  const [newSession, setNewSession] = useState({
    date: '',
    time: '',
    duration: '',
    topic: '',
    mentorId: '',
    description: ''
  });
  const [availableMentors, setAvailableMentors] = useState([]);
  
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewSession({ ...newSession, [name]: value });

    // If date changes, filter mentors based on the new date
    if (name === 'date') {
      filterAvailableMentors(value);
    }
  };

  const filterAvailableMentors = (selectedDate) => {
    const filteredMentors = mentors.filter(mentor => mentor.availableDates.includes(selectedDate));
    setAvailableMentors(filteredMentors);
    setNewSession((prev) => ({ ...prev, mentorId: '' }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    addSession({ id: Date.now(), ...newSession, mentor: availableMentors.find(m => m.id === parseInt(newSession.mentorId))?.name });
    navigate('/Admin');
  };

  return (
    <Container className="mt-5">
      <h2 className='text-center'>Create New Session</h2>
      <Form onSubmit={handleSubmit} className='donate-form'>
        <Form.Group className="mb-3">
          <Form.Label>Date</Form.Label>
          <Form.Control type="date" name="date" value={newSession.date} onChange={handleChange} required />
        </Form.Group>
        
        <Form.Group className="mb-3">
          <Form.Label>Time</Form.Label>
          <Form.Control type="text" name="time" placeholder="e.g., 10:00 AM - 12:00 PM" value={newSession.time} onChange={handleChange} required />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Duration</Form.Label>
          <Form.Control type="text" name="duration" placeholder="e.g., 2 hours" value={newSession.duration} onChange={handleChange} required />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Topic</Form.Label>
          <Form.Control type="text" name="topic" value={newSession.topic} onChange={handleChange} required />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Assigned Mentor</Form.Label>
          <Form.Select name="mentorId" value={newSession.mentorId} onChange={handleChange} required>
            <option value="">Select a mentor</option>
            {availableMentors.length === 0 ? (
              <option disabled>No mentors available for this date</option>
            ) : (
              availableMentors.map((mentor) => (
                <option key={mentor.id} value={mentor.id}>
                  {mentor.name}
                </option>
              ))
            )}
          </Form.Select>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control as="textarea" rows={3} name="description" value={newSession.description} onChange={handleChange} />
        </Form.Group>

        <Button type="submit" className='w-100 home-button'>Create Session</Button>
      </Form>
    </Container>
  );
};

export default CreateSessionPage;

