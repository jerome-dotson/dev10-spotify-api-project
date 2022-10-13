import React from "react";

function MessageDisplay(errors) {

    const error = (
        <ul className="list-unstyled">
            {errors.error.map( error => 
            <li key={error}>
                {error}
            </li>
            )}
        </ul>
    );

    return (
        <div className="card msgDisp text-center">
            <div className="card-header">
                <h5>Error:</h5>
            </div>
            <div className="card-body">
                <div className="card-text">
                    {error}
                </div>
            </div>
        </div>
    );

}

export default MessageDisplay;