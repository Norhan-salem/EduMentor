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
} from '../services/api';
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
        // Fetch mentor availability data
        const mentorAvailability = await getMentorAvailability(user);
  
        // Transform availability data to include separate date and time fields
        const formattedAvailability = mentorAvailability.map((entry) => {
          const dateObject = new Date(entry.time);
          return {
            ...entry,
            date: dateObject.toISOString().split('T')[0], // Extract date in 'YYYY-MM-DD' format
            time: dateObject.toISOString().split('T')[1].slice(0, 5), // Extract time in 'HH:mm' format
          };
        });
  
        // Update state with formatted availability data
        setAvailability(formattedAvailability);
  
        // Fetch and set additional mentor data
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
  

  const handleAddAvailability = () => {
    const newAvailability = {
      date: '',
      time: '',
      duration: ''
    };
    setAvailability((prev) => [...prev, newAvailability]);
  };

  const handleSaveAvailability = async (entry, index) => {
    try {
      const response = await addMentorAvailability({ mentor: user, availability: entry });
      if (response) {
        setAvailability((prev) => {
          const updated = [...prev];
          return updated;
        });
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
        setAvailability((prev) => prev.filter((_, i) => i !== index));
      }
    } catch (error) {
      console.error('Error deleting availability:', error);
    }
  };

  const handleScheduleChange = (e, index, field) => {
    const { value } = e.target;
    setAvailability((prev) => {
      const updated = [...prev];
      updated[index] = {
        ...updated[index],
        [field]: value
      };
      return updated;
    });
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
  

  const handleDeleteInterest = async (interest) => {
    try {
      await deleteTopicsFromUser({ user, topics: interest });
      setInterests((prev) => prev.filter((i) => i.topicID !== interest.topicID));
    } catch (error) {
      console.error('Error removing interest:', error);
    }
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
