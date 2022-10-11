import { useHistory } from "react-router-dom";
import React from "react";

function Error({ msg }) {
  const history = useHistory();

  return (
    <p>
      🙅🏾‍♂️ Error{" "}
      {history.location.state ? ` - ${history.location.state.msg}` : ""}
      {msg}
    </p>
  );
}

// function Error() {

//   return (
//     <></>
//   );
// }

export default Error;
