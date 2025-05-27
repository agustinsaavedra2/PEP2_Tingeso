import {useState} from "react"
import {useNavigate} from "react-router-dom";
import ClientService from "../services/ClientService";

export default function LoginClient(){
    const [email, setEmail] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        ClientService.getClientByEmail(email).then((response) => {
            console.log(response.data)
            navigate("/menuClient")
        }).catch((error) => {
            console.log("Client can't login", error);
        })
    }

    return(
        <form onSubmit={handleSubmit}>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="ExampleInputEmail1" style={{margin: "20px"}}>Email address</label>
                <input type="email" className="form-control" id="ExampleInputEmail1" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)}/>
            </div>
            <button type="submit" style={{margin: "20px"}} className="btn btn-success">Login Client</button>
        </form>
    )
}