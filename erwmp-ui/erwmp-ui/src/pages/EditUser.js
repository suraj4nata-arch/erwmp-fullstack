import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { updateUser } from "../services/api";

function EditUser() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    username: "",
    email: ""
  });

  const [error, setError] = useState("");

  // OPTIONAL: preload user from list / backend
  useEffect(() => {
    // If you already have user data, skip this
  }, []);

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await updateUser(id, user);
      navigate("/users"); // go back to list
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Edit User</h3>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <input
        name="username"
        value={user.username}
        onChange={handleChange}
        placeholder="Username"
        required
      />

      <input
        name="email"
        value={user.email}
        onChange={handleChange}
        placeholder="Email"
        required
      />

      <button type="submit">Update</button>
    </form>
  );
}

export default EditUser;
