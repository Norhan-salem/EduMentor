import React, { useState } from 'react';
import { Container, Button, Form, Row, Col, Card, InputGroup } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';
import { FaSortAlphaDown, FaSortNumericDown } from 'react-icons/fa'; 
import AOS from 'aos'; 
import 'aos/dist/aos.css'; 

AOS.init();

const ResultsPage = () => {
  const location = useLocation();
  // dummy data
  const users = location.state?.users || [
    { name: "John Doe", email: "john@gmail.com", mentoringHours: 120, interests: "AI, Web Development" },
    { name: "Jane Smith", email: "jane@gmail.com", mentoringHours: 90, interests: "Data Science, Machine Learning" },
    { name: "Alice Brown", email: "alice@outlook.com", mentoringHours: 150, interests: "UX/UI Design, Graphic Design" },
    { name: "Bob Johnson", email: "bob@techmail.com", mentoringHours: 80, interests: "Cybersecurity, Cloud Computing" },
    { name: "Mary Williams", email: "mary@domain.com", mentoringHours: 100, interests: "Blockchain, Finance" },
    { name: "David Wilson", email: "david@devmail.com", mentoringHours: 110, interests: "Web Development, Mobile Apps" },
    { name: "Olivia Moore", email: "olivia@techcompany.com", mentoringHours: 75, interests: "Software Engineering, Data Analysis" },
    { name: "Ethan Harris", email: "ethan@startup.com", mentoringHours: 130, interests: "Startups, Entrepreneurship" },
    { name: "Sophia Martinez", email: "sophia@socialmedia.com", mentoringHours: 140, interests: "Digital Marketing, Content Creation" },
    { name: "James Taylor", email: "james@techworld.com", mentoringHours: 95, interests: "AI, Robotics" }
  ];

  const [sortOption, setSortOption] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  
  // Filter users based on name or email
  const filteredUsers = users.filter(
    (user) =>
      user.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      user.email.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // Sort users based on the selected sort option
  const sortedUsers = [...filteredUsers].sort((a, b) => {
    if (sortOption === 'alphabetical') {
      return a.name.localeCompare(b.name);
    } else if (sortOption === 'hours') {
      return b.mentoringHours - a.mentoringHours;
    }
    return 0;
  });

  return (
    <Container className="py-5">
      <h1 className="mb-4 text-center">Search Results</h1>

      <Row className="mb-4">
        <Col md={8}>
          <InputGroup>
            <Form.Control
              type="text"
              placeholder="Search by Name or Email..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              aria-label="Search Mentors"
            />
          </InputGroup>
        </Col>
        <Col md={4} className="text-md-end">
          <Button
            variant="outline-primary"
            onClick={() => setSortOption('alphabetical')}
            className="me-2"
          >
            <FaSortAlphaDown /> Sort by Name
          </Button>
          <Button
            variant="outline-primary"
            onClick={() => setSortOption('hours')}
          >
            <FaSortNumericDown /> Sort by Hours
          </Button>
        </Col>
      </Row>

      <Row data-aos="fade-up">
        {sortedUsers.map((user, index) => (
          <Col key={index} md={4} className="mb-4">
            <Card className="shadow-sm hover-shadow-lg border-0 rounded-3">
              <Card.Body className="p-4">
                <div className="d-flex align-items-center mb-3">
                  {/* profile picture */}
                  <div className="avatar me-3">
                    <img
                      src="https://via.placeholder.com/50"
                      alt={user.name}
                      className="rounded-circle"
                    />
                  </div>
                  <div>
                    <Card.Title>{user.name}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">{user.email}</Card.Subtitle>
                  </div>
                </div>

                <Card.Text className="mb-2">Mentoring Hours: {user.mentoringHours}</Card.Text>
                <Card.Text>Interests: {user.interests}</Card.Text>

                <Button variant="outline-primary" className="mt-3">
                  View Profile
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {sortedUsers.length === 0 && (
        <div className="text-center">
          <h4>No mentors found matching your criteria.</h4>
        </div>
      )}
    </Container>
  );
};

export default ResultsPage;
