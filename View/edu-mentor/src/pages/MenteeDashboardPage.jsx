import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button, Alert } from 'react-bootstrap';
import RegisterUpcomingSessions from '../components/RegisterUpcomingSessions';
import RegisteredSessions from '../components/RegisteredSessions';
import AttendedHours from '../components/AttendedHours';
import Interests from '../components/InterestsSelection';
import { getSessions, updateUserEmail, updateUserName, getUserSessions, registerMentee, getMenteeAttendedHours, addTopicsToUser, deleteTopicsFromUser, getUserTopics } from '../api/apiClient'; 
import { useAuthContext } from '../context/useAuthContext';

const MenteeDashboardPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const { user, updateUser } = useAuthContext();

  const [dashboardData, setDashboardData] = useState({
    sessions: [],
    registeredSessions: [],
    attendedHours: 0,
    interests: [],
    profile: {
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || ''
    }
  });

  useEffect(() => {
    const fetchDashboardData = async () => {
      if (!user) return;

      setIsLoading(true);
      setError('');

      try {
        const [
          sessionsData,
          registeredSessionsData,
          attendedHoursData,
          userTopicsData
        ] = await Promise.all([
          getSessions(),
          getUserSessions(user),
          getMenteeAttendedHours(user.userID),
          getUserTopics(user)
        ]);

        setDashboardData({
          sessions: sessionsData,
          registeredSessions: registeredSessionsData,
          attendedHours: attendedHoursData,
          interests: userTopicsData,
          profile: {
            firstName: user?.firstName || '',
            lastName: user?.lastName || '',
            email: user?.email || ''
          }
        });
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
        setError('Failed to load dashboard data. Please try again later.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchDashboardData();
  }, [user]);

  const handleUpdateProfile = async () => {
    const { firstName, lastName, email } = dashboardData.profile;

    try {
      setError('');
      setSuccess('');

      await Promise.all([
        updateUserName(user, firstName, lastName),
        updateUserEmail(user, email)
      ]);

      const updatedUser = { ...user, firstName, lastName, email };
      updateUser(updatedUser);
      console.log(updatedUser);
  
      setSuccess('Profile updated successfully.');
    } catch (error) {
      setError('Error updating profile. Please try again.');
    }
  };

  const handleProfileChange = (field, value) => {
    setDashboardData(prev => ({
      ...prev,
      profile: {
        ...prev.profile,
        [field]: value
      }
    }));
  };

  const handleRegister = async (session) => {
    try {
      await registerMentee(session, user);
      setDashboardData(prev => ({
        ...prev,
        registeredSessions: [...prev.registeredSessions, session]
      }));
    } catch (error) {
      console.error('Error registering for session:', error);
      setError('Failed to register for session. Please try again.');
    }
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
      setError('Failed to add interest. Please try again.');
    }
  };

  const handleDeleteInterest = async (interest) => {
    console.log('Deleting interest:', interest);
    console.log('User:', user); 
    try {
      await deleteTopicsFromUser({ user, topics: interest });
      setDashboardData(prev => ({
        ...prev,
        interests: prev.interests.filter((i) => i.topicID !== interest.topicID)
      }));
    } catch (error) {
      console.error('Error removing interest:', error);
      setError('Failed to remove interest. Please try again.');
    }
  };

  if (isLoading) {
    return <div className="text-center mt-5">Loading dashboard...</div>;
  }

  const { sessions, registeredSessions, attendedHours, interests, profile } = dashboardData;

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
                  value={profile.firstName}
                  onChange={(e) => handleProfileChange('firstName', e.target.value)}
                />
              </Form.Group>
            </Col>

            {/* Last Name */}
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Last Name</Form.Label>
                <Form.Control
                  type="text"
                  value={profile.lastName}
                  onChange={(e) => handleProfileChange('lastName', e.target.value)}
                />
              </Form.Group>
            </Col>

            {/* Email */}
            <Col md={4}>
              <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  value={profile.email}
                  onChange={(e) => handleProfileChange('email', e.target.value)}
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
