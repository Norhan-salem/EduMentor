import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import RegisterUpcomingSessions from '../components/RegisterUpcomingSessions';
import RegisteredSessions from '../components/RegisteredSessions';
import AttendedHours from '../components/AttendedHours';
import Interests from '../components/InterestsSelection';
import { getSessions, getUserSessions, registerMentee, getMenteeAttendedHours, addTopicsToUser, deleteTopicsFromUser, getUserTopics } from '../services/api'; 
import { useAuthContext } from '../context/useAuthContext';

const MenteeDashboardPage = () => {
  const [sessions, setSessions] = useState([]);
  const [registeredSessions, setRegisteredSessions] = useState([]);
  const [attendedHours, setAttendedHours] = useState(0);
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

    const fetchAttendedHours = async () => {
      if(!user) return;
      try {
        const data = await getMenteeAttendedHours(user.userID);
        setAttendedHours(data);
      } catch (error) {
        console.error('Error fetching attended hours:', error);
      }
    };
    const fetchUserTopics = async () => {
      if (!user) return;
      try {
        const data = await getUserTopics(user);
        setInterests(data);
      } catch (error) {
        console.error('Error fetching user topics:', error);
      }
    };

    fetchSessions();
    fetchRegisteredSessions();
    fetchAttendedHours();
    fetchUserTopics();
  }, [user]);

  const handleRegister = async (session) => {
    try {
      await registerMentee(session, user);
      setRegisteredSessions((prevSessions) => [...prevSessions, session]);
    } catch (error) {
      console.error('Error registering for session:', error);
    }
  };

  const handleAddInterest = async (interest) => {
    try {
      const payload = {
        user: user, 
        topics: interest, 
      };
      await addTopicsToUser(payload);
      setInterests((prev) => [...prev, interest]);
    } catch (error) {
      console.error('Error adding interest:', error);
    }
  };
  

  const handleDeleteInterest = async (topicID) => {
    try {
      await deleteTopicsFromUser({ user, topics: [{ topicID }] });
      setInterests((prev) => prev.filter((i) => i.topicID !== topicID));
    } catch (error) {
      console.error('Error removing interest:', error);
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
        <Interests 
            interests={interests} 
            onAddInterest={handleAddInterest}
            onDeleteInterest={handleDeleteInterest}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default MenteeDashboardPage;
