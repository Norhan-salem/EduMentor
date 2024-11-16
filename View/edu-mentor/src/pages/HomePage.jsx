import React, { useState } from 'react';
import { Container, Row, Col, Button, Form } from 'react-bootstrap';
import MentorCard from '../components/MentorCard';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";

const mentorsData = [
  { name: "John Doe", bio: "Software Engineer", image: "https://via.placeholder.com/150" },
  { name: "Jane Smith", bio: "Data Scientist", image: "https://via.placeholder.com/150" },
  { name: "Emily Johnson", bio: "UX Designer", image: "https://via.placeholder.com/150" },
  { name: "Michael Brown", bio: "AI Researcher", image: "https://via.placeholder.com/150" },
  { name: "Sophia Davis", bio: "Product Manager", image: "https://via.placeholder.com/150" },
];

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearch = (event) => {
    setSearchQuery(event.target.value.toLowerCase());
  };

  const filteredMentors = mentorsData.filter(
    mentor =>
      mentor.name.toLowerCase().includes(searchQuery) ||
      mentor.bio.toLowerCase().includes(searchQuery)
  );

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
      <Container fluid className="bg-light text-center py-5">
        <h1 className="display-4">Empowering the Next Generation</h1>
        <p className="lead">Connecting mentors with students in developing countries.</p>
        <Button variant="primary" size="lg" className="mt-3">
          Get Started
        </Button>
      </Container>

      {/* Search Bar */}
      <Container className="py-5">
        <Form>
          <Form.Group controlId="mentorSearch">
            <Form.Control
              type="text"
              placeholder="Search for a mentor..."
              value={searchQuery}
              onChange={handleSearch}
              className="mb-4"
            />
          </Form.Group>
        </Form>

        {/* Mentor Slider */}
        <h2 className="text-center mb-4">Our Mentors</h2>
        <Slider {...sliderSettings}>
          {filteredMentors.map((mentor, index) => (
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
    </div>
  );
};


export default HomePage;

