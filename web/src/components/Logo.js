import logo from "../assets/logo.jpg";


export default function Logo() {
return (
    <div className="logo">
      <div className="logo-icon">
        <img
          src={logo}
          alt="Bazaar Logo"
          style={{ width: "70%", height: "70%" }}
        />
      </div>
      <span className="logo-text">Mini App</span>
    </div>
  );
}