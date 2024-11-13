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
          <Nav.Link as={Link} to="/donate" className="me-3" style={{ fontWeight: '500' }}>
            Donate Here
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Header;

