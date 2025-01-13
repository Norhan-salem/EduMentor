import React, { useState } from 'react';
import { Card, Button, Form } from 'react-bootstrap';

const Interests = ({ interests: parentInterests, setInterests: parentSetInterests }) => {
  const [selectedInterest, setSelectedInterest] = useState('');
  const [interests, setInterests] = useState(parentInterests || []);
  const allInterests = [
    'Frontend Development',
    'Backend Development',
    'AI/ML',
    'DevOps',
    'Cloud Computing',
    'Game Development',
  ];

  const updateInterests = (newInterests) => {
    setInterests(newInterests);
    if (parentSetInterests) {
      parentSetInterests(newInterests); 
    }
  };

  const handleAddInterest = () => {
    if (selectedInterest && !interests.includes(selectedInterest) && interests.length < 3) {
      updateInterests([...interests, selectedInterest]);
      setSelectedInterest(''); 
    }
  };

  const handleDeleteInterest = (interestToDelete) => {
    updateInterests(interests.filter((interest) => interest !== interestToDelete));
  };

  return (
    <Card className="mb-4 auth-form">
      <Card.Body>
        <Card.Title>Interests</Card.Title>
        <Form>
          <Form.Group>
            <Form.Label>Select an Interest</Form.Label>
            <div className="d-flex align-items-center mb-3">
              <Form.Select
                value={selectedInterest}
                onChange={(e) => setSelectedInterest(e.target.value)}
                className="me-2"
              >
                <option value="">-- Select an Interest --</option>
                {allInterests.map((interest) => (
                  <option key={interest} value={interest}>
                    {interest}
                  </option>
                ))}
              </Form.Select>
              <Button
                onClick={handleAddInterest}
                disabled={!selectedInterest || interests.includes(selectedInterest) || interests.length >= 3}
                className="home-button"
              >
                Add
              </Button>
            </div>
            <p className="mt-3">Selected Interests:</p>
            <ul className="list-unstyled">
              {interests.map((interest) => (
                <li key={interest} className="d-flex flex-column align-items-start mb-2">
                  <span>{interest}</span>
                  <Button
                    size="sm"
                    className="delete-btn mt-1"
                    onClick={() => handleDeleteInterest(interest)}
                  >
                    Delete
                  </Button>
                </li>
              ))}
            </ul>
          </Form.Group>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default Interests;
