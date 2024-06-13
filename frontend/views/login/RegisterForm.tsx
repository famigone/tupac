import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function RegisterForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [roles, setRoles] = useState(['USER']); // Default role
  const navigate = useNavigate();

  const handleSubmit = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    const response = await fetch('/api/users/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password, name, roles })
    });

    if (response.ok) {
      navigate('/login');
    } else {
      console.error('Failed to register user');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>
          Username:
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        </label>
      </div>
      <div>
        <label>
          Password:
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </label>
      </div>
      <div>
        <label>
          Name:
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        </label>
      </div>
      <div>
        <label>
          Roles:
          <input type="text" value={roles} onChange={(e) => setRoles(e.target.value.split(','))} />
        </label>
      </div>
      <button type="submit">Register</button>
    </form>
  );
}

export default RegisterForm;
