import React, { useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import AssignedSessions from '../components/AssignedSessions';
import AvailabilitySchedule from '../components/AvailabilitySchedule';
import MentoringHours from '../components/MentoringHours';
import Interests from '../components/InterestsSelection';

const MentorDashboard = () => {
  const [sessions] = useState([
    { date: '2024-11-07', duration: '2 hours', topic: 'Math Tutoring' },
    { date: '2024-11-09', duration: '1.5 hours', topic: 'Science Tutoring' },
    { date: '2024-11-12', duration: '3 hours', topic: 'Programming' },
  ]);

  const [availability, setAvailability] = useState([
    { date: '2024-11-07', time: '10:00 AM - 12:00 PM', status: 'Free' },
    { date: '2024-11-09', time: '2:00 PM - 3:30 PM', status: 'Booked' },
    { date: '2024-11-10', time: '9:00 AM - 11:00 AM', status: 'Free' },
  ]);

  const [taughtHours, setTaughtHours] = useState(6);
  const [interests, setInterests] = useState([]);

  const handleInterestChange = (interest) => {
    setInterests((prevInterests) => {
      if (prevInterests.includes(interest)) {
        return prevInterests.filter((i) => i !== interest);
      }
      if (prevInterests.length < 3) {
        return [...prevInterests, interest];
      }
      return prevInterests;
    });
  };

  const handleScheduleChange = (e, index) => {
    const { name, value } = e.target;
    const updatedAvailability = [...availability];
    updatedAvailability[index][name] = value;
    setAvailability(updatedAvailability);
  };

  const handleAddAvailability = () => {
    setAvailability([...availability, { date: '', time: '', status: 'Free' }]);
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Mentor Dashboard</h1>
      <Row>
        <Col md={6}>
          <AssignedSessions sessions={sessions} />
        </Col>
        <Col md={6}>
          <AvailabilitySchedule
            availability={availability}
            handleAddAvailability={handleAddAvailability}
            handleScheduleChange={handleScheduleChange}
          />
        </Col>
      </Row>

      <Row>
        <Col md={6}>
          <MentoringHours taughtHours={taughtHours} />
        </Col>
        <Col md={6}>
          <Interests
            interests={interests}
            handleInterestChange={handleInterestChange}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default MentorDashboard;
