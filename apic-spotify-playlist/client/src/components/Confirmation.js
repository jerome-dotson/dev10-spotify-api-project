import { useHistory } from "react-router-dom";
import React from "react";

function Confirmation() {
  const history = useHistory();

  return (
    <p>
      CRUD ✅ {history.location.state ? ` - ${history.location.state.msg}` : ""}
    </p>
  );
}

export default Confirmation;
