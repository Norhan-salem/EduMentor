
import React, { useEffect, useState } from 'react';
import { Container, Button, Form, Row, Col } from 'react-bootstrap';
import MentorCard from '../components/MentorCard';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useNavigate } from 'react-router-dom';
import AOS from 'aos';
import 'aos/dist/aos.css';
import '../styles/Home.css';
import { searchMentors } from '../api/apiClient';

const mentorsData = [
  { name: "John Doe", bio: "Software Engineer", image: "https://via.placeholder.com/150" },
  { name: "Jane Smith", bio: "Data Scientist", image: "https://via.placeholder.com/150" },
  { name: "Emily Johnson", bio: "UX Designer", image: "https://via.placeholder.com/150" },
  { name: "Michael Brown", bio: "AI Researcher", image: "https://via.placeholder.com/150" },
  { name: "Sophia Davis", bio: "Product Manager", image: "https://via.placeholder.com/150" },
];

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  const handleSearchSubmit = async (e) => {
    e.preventDefault();
    try {
      const users = await searchMentors(searchQuery);
      navigate('/results', { state: { users } });
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  };

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
  };

  return (
    <div>
      {/* Hero Section */}
      <Container fluid className="home-bg-light" data-aos="fade-in">
        <Row className="align-items-center">
          <Col md={6} className="text-md-start text-center ps-5">
            <h1 className="home-heading">Empowering the Next Generation</h1>
            <p className="home-lead">Connecting mentors with students in developing countries.</p>
            <Button className="home-button">Get Started</Button>
          </Col>
        </Row>
      </Container>

      {/* Search Bar */}
      <Container className="py-5" data-aos="fade-up">
        <Form onSubmit={handleSearchSubmit} className="home-form">
          <Form.Control
            type="text"
            placeholder="Search for a mentor..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="home-form-input"
          />
          <Button type="submit" className="home-button">
            <i className="bi bi-search"></i>
          </Button>
        </Form>
      </Container>

      {/* How It Works */}
      <Container className="py-5 text-center" data-aos="fade-up">
        <h2 className="mb-5">How It Works</h2>
        <div className="d-flex justify-content-around flex-wrap gap-4">
          <div className="home-how-it-works-step">
            <div className="home-how-it-works-icon-container mb-3">
              <i className="bi bi-person-plus-fill home-how-it-works-icon"></i>
            </div>
            <h5 className="fw-bold">1. Sign Up</h5>
            <p>Join the platform and create your profile.</p>
          </div>
          <div className="home-how-it-works-step">
            <div className="home-how-it-works-icon-container mb-3">
              <i className="bi bi-search home-how-it-works-icon"></i>
            </div>
            <h5 className="fw-bold">2. Find a Mentor</h5>
            <p>Search and connect with experts in your field.</p>
          </div>
          <div className="home-how-it-works-step">
            <div className="home-how-it-works-icon-container mb-3">
              <i className="bi bi-lightbulb home-how-it-works-icon"></i>
            </div>
            <h5 className="fw-bold">3. Start Learning</h5>
            <p>Engage in personalized mentoring sessions.</p>
          </div>
        </div>
      </Container>

      {/* Mentor Slider */}
      <Container className="py-5" data-aos="fade-in">
        <h2 className="text-center mb-4">Our Mentors</h2>
        <Slider {...sliderSettings}>
          {mentorsData.map((mentor, index) => (
            <div key={index} className="px-3">
              <MentorCard name={mentor.name} bio={mentor.bio} image={mentor.image} />
            </div>
          ))}
        </Slider>
      </Container>

      {/* Testimonials */}
      <Container fluid className="home-testimonials-section" data-aos="fade-up">
        <Container>
          <h2 className="home-testimonials-heading">Testimonials</h2>
          <Slider {...sliderSettings}>
            <div>
              <p className="home-testimonials-text">
                "EduMentor helped me find the perfect mentor to guide me in my career."
              </p>
              <p>- Sasa</p>
            </div>
            <div>
              <p className="home-testimonials-text">
                "The platform is easy to use and has amazing mentors!"
              </p>
              <p>- Sarah</p>
            </div>
            <div>
              <p className="home-testimonials-text">
                "I was able to mentor and give back to the community."
              </p>
              <p>- Nada</p>
            </div>
          </Slider>
        </Container>
      </Container>
    </div>
  );
};

export default HomePage;
