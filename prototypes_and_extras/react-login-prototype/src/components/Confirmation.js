import { useHistory } from "react-router-dom";

function Confirmation() {
  const history = useHistory();

  return (
    <p>
      CRUD ✅ {history.location.state ? ` - ${history.location.state.msg}` : ""}
    </p>
  );
}

export default Confirmation;
