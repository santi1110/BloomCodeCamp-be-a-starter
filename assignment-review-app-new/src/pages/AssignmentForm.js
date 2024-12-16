import React, { useState } from 'react';
import axios from 'axios';

function AssignmentForm({ onSubmit }) {
  const [githubUrl, setGithubUrl] = useState('');
  const [branch, setBranch] = useState('');
  const [assignmentNumber, setAssignmentNumber] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('token');
      const response = await axios.post(
        'http://localhost:8080/api/assignments',
        { githubUrl, branch, assignmentNumber },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      alert('Assignment submitted successfully!');
      onSubmit();
    } catch (err) {
      console.error(err);
      alert('Error submitting assignment');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
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
        Assignment Number:
        <input
          type="number"
          value={assignmentNumber}
          onChange={(e) => setAssignmentNumber(e.target.value)}
        />
      </label>
      <br />
      <button type="submit">Submit Assignment</button>
    </form>
  );
}

export default AssignmentForm;
