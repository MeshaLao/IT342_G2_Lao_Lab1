export default function FormInput({ label, name, type = 'text', placeholder, value, onChange }) {
  return (
    <div className="form-group">
      <label>{label}</label>
      <input
        name={name}
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
      />
    </div>
  );
}