import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import LearnerDashboard from './pages/LearnerDashboard';
import ReviewerDashboard from './pages/ReviewerDashboard';
import LearnerAssignmentView from './pages/LearnerAssignmentView';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/dashboard" element={<LearnerDashboard />} />
      <Route path="/reviewer-dashboard" element={<ReviewerDashboard />} />
      <Route path="/assignment/:id" element={<LearnerAssignmentView />} />
    </Routes>
  );
}

export default App;
