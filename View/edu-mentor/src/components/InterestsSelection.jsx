import React from 'react';
import { Card, Button, Form } from 'react-bootstrap';

const Interests = ({ interests, handleInterestChange }) => {
  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Interests</Card.Title>
        <Form>
          <Form.Group>
            <Form.Label>Select Your Interests</Form.Label>
            <div className="d-flex flex-wrap">
              {[  'Technology',
                    'History',
                    'Health',
                    'Business',
                    'Art',
                    'Science'].map((interest, index) => (
                <Button
                  key={index}
                  variant={interests.includes(interest) ? 'primary' : 'outline-primary'}
                  onClick={() => handleInterestChange(interest)}
                  className="m-1"
                  disabled={interests.length >= 3 && !interests.includes(interest)}
                >
                  {interest}
                </Button>
              ))}
            </div>
            <p className="mt-3">Selected Interests: {interests.join(', ')}</p>
          </Form.Group>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default Interests;
