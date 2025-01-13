import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';

const AboutPage = () => {
  return (
    <Container className="about-page mt-5">
      <h1 className="text-center mb-4">About EduMentor</h1>
      <Row className="align-items-center mb-5">
        <Col md={6}>
          <h3>Our Mission</h3>
          <p>
            At EduMentor, our mission is to provide accessible and high-quality mentorship to students in low-income countries, helping them unlock their potential and gain essential skills for their future.
          </p>
          <Button className="mt-3 home-button">Learn More</Button>
        </Col>
        <Col md={6}>
          <img
            src="https://static.startuptalky.com/2022/05/Coaching-vs-mentoring-StartupTalky.jpg"
            alt="EduMentor Mission"
            className="img-fluid rounded shadow"
          />
        </Col>
      </Row>
      
      <Row className="values-section mb-5 text-center">
        <h3 className="mb-4">Our Core Values</h3>
        <Col md={4}>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <h5>Empowerment</h5>
              <p>Empowering students to take charge of their own futures through mentorship and education.</p>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <h5>Accessibility</h5>
              <p>Ensuring that quality mentorship is available to everyone, regardless of their background.</p>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <h5>Community</h5>
              <p>Building a supportive network of mentors and mentees dedicated to lifelong learning.</p>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="text-center">
        <h3 className="mb-4">Our Goals</h3>
        <Col md={{ span: 8, offset: 2 }}>
          <p>
            By 2025, EduMentor aims to provide mentorship opportunities to over 10,000 students globally, bridging educational gaps and fostering sustainable community development.
          </p>
        </Col>
      </Row>
    </Container>
  );
};

export default AboutPage;
