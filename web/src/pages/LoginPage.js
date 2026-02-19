import { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { authService } from '../services/authService';
import Logo from '../components/Logo';
import FormInput from '../components/FormInput';
import Alert from '../components/Alert';
import Button from '../components/Button';

export default function LoginPage({ onNavigate }) {
  const { login } = useAuth();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.email || !form.password) {
      setError('Please fill in all fields.');
      return;
    }

    setLoading(true);
    try {
      const data = await authService.login(form);
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

        <h1 className="card-title">Welcome back</h1>
        <p className="card-sub">Sign in to your account to continue.</p>

        <Alert type="error" message={error} />

        <form onSubmit={handleSubmit}>
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
            placeholder="Your password"
            value={form.password}
            onChange={handleChange}
          />

          <Button type="submit" disabled={loading}>
            {loading ? 'Signing in…' : 'Sign In →'}
          </Button>
        </form>

        <div className="link-row">
          Don't have an account?{' '}
          <span className="link" onClick={() => onNavigate('register')}>
            Register
          </span>
        </div>
      </div>
    </div>
  );
}