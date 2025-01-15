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
import AdminDashboardPage from './pages/AdminDashboardPage';
import CreateSessionPage from './pages/CreateSessionPage';
import ResultsPage from './pages/ResultsPage';
import DonationsPage from './pages/DisplayDonationsPage';
import AssignMentorPage from './pages/AssignMentorPage';
import SessionDetailsPage from './pages/SessionDetailsPage';
import { useAuthContext } from './context/useAuthContext';

const App = () => {
  const { isInitialized } = useAuthContext();

  if (!isInitialized) {
    return <div>Loading...</div>;
  }
  return (
    <div className='app-wrapper'>
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/auth" element={<LoginPage />} />
        <Route path="/mentor-dashboard" element={<MentorDashboardPage />} />
        <Route path="/mentee-dashboard" element={<MenteeDashboardPage />} />
        <Route path="/admin-dashboard" element={<AdminDashboardPage />} />
        <Route path="/create-session" element={<CreateSessionPage />} />
        <Route path="/results" element={<ResultsPage />} />
        <Route path="/donations" element={<DonationsPage />} />
        <Route path="/assign-mentor" element={<AssignMentorPage />} />
        <Route path="/session-details/:sessionID" element={<SessionDetailsPage />} />
        <Route path="*" element={<h1>Not Found</h1>} />
      </Routes>
      <Footer />
    </Router>
    </div>
  );
};

export default App;

