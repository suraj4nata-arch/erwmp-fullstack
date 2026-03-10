import { useEffect, useState } from "react";
import { fetchUsers } from "../services/api";
import { updateUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import { deleteUser } from "../services/api";

function UserList() {
  const navigate = useNavigate();

  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [page, setPage] = useState(0);
  const [size] = useState(5);
  const [totalPages, setTotalPages] = useState(0);

  const [search, setSearch] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");
  const [sort, setSort] = useState("createdAt,desc");

  // Debounce search
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(search);
      setPage(0);
    }, 400);

    return () => clearTimeout(timer);
  }, [search]);

  const loadUsers = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await fetchUsers(page, size, debouncedSearch, sort);

      setUsers(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Single fetch effect
  useEffect(() => {
    loadUsers();
  }, [page, debouncedSearch, sort]);

  const toggleSort = (field) => {
    const direction = sort.includes("asc") ? "desc" : "asc";
    setPage(0);
    setSort(`${field},${direction}`);
  };

  // 🧨 DELETE USER
  const handleDelete = async (id) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this user?");
    if (!confirmDelete) return;

    try {
      await deleteUser(id);

      // If deleting last item on page, move back one page
      if (users.length === 1 && page > 0) {
        setPage(page - 1);
      } else {
        loadUsers();
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h3>Users</h3>

      <input
        type="text"
        placeholder="Search users..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      <table border="1" cellPadding="8">
        <thead>
          <tr>
            <th onClick={() => toggleSort("username")}>Username</th>
            <th onClick={() => toggleSort("email")}>Email</th>
            <th onClick={() => toggleSort("createdAt")}>Created</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.createdAt}</td>
              <td>
                <button onClick={() => navigate(`/users/edit/${user.id}`)}>
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(user.id)}
                  style={{ marginLeft: "5px" }}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div style={{ marginTop: "10px" }}>
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>
          Previous
        </button>

        <span style={{ margin: "0 10px" }}>
          Page {page + 1} of {totalPages}
        </span>

        <button
          disabled={page + 1 === totalPages}
          onClick={() => setPage(page + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}
export default UserList

/*
This component:
  ->Calls the backend to fetch users
  ->Stores them in React state
  -> Handles errors


What happens when you press a floor button?

1️⃣ Elevator starts moving
→ setLoading(true)

2️⃣ Clear old warnings
→ setError("")

3️⃣ Elevator goes to requested floor
→ fetchUsers(page, 5)

4️⃣ If it reaches successfully
→ Show people on that floor
→ Save total number of floors

5️⃣ If elevator breaks
→ Show error message

6️⃣ Elevator stops moving (no matter what)
→ setLoading(false)
*/