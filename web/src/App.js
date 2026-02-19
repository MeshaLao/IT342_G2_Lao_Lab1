import { useState } from 'react';
import { AuthProvider } from './contexts/AuthContext';
import { useAuth } from './hooks/useAuth';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import './styles/App.css';

function Router() {
  const { token, loading } = useAuth();
  const [page, setPage] = useState('login');

  if (loading) {
    return (
      <div className="page">
        <div className="card">
          <p style={{ color: 'var(--muted)', textAlign: 'center' }}>Loading...</p>
        </div>
      </div>
    );
  }

  if (token) return <DashboardPage />;

  if (page === 'register') return <RegisterPage onNavigate={setPage} />;
  return <LoginPage onNavigate={setPage} />;
}

export default function App() {
  return (
    <AuthProvider>
      <Router />
    </AuthProvider>
  );
}