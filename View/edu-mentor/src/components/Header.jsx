import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useSpring, animated } from 'react-spring';
import { useAuthContext } from '../context/useAuthContext';
import '../styles/Header.css';

const Header = () => {
  const { user, logout } = useAuthContext();
  const location = useLocation();
  const navigate = useNavigate();

  const donateBtnAnim = useSpring({
    transform: 'scale(1.1)',
    config: { tension: 250, friction: 15 },
    loop: { reverse: true },
  });

  const isActive = (path) => location.pathname === path;

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  // Map backend enums to frontend roles
  const userTypeMap = {
    ADMIN: 'admin',
    MENTOR: 'mentor',
    MENTEE: 'mentee',
    ONLINEDONOR: 'donor',
  };

  return (
    <Navbar expand="lg" sticky="top" className="custom-navbar">
      <Container>
        {/* Left Section: Brand */}
        <Navbar.Brand as={Link} to="/" className="brand">
          EduMentor
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="navbar-nav" />

        {/* Centered Navigation Links */}
        <Navbar.Collapse id="navbar-nav" className="justify-content-center">
          <Nav className="nav-links">
            <Nav.Link
              as={Link}
              to="/"
              className={`nav-link ${isActive('/') ? 'active-link' : ''}`}
            >
              Home
            </Nav.Link>
            <Nav.Link
              as={Link}
              to="/about"
              className={`nav-link ${isActive('/about') ? 'active-link' : ''}`}
            >
              About
            </Nav.Link>

            {/* Conditionally Render Login or User-specific links */}
            {!user ? (
              <Nav.Link
                as={Link}
                to="/auth"
                className={`nav-link ${isActive('/auth') ? 'active-link' : ''}`}
              >
                Login
              </Nav.Link>
            ) : (
              <>
                {/* Show different links based on userType */}
                {user.userType === userTypeMap.MENTOR && (
                  <Nav.Link
                    as={Link}
                    to="/mentor-dashboard"
                    className={`nav-link ${isActive('/mentor-dashboard') ? 'active-link' : ''}`}
                  >
                    Dashboard
                  </Nav.Link>
                )}
                {user.userType === userTypeMap.MENTEE && (
                  <Nav.Link
                    as={Link}
                    to="/mentee-dashboard"
                    className={`nav-link ${isActive('/mentee-dashboard') ? 'active-link' : ''}`}
                  >
                    Dashboard
                  </Nav.Link>
                )}
                {user.userType === userTypeMap.ADMIN && (
                  <Nav.Link
                    as={Link}
                    to="/admin-dashboard"
                    className={`nav-link ${isActive('/admin-dashboard') ? 'active-link' : ''}`}
                  >
                    Dashboard
                  </Nav.Link>
                )}
                {user.userType === userTypeMap.ONLINEDONOR && (
                  <Nav.Link
                    as={Link}
                    to="/donations"
                    className={`nav-link ${isActive('/donations') ? 'active-link' : ''}`}
                  >
                    Donations
                  </Nav.Link>
                )}

                {/* Logout link */}
                <Nav.Link
                  as={Link}
                  to="#"
                  onClick={handleLogout}
                  className={`nav-link ${isActive('/logout') ? 'active-link' : ''}`}
                >
                  Logout
                </Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>

        {/* Donate Button on the Right */}
        <animated.div style={donateBtnAnim} className="donate-btn-container">
          <Link as={Link} to="/donate" className="donate-btn">
            Donate
          </Link>
        </animated.div>
      </Container>
    </Navbar>
  );
};

export default Header;
