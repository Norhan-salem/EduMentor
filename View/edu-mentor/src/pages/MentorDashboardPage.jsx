import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import {
  getMentorAvailability,
  addMentorAvailability,
  deleteMentorAvailability,
  getMentoringHours,
  getUserSessions,
  getUserTopics,
  addTopicsToUser,
  deleteTopicsFromUser
} from '../api/apiClient';
import AssignedSessions from '../components/AssignedSessions';
import AvailabilitySchedule from '../components/AvailabilitySchedule';
import MentoringHours from '../components/MentoringHours';
import Interests from '../components/InterestsSelection';
import { useAuthContext } from '../context/useAuthContext';

const MentorDashboardPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [dashboardData, setDashboardData] = useState({
    sessions: [],
    availability: [],
    taughtHours: 0,
    interests: []
  });
  const { user } = useAuthContext();

  useEffect(() => {
    const fetchMentorData = async () => {
      if (!user) return;

      setIsLoading(true);
      setError(null);

      try {
        const [
          mentorAvailability,
          hours,
          mentorSessions,
          userTopics
        ] = await Promise.all([
          getMentorAvailability(user),
          getMentoringHours(user),
          getUserSessions(user),
          getUserTopics(user)
        ]);

        // Transform availability data to include separate date and time fields
        const formattedAvailability = mentorAvailability.map((entry) => {
          const dateObject = new Date(entry.time);
          return {
            ...entry,
            date: dateObject.toISOString().split('T')[0],
            time: dateObject.toISOString().split('T')[1].slice(0, 5),
          };
        });

        setDashboardData({
          sessions: mentorSessions,
          availability: formattedAvailability,
          taughtHours: hours,
          interests: userTopics
        });
      } catch (error) {
        console.error('Error fetching mentor data:', error);
        setError('Failed to load dashboard data. Please try again later.');
      } finally {
        setIsLoading(false);
      }
    };
  
    fetchMentorData();
  }, [user]);
  

  const handleAddAvailability = () => {
    const newAvailability = {
      date: '',
      time: '',
      duration: ''
    };
    setDashboardData(prev => ({
      ...prev,
      availability: [...prev.availability, newAvailability]
    }));
  };

  const handleSaveAvailability = async (entry, index) => {
    try {
      const response = await addMentorAvailability({ mentor: user, availability: entry });
      if (response) {
        setDashboardData(prev => ({
          ...prev,
          availability: [...prev.availability]
        }));
      }
    } catch (error) {
      console.error('Error saving availability:', error);
    }
  };

  const handleDeleteAvailability = async (payload, index) => {
    try {
      // Make the API call to delete the specified availability
      const response = await deleteMentorAvailability({ mentor: user, availability: payload });
  
      // If the deletion is successful, update the local state
      if (response) {
        setDashboardData(prev => ({
          ...prev,
          availability: prev.availability.filter((_, i) => i !== index)
        }));
      }
    } catch (error) {
      console.error('Error deleting availability:', error);
    }
  };

  const handleScheduleChange = (e, index, field) => {
    const { value } = e.target;
    setDashboardData(prev => ({
      ...prev,
      availability: prev.availability.map((item, i) =>
          i === index ? { ...item, [field]: value } : item
      )
    }));
  };

  const handleAddInterest = async (interest) => {
    try {
      await addTopicsToUser({ user, topics: interest });
      setDashboardData(prev => ({
        ...prev,
        interests: [...prev.interests, interest]
      }));
    } catch (error) {
      console.error('Error adding interest:', error);
    }
  };
  

  const handleDeleteInterest = async (interest) => {
    try {
      await deleteTopicsFromUser({ user, topics: interest });
      setDashboardData(prev => ({
        ...prev,
        interests: prev.interests.filter((i) => i.topicID !== interest.topicID)
      }));
    } catch (error) {
      console.error('Error removing interest:', error);
    }
  };

  if (isLoading) {
    return <div className="text-center mt-5">Loading dashboard...</div>;
  }

  if (error) {
    return <div className="text-center mt-5 text-danger">{error}</div>;
  }

  const { sessions, availability, taughtHours, interests } = dashboardData;

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
            handleSaveAvailability={handleSaveAvailability}
            handleDeleteAvailability={handleDeleteAvailability}
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
            onAddInterest={handleAddInterest}
            onDeleteInterest={handleDeleteInterest}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default MentorDashboardPage;
