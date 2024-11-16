import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Header.css';


const Header = () => {
  return (
    <Navbar bg="primary" variant="dark" expand="lg" className="mb-4" style={{ padding: '1rem' }}>
      <Navbar.Brand as={Link} to="/" className="fw-bold fs-4">
        EduMentor
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
        <Nav>
          <Nav.Link as={Link} to="/" className="me-3" style={{ fontWeight: '500' }}>
            Home
          </Nav.Link>
          <Nav.Link as={Link} to="/about" className="me-3" style={{ fontWeight: '500' }}>
            About
          </Nav.Link>
          <Nav.Link as={Link} to="/login" className="me-3" style={{ fontWeight: '500' }}>
            Login
          </Nav.Link>
          {/* Special "Donate Here" link */}
          <Nav.Link
            as={Link}
            to="/donate"
            className="me-3 special-donate-link"
            style={{
              color: 'white',
              backgroundColor: '#2c2b2b',
              padding: '0.5rem 1rem',
              borderRadius: '5px',
              fontWeight: '600',
              textShadow: '1px 1px 2px rgba(0,0,0,0.3)', 
              boxShadow: '0 4px 6px rgba(0,0,0,0.1)', 
              transition: 'transform 0.2s, background-color 0.2s', 
            }}
            onMouseOver={(e) => {
              e.target.style.backgroundColor = '#29465b'; 
              e.target.style.transform = 'scale(1.05)'; 
            }}
            onMouseOut={(e) => {
              e.target.style.backgroundColor = '#2c2b2b';
              e.target.style.transform = 'scale(1)';
            }}
          >
            Donate Here
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Header;


