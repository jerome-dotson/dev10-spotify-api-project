import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import React from "react";

function NavBar() {
  const auth = useContext(AuthContext);

  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/playlists">Playlists</Link>
        </li>
        {auth.user ? (
          <li>
            <Link to="/add">Add</Link>
          </li>) :
          (<>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
          </>
          )}
      </ul>
      {auth.user && (
        <div>
          Welcome {auth.user.username}!
          <button onClick={() => auth.logout()}>Logout</button>
        </div>
      )}
    </nav>
  );
}

export default NavBar;