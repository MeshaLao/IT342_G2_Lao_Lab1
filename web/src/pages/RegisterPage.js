import { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { authService } from '../services/authService';
import Logo from '../components/Logo';
import FormInput from '../components/FormInput';
import Alert from '../components/Alert';
import Button from '../components/Button';

export default function RegisterPage({ onNavigate }) {
  const { login } = useAuth();
  const [form, setForm] = useState({ userName: '', email: '', password: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.userName || !form.email || !form.password) {
      setError('Please fill in all fields.');
      return;
    }

    if (form.password.length < 6) {
      setError('Password must be at least 6 characters.');
      return;
    }

    setLoading(true);
    try {
      const data = await authService.register(form);
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

        <h1 className="card-title">Create account</h1>
        <p className="card-sub">Join us — it only takes a minute.</p>

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
            label="Email"
            name="email"
            type="email"
            placeholder="you@email.com"
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

          <Button type="submit" disabled={loading}>
            {loading ? 'Creating account…' : 'Create Account →'}
          </Button>
        </form>

        <div className="link-row">
          Already have an account?{' '}
          <span className="link" onClick={() => onNavigate('login')}>
            Sign in
          </span>
        </div>
      </div>
    </div>
  );
}