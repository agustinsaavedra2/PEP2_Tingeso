import {useState} from "react"
import {useNavigate, useParams} from "react-router-dom"
import BookingService from "../services/BookingService"

const DiscountFreqClient = () => {
    const {id} = useParams();
    const [idInput, setIdInput] = useState(id || "");
    const [clientsDiscounts, setClientsDiscounts] = useState([]);
    const [showPrices, setShowPrices] = useState(false);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        setIdInput(e.target.value);
    }

    const setDiscFreqClient = () => {
        BookingService.setDiscountFreqClient(idInput).then((response) => {
            setClientsDiscounts(response.data);
            setShowPrices(true);
        }).catch((error) =>{
            console.log("Error fetching discount by frequent client.", error);
        });
    }

    const handleMenuClient = () => {
        navigate("/menuClient");
    }

    return(
        <div className="container mt-4" style={{margin: "50px"}}>
            <h2 className="mb-4">Set Discounts Frequent Clients</h2>

            <div>
                <label htmlFor="bookingId">Enter the Booking ID: </label>
                <input
                    type="text"
                    id="bookingId"
                    className="form-control"
                    value={idInput}
                    onChange={handleInputChange}/>
            </div>

            <div>
                <button className="btn btn-primary mb-4" onClick={setDiscFreqClient} style={{margin:"20px"}}>Discount for Frequent Client</button>
            </div>

            {showPrices && clientsDiscounts.length > 0 ? (
                <div>
                    <h3>Discounts for Frequent Clients for Booking #{idInput}</h3>
                    <ul className="list-group">
                        {clientsDiscounts.map((pair, index) => (
                            <li key={index} className="list-group-item">
                                {pair.first} - ${pair.second.toLocaleString()}    
                            </li>
                        ))}
                    </ul>
                    <button className="btn btn-success mb-4" style={{margin:"30px"}} onClick={handleMenuClient}>
                        Return to menu client
                    </button>
                </div>
                
            ) : (
                showPrices && <p>No data available</p>
            )}
        </div>
    );
}

export default DiscountFreqClient;