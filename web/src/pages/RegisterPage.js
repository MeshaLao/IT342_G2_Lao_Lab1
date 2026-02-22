import { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { authService } from '../services/authService';
import Logo from '../components/Logo';
import FormInput from '../components/FormInput';
import Alert from '../components/Alert';
import Button from '../components/Button';

export default function RegisterPage({ onNavigate }) {
  const { login } = useAuth();
  const [form, setForm] = useState({ 
    userName: '', 
    email: '', 
    password: '', 
    confirmPassword: ''  // ← ADDED
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // ← UPDATED validation
    if (!form.userName || !form.email || !form.password || !form.confirmPassword) {
      setError('Please fill in all fields.');
      return;
    }

    if (form.password.length < 6) {
      setError('Password must be at least 6 characters.');
      return;
    }

    // ← NEW: Check if passwords match
    if (form.password !== form.confirmPassword) {
      setError('Passwords do not match!');
      return;
    }

    setLoading(true);
    try {
      // Only send userName, email, password (not confirmPassword)
      const { confirmPassword, ...registerData } = form;
      const data = await authService.register(registerData);
      setSuccess(data.message);
      login(data.token);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <div className="card">
        <Logo />

        <h1 className="card-title">Create an Account</h1>
        <p className="card-sub">Become a member now!</p>

        <Alert type="error" message={error} />
        <Alert type="success" message={success} />

        <form onSubmit={handleSubmit}>
          <FormInput
            label="Username"
            name="userName"
            placeholder="e.g. mesha_lao"
            value={form.userName}
            onChange={handleChange}
          />
          <FormInput
            label="Email Address"
            name="email"
            type="email"
            placeholder="you@campus.edu"
            value={form.email}
            onChange={handleChange}
          />
          <FormInput
            label="Password"
            name="password"
            type="password"
            placeholder="Min. 6 characters"
            value={form.password}
            onChange={handleChange}
          />
          
          <FormInput
            label="Confirm Secret Key"
            name="confirmPassword"
            type="password"
            placeholder="Re-enter password"
            value={form.confirmPassword}
            onChange={handleChange}
          />

          <Button type="submit" disabled={loading}>
            {loading ? 'Preparing Caravan…' : 'Join Caravan'}
          </Button>
        </form>

        <div className="link-row">
          Already have an account?{' '}
          <span className="link" onClick={() => onNavigate('login')}>
            Sign In
          </span>
        </div>
      </div>
    </div>
  );
}