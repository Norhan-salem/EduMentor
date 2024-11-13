import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import DonatePage from './pages/DonatePage';
import LoginPage from './pages/LoginPage';
import Footer from './components/Footer';
import './App.css';
import MentorDashboardPage from './pages/MentorDashboardPage';
import MenteeDashboardPage from './pages/MenteeDashboardPage';

const App = () => {
  return (
    <div className='app-wrapper'>
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/Mentor" element={<MentorDashboardPage />} />
        <Route path="/Mentee" element={<MenteeDashboardPage />} />
      </Routes>
      <Footer />
    </Router>
    </div>
  );
};

export default App;

