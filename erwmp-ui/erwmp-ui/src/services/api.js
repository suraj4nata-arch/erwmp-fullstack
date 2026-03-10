const BASE_URL = "http://localhost:9000/api"; // MUST match backend port

function getToken() {
  return localStorage.getItem("token"); // or sessionStorage
}


export async function fetchUsers(page = 0, size = 10) {

  const response = await fetch(
    `${BASE_URL}/users?page=${page}&size=${size}`,
    {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${getToken()}`,
        "Content-Type": "application/json",
      },
    }
  );

  if (!response.ok) {
    let msg = "Failed to fetch users";
    try {
      const err = await response.json();
      msg = err.message || msg;
    } catch (_) {}
    throw new Error(msg);
  }

  return response.json(); // Page<UserEntity>
}

export async function createUser(user) {
  const response = await fetch("http://localhost:9000/api/users", {
    method: "POST",
    headers: {
    "Authorization": `Bearer ${getToken()}`,
    "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Failed to create user");
  }

  return response.json();
}

export async function login(username, password) {
  const response = await fetch(`${BASE_URL}/users/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) {
    throw new Error("Invalid username or password");
  }

  const data = await response.json();

  // 🔐 SAVE TOKEN HERE
  localStorage.setItem("token", data.token);

  return data;
}

export async function updateUser(userId, userData) {
  const token = localStorage.getItem("token");
    console.log("JWT token:", token);
  const response = await fetch(`${BASE_URL}/users/${userId}`, {
    method: "PUT",
    headers: {
    "Authorization": `Bearer ${getToken()}`,
    "Content-Type": "application/json"
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    let msg = "Failed to update user";
    try {
      const err = await response.json();
      msg = err.message || msg;
    } catch (_) {}
    throw new Error(msg);
  }

  // backend returns 204 No Content
  return true;
}

export async function deleteUser(userId) {
  const token = localStorage.getItem("token");

  const response = await fetch(`${BASE_URL}/users/${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    let msg = "Failed to delete user";
    try {
      const err = await response.json();
      msg = err.message || msg;
    } catch (_) {}
    throw new Error(msg);
  }

  return true; // backend usually returns 204 No Content
}

