import { useState, useContext } from "react";
import { useParams, useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";



function DeleteConfirm() {

    const { id } = useParams();

    const [error, setError] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext);

    function deleteClicked(evt) {
        evt.preventDefault();

        fetch("http://localhost:8080/api/playlist/track/" + toDelete.trackId, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${auth.user.token}`,
            },
        })
            .then(response => {
                if (response.status === 204) {
                    history.push("/playlist/" + id);
                } else {
                    setError(response);
                }
            })
    }

    function handleCancel(evt) {
        evt.preventDefault();

        history.push("/playlist/" + id);
    }

    return (
        <div className="container text-center">
            <div className="card">
                <div className="card-header">
                    <h5>Are you sure you want to delete?</h5>
                </div>
                <div className="card-body">
                    <button type="button" className="btn btn-danger m-1" onClick={deleteClicked}>Confirm</button>
                    <button type="button" className="btn btn-warning m-1" onClick={handleCancel}>Cancel</button>
                </div>
            </div>
        </div>
    );

}

export default DeleteConfirm;