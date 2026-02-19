import { useAuth } from '../hooks/useAuth';
import Button from '../components/Button';

export default function DashboardPage() {
  const { user, logout } = useAuth();

  if (!user) {
    return (
      <div className="page">
        <div className="card">
          <p style={{ color: 'var(--muted)', textAlign: 'center' }}>Loading profile…</p>
        </div>
      </div>
    );
  }

  const joinDate = user.createdAt
    ? new Date(user.createdAt).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      })
    : '—';

  return (
    <div className="dashboard">
      {/* Navbar */}
      <nav className="navbar">
        <div className="nav-logo">
          <div className="nav-logo-icon">🔐</div>
          <span className="nav-logo-text">userAuth</span>
        </div>
        <div className="nav-right">
          <span className="nav-greeting">
            Hi, <span>{user.userName}</span>
          </span>
          <Button variant="outline" className="btn-sm" onClick={logout}>
            Logout
          </Button>
        </div>
      </nav>

      {/* Body */}
      <div className="dashboard-body">
        <div className="badge">
          <div className="badge-dot" />
          Authenticated Session Active
        </div>

        <h1 className="welcome-heading">Hello, {user.userName}! 👋</h1>
        <p className="welcome-sub">Here's your profile information from the database.</p>

        <div className="grid">
          <div className="info-card">
            <div className="info-card-icon">👤</div>
            <div className="info-card-label">Username</div>
            <div className="info-card-value">{user.userName}</div>
          </div>
          <div className="info-card">
            <div className="info-card-icon">📧</div>
            <div className="info-card-label">Email Address</div>
            <div className="info-card-value">{user.email}</div>
          </div>
          <div className="info-card">
            <div className="info-card-icon">🆔</div>
            <div className="info-card-label">User ID</div>
            <div className="info-card-value">#{user.userID}</div>
          </div>
          <div className="info-card">
            <div className="info-card-icon">📅</div>
            <div className="info-card-label">Member Since</div>
            <div className="info-card-value">{joinDate}</div>
          </div>
        </div>

        <hr className="divider" />

        <p className="section-title">Account Actions</p>
        <div className="logout-area">
          <div className="logout-text">
            <h3>Sign out</h3>
            <p>You'll need to sign in again to access your dashboard.</p>
          </div>
          <Button variant="danger" className="btn-sm" onClick={logout}>
            Logout →
          </Button>
        </div>
      </div>
    </div>
  );
}