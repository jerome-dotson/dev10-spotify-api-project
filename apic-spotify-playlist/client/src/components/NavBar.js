import { useContext } from "react";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import React from "react";

function NavBar() {
  const auth = useContext(AuthContext);
  const history = useHistory();

  return (
    <div className="container">
      <nav className="navbar navbar-expand-lg navbar-light bg-light justify-content-between">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">Home</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to="/playlistsearch" className="nav-link active">Playlist Search</Link>
              </li> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
              {auth.user ? (
                <>
                  <li className="nav-text mt-2 mb-2">
                    Welcome {auth.user.username}!
                  </li>
                  <li className="nav-item">
                    <Link to="/userpage" className="nav-link active">Account</Link>
                  </li>
                  <li className="nav-item ms-2">
                    <button onClick={() => {auth.logout(); history.push("/"); }} className="nav-link active btn btn-warning">Logout</button>
                  </li>
                </>) :
                (<>
                  <li className="nav-item">
                    <Link to="/register" className="nav-link active">Register</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/login" className="nav-link active btn btn-warning">Login</Link>
                  </li>
                </>
                )}
            </ul>
            {/* {auth.user && (
            <div>
              Welcome {auth.user.username}!
              <button onClick={() => auth.logout()}>Logout</button>
            </div>
          )} */}
          </div>
        </div>
      </nav>
    </div>
  );
}

export default NavBar;