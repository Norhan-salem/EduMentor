import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { getMentorAvailability, addMentorAvailability, deleteMentorAvailability, getMentoringHours, getUserSessions } from '../services/api'; // Add necessary imports
import { getUserTopics, addTopicsToUser, deleteTopicsFromUser } from '../services/api';
import AssignedSessions from '../components/AssignedSessions';
import AvailabilitySchedule from '../components/AvailabilitySchedule';
import MentoringHours from '../components/MentoringHours';
import Interests from '../components/InterestsSelection';
import { useAuthContext } from '../context/useAuthContext';

const MentorDashboardPage = () => {
  const [sessions, setSessions] = useState([]);
  const [availability, setAvailability] = useState([]);
  const [taughtHours, setTaughtHours] = useState(0);
  const [interests, setInterests] = useState([]);
  const { user } = useAuthContext();

  useEffect(() => {
    const fetchMentorData = async () => {
      if (!user) return;

      try {
        const mentorAvailability = await getMentorAvailability(user);
        setAvailability(mentorAvailability);

        const hours = await getMentoringHours(user);
        setTaughtHours(hours);

        const mentorSessions = await getUserSessions(user);
        setSessions(mentorSessions);

        const userTopics = await getUserTopics(user);
        setInterests(userTopics);
      } catch (error) {
        console.error('Error fetching mentor data:', error);
      }
    };

    fetchMentorData();
  }, [user]);

  const handleAddAvailability = async (availabilityData) => {
    try {
      const response = await addMentorAvailability({ mentor: user, availability: availabilityData });
      if (response) {
        setAvailability((prev) => [...prev, availabilityData]);
      }
    } catch (error) {
      console.error('Error adding mentor availability:', error);
    }
  };

  const handleDeleteAvailability = async (availabilityData) => {
    try {
      const response = await deleteMentorAvailability({ mentor: user, availability: availabilityData });
      if (response) {
        setAvailability((prev) => prev.filter(avail => avail !== availabilityData));
      }
    } catch (error) {
      console.error('Error deleting mentor availability:', error);
    }
  };

  const handleInterestChange = async (interest) => {
    if (interests.includes(interest)) {
      try {
        await deleteTopicsFromUser({ user, topics: interest });
        setInterests((prev) => prev.filter((i) => i !== interest));
      } catch (error) {
        console.error('Error removing interest:', error);
      }
    } else if (interests.length < 3) {
      try {
        await addTopicsToUser({ user, topics: interest });
        setInterests((prev) => [...prev, interest]);
      } catch (error) {
        console.error('Error adding interest:', error);
      }
    }
  };

  const handleScheduleChange = (e, index, field) => {
    const { value } = e.target;
    const updatedAvailability = [...availability];
    updatedAvailability[index] = {
      ...updatedAvailability[index],
      [field]: value,
    };
    setAvailability(updatedAvailability);
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Mentor Dashboard</h1>
      
      <Row>
        {/* Assigned Sessions */}
        <Col md={6}>
          <AssignedSessions sessions={sessions} />
        </Col>

        {/* Availability Schedule */}
        <Col md={6}>
          <AvailabilitySchedule
            availability={availability}
            handleAddAvailability={handleAddAvailability}
            handleScheduleChange={handleScheduleChange}
            handleDeleteAvailability={handleDeleteAvailability}
          />
        </Col>
      </Row>

      <Row>
        {/* Mentoring Hours */}
        <Col md={6}>
          <MentoringHours taughtHours={taughtHours} />
        </Col>

        {/* Interests */}
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

export default MentorDashboardPage;
