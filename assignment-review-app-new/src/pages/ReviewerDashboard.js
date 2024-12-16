import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ReviewerDashboard() {
  const [assignments, setAssignments] = useState([]);

  useEffect(() => {
    const fetchAssignments = async () => {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/api/assignments', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssignments(response.data.filter((assignment) => assignment.status === 'SUBMITTED'));
    };
    fetchAssignments();
  }, []);

  const handleClaimAssignment = async (id) => {
    const token = localStorage.getItem('token');
    await axios.put(
      `http://localhost:8080/api/assignments/${id}`,
      { status: 'IN_REVIEW' },
      { headers: { Authorization: `Bearer ${token}` } }
    );
    alert('Assignment claimed!');
    // Refresh the assignments
    const fetchAssignments = async () => {
      const response = await axios.get('http://localhost:8080/api/assignments', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssignments(response.data.filter((assignment) => assignment.status === 'SUBMITTED'));
    };
    fetchAssignments();
  };

  const handleCompleteAssignment = async (id) => {
    const token = localStorage.getItem('token');
    await axios.put(
      `http://localhost:8080/api/assignments/${id}`,
      { status: 'COMPLETED' },
      { headers: { Authorization: `Bearer ${token}` } }
    );
    alert('Assignment completed!');
    // Refresh the assignments
    const fetchAssignments = async () => {
      const response = await axios.get('http://localhost:8080/api/assignments', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssignments(response.data.filter((assignment) => assignment.status === 'IN_REVIEW'));
    };
    fetchAssignments();
  };

  return (
    <div>
      <h1>Reviewer Dashboard</h1>
      <ul>
        {assignments.map((assignment) => (
          <li key={assignment.id}>
            Assignment {assignment.id}: {assignment.status}{' '}
            <button onClick={() => handleClaimAssignment(assignment.id)}>Claim</button>
            <button onClick={() => handleCompleteAssignment(assignment.id)}>Complete Review</button>
          </li>
        ))}
      </ul>
      <button onClick={() => { localStorage.removeItem('token'); window.location.href = '/'; }}>Logout</button>
    </div>
  );
}

export default ReviewerDashboard;
