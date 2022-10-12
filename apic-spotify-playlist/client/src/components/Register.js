import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import Error from "./Error";
// import AuthContext from "../context/AuthContext";

function Register() {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errors, setErrors] = useState([]);

    const history = useHistory();

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrors([]);

        if (password != confirmPassword) {
            console.log("Mismatch password");
            return setErrors(["Password does not match Confirm Password"]);
        }

        const response = await fetch("http://localhost:8080/create_account", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                email,
                firstName,
                lastName,
                password
            }),
        });

        if (response.status === 201) {
            console.log("Account Created");
            history.push("/login");
        } else if (response.status === 403) {
            setErrors(["Registration failed."]);
        } else {
            setErrors(["Unknown error."]);
        }
    };

    return (
        <div>
            <h2>Register</h2>
            {errors.map((error, i) => (
                <Error key={i} msg={error} />
            ))}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        onChange={(event) => setUsername(event.target.value)}
                        id="username"
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        onChange={(event) => setEmail(event.target.value)}
                        id="email"
                    />
                </div>
                <div>
                    <label htmlFor="first_name">First Name:</label>
                    <input
                        type="first_name"
                        onChange={(event) => setFirstName(event.target.value)}
                        id="first_name"
                    />
                </div>
                <div>
                    <label htmlFor="last_name">Last Name:</label>
                    <input
                        type="last_name"
                        onChange={(event) => setLastName(event.target.value)}
                        id="last_name"
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        onChange={(event) => setPassword(event.target.value)}
                        id="password"
                    />
                </div>
                <div>
                    <label htmlFor="confirmPassword">Confirm Password:</label>
                    <input
                        type="password"
                        onChange={(event) => setConfirmPassword(event.target.value)}
                        id="confirmPassword"
                    />
                </div>
                <div>
                    <button type="submit">Register</button>
                </div>
            </form>
        </div>
    );
}

export default Register;