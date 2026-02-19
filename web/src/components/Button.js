export default function Button({ children, onClick, type = 'button', variant = 'primary', disabled, className = '' }) {
  const variantClass = variant === 'outline' ? 'btn-outline' : variant === 'danger' ? 'btn-danger' : '';

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`btn ${variantClass} ${className}`}
    >
      {children}
    </button>
  );
}