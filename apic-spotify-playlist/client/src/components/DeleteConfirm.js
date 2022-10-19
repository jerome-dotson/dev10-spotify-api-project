import { useEffect, useState, useContext } from "react";
import { useParams, useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";



function DeleteConfirm() {

    const { id } = useParams();

    const [playlist, setPlaylist] = useState(null);

    const [error, setError] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext);

    // useEffect(() => {
    //     fetch("http://localhost:8080/api/playlist/" + id, {
    //         headers: {
    //             Authorization: `Bearer ${auth.user.token}`
    //         }
    //     })
    //         .then(
    //             response => {
    //                 if (response.status === 201) {
    //                     return response.json();
    //                 } else {
    //                     return response.json();
    //                 }
    //             }
    //         )
    //         .then(data => {
    //             if (data.playlistId == id) {
    //                 setError([]);
    //                 setPlaylist(data);
    //             } else {
    //                 setError(data)
    //             }
    //         })
    //         .catch(err => console.log(err));
    // },
    //     []);

    function deleteClicked(evt) {
        evt.preventDefault();

        fetch("http://localhost:8080/api/playlist/" + id, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${auth.user.token}`,
            },
        })
            .then(response => {
                if (response.status === 204) {
                    history.push("/");
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
            {/* {auth.user.userId == playlist.appUserId ? */}
            <div className="card">
                <div className="card-header">
                    <h5>Are you sure you want to delete?</h5>
                </div>
                <div className="card-body">
                    <button type="button" className="btn btn-danger m-1" onClick={deleteClicked}>Confirm</button>
                    <button type="button" className="btn btn-warning m-1" onClick={handleCancel}>Cancel</button>
                    
                </div>
            </div>
            {/* // : "You do not have permission to delete"} */}
        </div>
    );

}

export default DeleteConfirm;