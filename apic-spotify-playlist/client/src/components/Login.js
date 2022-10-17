
import React, { useContext, useState } from "react";
import { useHistory } from "react-router-dom";
import MessageDisplay from "./MessageDisplay";
import AuthContext from "../context/AuthContext";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch("http://localhost:8080/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        // Authorization: `Bearer ${auth.user.token}`,
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });
  
    if (response.status === 200) {
      const { jwt_token } = await response.json();
      console.log(jwt_token);
      auth.login(jwt_token);
      history.push("/");
    } else if (response.status === 403) {
      setError(["Login failed."]);
    } else {
      setError(["Unknown error."]);
    }
  };

  return (
    <div className="card m-5" style={{width: '18rem'}}>
      <h2 className="card-header text-center">Login</h2>
      {/* {errors.map((error, i) => (
        <Error key={i} msg={error} />
      ))} */}
      <div>
                {error.length > 0 ? <MessageDisplay error={error} /> : null}
            </div>
      <form onSubmit={handleSubmit} className="card-body">
        <div className="card-text form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            className="form-control"
            placeholder="Enter Username"
            onChange={(event) => setUsername(event.target.value)}
            id="username"
          />
        </div>
        <div className="card-text form-group mt-2 mb-1">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter Password"
            onChange={(event) => setPassword(event.target.value)}
            id="password"
          />
        </div>
        <div>
          <button type="submit" className="btn btn-success mt-2">Login</button>
        </div>
      </form>
    </div>
  );
}