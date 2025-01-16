import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button, Alert } from 'react-bootstrap';
import RegisterUpcomingSessions from '../components/RegisterUpcomingSessions';
import RegisteredSessions from '../components/RegisteredSessions';
import AttendedHours from '../components/AttendedHours';
import Interests from '../components/InterestsSelection';
import { getSessions, updateUserEmail, updateUserName, getUserSessions, registerMentee, getMenteeAttendedHours, addTopicsToUser, deleteTopicsFromUser, getUserTopics } from '../api/apiClient'; 
import { useAuthContext } from '../context/useAuthContext';

const MenteeDashboardPage = () => {
  const [sessions, setSessions] = useState([]);
  const [registeredSessions, setRegisteredSessions] = useState([]);
  const [attendedHours, setAttendedHours] = useState(0);
  const [interests, setInterests] = useState([]);
  const { user, updateUser } = useAuthContext();
  const [firstName, setFirstName] = useState(user?.firstName || '');
  const [lastName, setLastName] = useState(user?.lastName || '');
  const [email, setEmail] = useState(user?.email || '');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

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

  const handleUpdateProfile = async () => {
    try {
      setError('');
      setSuccess('');
      await updateUserName(user, firstName, lastName);
      await updateUserEmail(user, email);
  
      const updatedUser = { ...user, firstName, lastName, email };
      updateUser(updatedUser);
      console.log(updatedUser);
  
      setSuccess('Profile updated successfully.');
    } catch (error) {
      setError('Error updating profile. Please try again.');
    }
  };

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
      <h1 className="text-center mb-4">Mentee Dashboard</h1>
      <Row className='mb-4'>
        <h3>Update Profile</h3>
        {error && <Alert variant="danger">{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}
        <Form>
          <Row className="align-items-center">
            {/* First Name */}
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>First Name</Form.Label>
                <Form.Control
                  type="text"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                />
              </Form.Group>
            </Col>

            {/* Last Name */}
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Last Name</Form.Label>
                <Form.Control
                  type="text"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                />
              </Form.Group>
            </Col>

            {/* Email */}
            <Col md={4}>
              <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Form.Group>
            </Col>

            {/* Save Profile Button */}
            <Col md={2}>
              <Button
                onClick={handleUpdateProfile}
                className="home-button w-100"
                style={{ marginTop: "30px" }}
              >
                Save Profile
              </Button>
            </Col>
          </Row>
        </Form>
      </Row>

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
