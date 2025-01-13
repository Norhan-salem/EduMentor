import React, { useEffect, useState } from 'react';
import { Container, Button, Form, Row, Col} from 'react-bootstrap';
import MentorCard from '../components/MentorCard';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import { useNavigate } from 'react-router-dom';
import AOS from 'aos';
import 'aos/dist/aos.css';
import axios from 'axios';
import config from '../config';

const mentorsData = [
  { name: "John Doe", bio: "Software Engineer", image: "https://via.placeholder.com/150" },
  { name: "Jane Smith", bio: "Data Scientist", image: "https://via.placeholder.com/150" },
  { name: "Emily Johnson", bio: "UX Designer", image: "https://via.placeholder.com/150" },
  { name: "Michael Brown", bio: "AI Researcher", image: "https://via.placeholder.com/150" },
  { name: "Sophia Davis", bio: "Product Manager", image: "https://via.placeholder.com/150" },
];

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  const handleSearchSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(`${config.backendUrl}/api/search/users?search=${searchQuery}`);
      setUsers(response.data);
      navigate('/results', { state: { users: response.data } });
    } catch (error) {
      console.error('Error fetching users:', error);
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
      <Container fluid className="bg-light text-start px-4" data-aos="fade-in">
      <Row className="align-items-center">
        {/* Left Column: Text and Button */}
        <Col md={6} className="text-md-start text-center ps-5">
          <h1 className="display-4">Empowering the Next Generation</h1>
          <p className="lead">Connecting mentors with students in developing countries.</p>
          <Button variant="primary" size="lg" className="mt-3">
            Get Started
          </Button>
        </Col>

        {/* Right Column: Image */}
        <Col md={5} className="text-center">
          <img
            src="https://corp.tutorocean.com/wp-content/uploads/2021/10/find-a-tutor-image-final-19-1.png"
            alt="Empowerment"
            className="img-fluid"
          />
        </Col>
      </Row>
    </Container>

      {/* Search Bar */}
      <Container className="py-5" data-aos="fade-up">
        <Form onSubmit={handleSearchSubmit} className="d-flex mb-4">
          <Form.Control
            type="text"
            placeholder="Search for a mentor..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
          <Button
            type="submit"
            variant="primary"
            className="ms-2"
          >
            <i className="bi bi-search"></i>
          </Button>
        </Form>
      </Container>

      {/* How It Works */}
      <Container className="py-5 text-center" data-aos="fade-up">
        <h2 className="mb-5">How It Works</h2>
        <div className="d-flex justify-content-around flex-wrap gap-4">
          <div className="how-it-works-step p-4 bg-light rounded shadow-sm">
            <div className="icon-container mb-3">
              <i className="bi bi-person-plus-fill text-primary" style={{ fontSize: '2rem' }}></i>
            </div>
            <h5 className="fw-bold">1. Sign Up</h5>
            <p className="text-muted">Join the platform and create your profile.</p>
          </div>
          <div className="how-it-works-step p-4 bg-light rounded shadow-sm">
            <div className="icon-container mb-3">
              <i className="bi bi-search text-primary" style={{ fontSize: '2rem' }}></i>
            </div>
            <h5 className="fw-bold">2. Find a Mentor</h5>
            <p className="text-muted">Search and connect with experts in your field.</p>
          </div>
          <div className="how-it-works-step p-4 bg-light rounded shadow-sm">
            <div className="icon-container mb-3">
              <i className="bi bi-lightbulb text-primary" style={{ fontSize: '2rem' }}></i>
            </div>
            <h5 className="fw-bold">3. Start Learning</h5>
            <p className="text-muted">Engage in personalized mentoring sessions.</p>
          </div>
        </div>
      </Container>


      {/* Mentor Slider */}
      <Container className="py-5" data-aos="fade-in">
        <h2 className="text-center mb-4">Our Mentors</h2>
        <Slider {...sliderSettings}>
          {mentorsData.map((mentor, index) => (
            <div key={index} className="px-3">
              <MentorCard
                name={mentor.name}
                bio={mentor.bio}
                image={mentor.image}
              />
            </div>
          ))}
        </Slider>
      </Container>

      {/* Testimonials */}
      <Container fluid className="bg-primary text-white py-5" data-aos="fade-up">
        <Container>
          <h2 className="text-center mb-4">Testimonials</h2>
          <Slider {...sliderSettings}>
            <div>
              <p className="text-center">
                "EduMentor helped me find the perfect mentor to guide me in my career."
              </p>
              <p className="text-center">- Sasa</p>
            </div>
            <div>
              <p className="text-center">
                "The platform is easy to use and has amazing mentors!"
              </p>
              <p className="text-center">- Sarah</p>
            </div>
            <div>
              <p className="text-center">
                "I was able to mentor and give back to the community."
              </p>
              <p className="text-center">- Nada</p>
            </div>
          </Slider>
        </Container>
      </Container>
    </div>
  );
};

export default HomePage;
