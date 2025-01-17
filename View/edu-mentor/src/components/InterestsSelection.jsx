import React, { useState, useEffect } from 'react';
import { Card, Button, Form } from 'react-bootstrap';
import { getAllTopics } from '../api/apiClient';

const Interests = ({ interests, onAddInterest, onDeleteInterest }) => {
  const [allTopics, setAllTopics] = useState([]);
  const [selectedTopicID, setSelectedTopicID] = useState('');

  useEffect(() => {
    const fetchTopics = async () => {
      try {
        const data = await getAllTopics();
        setAllTopics(data.filter((topic) => !topic.deleted));
      } catch (error) {
        console.error('Error fetching topics:', error);
      }
    };
    fetchTopics();
  }, []);

  const handleAddInterest = () => {
    if (
      selectedTopicID &&
      !interests.some((interest) => interest.topicID === selectedTopicID)
    ) {
      const topic = allTopics.find((topic) => topic.topicID === Number(selectedTopicID));
      if (topic) {
        onAddInterest(topic);
        setSelectedTopicID('');
      }
    }
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
                value={selectedTopicID}
                onChange={(e) => setSelectedTopicID(e.target.value)}
                className="me-2"
              >
                <option value="">-- Select an Interest --</option>
                {allTopics.map((topic) => (
                  <option key={topic.topicID} value={topic.topicID}>
                    {topic.topicsName}
                  </option>
                ))}
              </Form.Select>
              <Button
                onClick={handleAddInterest}
                disabled={!selectedTopicID || interests.some((i) => i.topicID === Number(selectedTopicID))}
                className="home-button"
              >
                Add
              </Button>
            </div>
            <p className="mt-3">Selected Interests:</p>
            <ul className="list-unstyled">
              {interests.map((interest) => (
                <li key={interest.topicID} className="d-flex flex-column align-items-start mb-2">
                  <span>{interest.topicsName}</span>
                  <Button
                    size="sm"
                    className="delete-btn mt-1"
                    onClick={() => onDeleteInterest(interest)}
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
