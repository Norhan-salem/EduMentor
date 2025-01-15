import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import RegisterUpcomingSessions from '../components/RegisterUpcomingSessions';
import RegisteredSessions from '../components/RegisteredSessions';
import AttendedHours from '../components/AttendedHours';
import Interests from '../components/InterestsSelection';
import { getSessions, getUserSessions, registerMentee } from '../services/api'; 
import { useAuthContext } from '../context/useAuthContext';

const MenteeDashboardPage = () => {
  const [sessions, setSessions] = useState([]);
  const [registeredSessions, setRegisteredSessions] = useState([]);
  const [attendedHours, setAttendedHours] = useState(4);
  const [interests, setInterests] = useState([]);
  const { user } = useAuthContext();

  useEffect(() => {
    const fetchSessions = async () => {
      try {
        const data = await getSessions();
        setSessions(data);
      } catch (error) {
        console.error('Error fetching sessions:', error);
      }
    };

    const fetchRegisteredSessions = async () => {
      if(!user) return;
      try {
        console.log(user);
        const data = await getUserSessions(user);
        setRegisteredSessions(data);
      } catch (error) {
        console.error('Error fetching registered sessions:', error);
      }
    };

    fetchSessions();
    fetchRegisteredSessions();
  }, [user]);

  const handleRegister = async (session) => {
    try {
      await registerMentee(session, user);
      setRegisteredSessions((prevSessions) => [...prevSessions, session]);
    } catch (error) {
      console.error('Error registering for session:', error);
    }
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Mentee Dashboard</h1>
      
      <Row>
        {/* Upcoming Sessions */}
        <Col md={6}>
          <RegisterUpcomingSessions
            sessions={sessions}
            registeredSessions={registeredSessions}
            handleRegister={handleRegister}
          />
        </Col>

        {/* Attended Hours */}
        <Col md={6}>
          <AttendedHours attendedHours={attendedHours} />
        </Col>
      </Row>

      <Row>
        {/* Registered Sessions */}
        <Col md={12}>
          <RegisteredSessions sessions={registeredSessions} />
        </Col>
      </Row>

      <Row>
        {/* Interests */}
        <Col md={12}>
          <Interests interests={interests} handleInterestChange={(interest) => setInterests(prev => {
            if (prev.includes(interest)) {
              return prev.filter(i => i !== interest);
            }
            if (prev.length < 3) {
              return [...prev, interest];
            }
            return prev;
          })} />
        </Col>
      </Row>
    </Container>
  );
};

export default MenteeDashboardPage;
