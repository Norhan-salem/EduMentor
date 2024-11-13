import React, { useState } from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import UpcomingSessions from '../components/UpcomingSessions';
import AttendedHours from '../components/AttendedHours';
import Interests from '../components/InterestsSelection';

const MenteeDashboardPage = () => {
  const [sessions] = useState([
    { date: '2024-11-07', duration: '2 hours', topic: 'Math Tutoring' },
    { date: '2024-11-09', duration: '1.5 hours', topic: 'Science Tutoring' },
  ]);

  const [attendedHours, setAttendedHours] = useState(4);
  const [interests, setInterests] = useState([]);
  const [registeredSessions, setRegisteredSessions] = useState([]);

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

  const handleRegister = (session) => {
    setRegisteredSessions((prevSessions) => [...prevSessions, session]);
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Mentee Dashboard</h1>
      
      <Row>
        {/* Upcoming Sessions */}
        <Col md={6}>
          <UpcomingSessions 
            sessions={sessions} 
            handleRegister={handleRegister} 
          />
        </Col>

        {/* Attended Hours */}
        <Col md={6}>
          <AttendedHours attendedHours={attendedHours} />
        </Col>
      </Row>

      <Row>
        {/* Interests */}
        <Col md={12}>
          <Interests interests={interests} handleInterestChange={handleInterestChange} />
        </Col>
      </Row>
    </Container>
  );
};

export default MenteeDashboardPage;
