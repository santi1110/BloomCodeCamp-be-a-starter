import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function LearnerDashboard() {
  const [assignments, setAssignments] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      window.location.href = '/login';
    }

    const fetchAssignments = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/assignments', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setAssignments(response.data);
      } catch (err) {
        setError('Failed to fetch assignments');
      }
    };

    fetchAssignments();
  }, []);

  const handleSubmitAssignment = async () => {
    const token = localStorage.getItem('token');
    const newAssignment = {
      status: 'PENDING_SUBMISSION',
      number: 1,
      githubUrl: 'https://github.com/example/repo',
      branch: 'main',
      reviewVideoUrl: '',
      username: 'testuser',
    };

    try {
      const response = await axios.post(
        'http://localhost:8080/api/assignments',
        newAssignment,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert('Assignment submitted successfully');
      setAssignments([...assignments, response.data]);
    } catch (err) {
      alert('Error submitting assignment');
    }
  };

  return (
    <div>
      <h1>Learner Dashboard</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <ul>
        {assignments.map((assignment) => (
          <li key={assignment.id}>
            Assignment {assignment.id}: {assignment.status}{' '}
            <Link to={`/assignment/${assignment.id}`}>View</Link>
          </li>
        ))}
      </ul>
      <button onClick={handleSubmitAssignment}>Submit New Assignment</button>
      <button onClick={() => { localStorage.removeItem('token'); window.location.href = '/'; }}>Logout</button>
    </div>
  );
}

export default LearnerDashboard;
