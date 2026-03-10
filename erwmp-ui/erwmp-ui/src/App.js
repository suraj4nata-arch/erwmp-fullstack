import UserList from "./pages/UserList";
import CreateUser from "./pages/CreateUser"
import EditUser from "./pages/EditUser";
import LoginUser from "./pages/LoginUser"
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <div>
      <h2>ERWMP Dashboard</h2>

      <Routes>
        <Route path="/" element={<UserList />} />
        <Route path="/users" element={<UserList />} />
        <Route path="/users/create" element={<CreateUser />} />
        <Route path="/users/edit/:id" element={<EditUser />} />
        <Route path="/users/login" element={<LoginUser />} />
      </Routes>
    </div>
  );
}

export default App;
