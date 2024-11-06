import React from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import MentorCard from '../components/MentorCard';

const HomePage = () => {
  return (
    <div>
      <Container fluid className="bg-light text-center py-5">
        <h1 className="display-4">Empowering the Next Generation</h1>
        <p className="lead">Connecting mentors with students in developing countries.</p>
        <Button variant="primary" size="lg" className="mt-3">Get Started</Button>
      </Container>

      <Container className="py-5">
        <h2 className="text-center mb-4">Our Mentors</h2>
        <Row>
          <Col md={4}><MentorCard name="John Doe" bio="Software Engineer" image="https://via.placeholder.com/150" /></Col>
          <Col md={4}><MentorCard name="Jane Smith" bio="Data Scientist" image="https://via.placeholder.com/150" /></Col>
          <Col md={4}><MentorCard name="Emily Johnson" bio="UX Designer" image="https://via.placeholder.com/150" /></Col>
        </Row>
      </Container>
    </div>
  );
};

export default HomePage;
