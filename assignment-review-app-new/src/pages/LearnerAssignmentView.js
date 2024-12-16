import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

function LearnerAssignmentView() {
  const { id } = useParams();
  const [assignment, setAssignment] = useState(null);
  const [githubUrl, setGithubUrl] = useState('');
  const [branch, setBranch] = useState('');
  const [reviewVideoUrl, setReviewVideoUrl] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      window.location.href = '/login';
    }

    const fetchAssignment = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/assignments/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setAssignment(response.data);
        setGithubUrl(response.data.githubUrl);
        setBranch(response.data.branch);
        setReviewVideoUrl(response.data.reviewVideoUrl);
      } catch (err) {
        alert('Error fetching assignment');
      }
    };

    fetchAssignment();
  }, [id]);

  const handleResubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    try {
      await axios.put(
        `http://localhost:8080/api/assignments/${id}`,
        {
          status: 'RESUBMITTED',
          number: assignment.number,
          githubUrl,
          branch,
          reviewVideoUrl,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert('Assignment resubmitted!');
      window.location.href = '/dashboard';
    } catch (err) {
      alert('Error resubmitting assignment');
    }
  };

  const handleDelete = async () => {
    const token = localStorage.getItem('token');
    try {
      await axios.delete(`http://localhost:8080/api/assignments/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Assignment deleted!');
      window.location.href = '/dashboard';
    } catch (err) {
      alert('Error deleting assignment');
    }
  };

  return (
    <div>
      <h1>Assignment Details</h1>
      {assignment ? (
        <>
          <p>Assignment Number: {assignment.number}</p>
          <p>Status: {assignment.status}</p>
          <p>GitHub URL: {assignment.githubUrl}</p>
          <p>Branch: {assignment.branch}</p>
          {assignment.reviewVideoUrl && <p>Review Video URL: {assignment.reviewVideoUrl}</p>}

          <form onSubmit={handleResubmit}>
            <label>
              GitHub URL:
              <input
                type="text"
                value={githubUrl}
                onChange={(e) => setGithubUrl(e.target.value)}
              />
            </label>
            <br />
            <label>
              Branch:
              <input
                type="text"
                value={branch}
                onChange={(e) => setBranch(e.target.value)}
              />
            </label>
            <br />
            <label>
              Review Video URL (Optional):
              <input
                type="text"
                value={reviewVideoUrl}
                onChange={(e) => setReviewVideoUrl(e.target.value)}
              />
            </label>
            <br />
            <button type="submit">Resubmit Assignment</button>
          </form>

          <button onClick={handleDelete}>Delete Assignment</button>
          <br />
          <Link to="/dashboard">Back to Dashboard</Link>
        </>
      ) : (
        <p>Loading assignment...</p>
      )}
    </div>
  );
}

export default LearnerAssignmentView;
