import { useState } from "react"
import { useNavigate } from "react-router-dom"
import ClientService from "../services/ClientService"

const AddClient = () => {
    const [name, setName] = useState("")
    const [rut, setRut] = useState("")
    const [email, setEmail] = useState("")
    const [birthDate, setBirthDate] = useState("")
    const [numberOfVisits, setNumberOfVisits] = useState(0);
    const [bookingList, setBookingList] = useState([]);
    const navigate = useNavigate();

    const create = (e) => {
        e.preventDefault();

        const client = {name, rut, email, birthDate, numberOfVisits, bookingList}

        ClientService.createClient(client)
            .then((response) => {
                console.log("A new client has been created.");
                console.log(response.data);
                navigate("/home");
            })
            .catch((error) => {
                console.log("There was an error creating the client:", error);
            });
    }

    return(
        <form onSubmit={create}>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="formGroupExampleInput">Name</label>
                <input type="text" className="form-control" id="formGroupExampleInput" placeholder="Enter name" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="formGroupExampleInput2">Rut</label>
                <input type="text" className="form-control" id="formGroupExampleInput2" placeholder="Example: 12.345.678-9" value={rut} onChange={(e) => setRut(e.target.value)}/>
            </div>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="ExampleInputEmail1">Email address</label>
                <input type="email" className="form-control" id="ExampleInputEmail1" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)}/>
            </div>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="formGroupExampleInput3">Birth of Date</label>
                <input type="text" className="form-control" id="formGroupExampleInput3" placeholder="Example: 2005-01-12" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} />
            </div>
            <div className="form-group" style={{margin: "20px"}}>
                <label htmlFor="formGroupExampleInput4">Number of Visits</label>
                <input type="text" className="form-control" id="formGroupExampleInput4" placeholder="Example: 0, 1" value={numberOfVisits} onChange={(e) => setNumberOfVisits(e.target.value)}/>
            </div>

            <button type="submit" style={{margin: "20px"}} className="btn btn-primary">Add Client</button>
        </form>
                    
    )

};

export default AddClient;