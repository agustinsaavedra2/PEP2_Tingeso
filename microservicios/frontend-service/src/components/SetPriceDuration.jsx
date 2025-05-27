import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import BookingService from "../services/BookingService";

const SetPriceDuration = () => {
    const { id } = useParams(); 
    const [idInput, setIdInput] = useState(id || "");
    const [clientPrices, setClientPrices] = useState([]);
    const [showPrices, setShowPrices] = useState(false);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        setIdInput(e.target.value);
    };

    const handleSetPriceDuration = () => {
        BookingService.setPriceAndDuration(idInput)
            .then(response => {
                setClientPrices(response.data);
                setShowPrices(true); 
            })
            .catch(error => {
                console.error("Error fetching client prices", error);
            });
    }

    const handleMenuClient = () => {
        navigate("/menuClient");
    }    

    return (
        <div className="container mt-4" >
            <h2 className="mb-4" style={{margin: "50px"}}>Set Base Prices</h2>

            <div>
                <label htmlFor="bookingId">Enter the booking ID: </label>
                <input 
                    type="text" 
                    id="bookingId"
                    className="form-control"
                    value={idInput}
                    onChange={handleInputChange}/>
            </div>
            
            <div>
                <button className="btn btn-primary mb-4" onClick={handleSetPriceDuration} style={{margin:"20px"}}>
                    Set Price and Duration
                </button>
            </div>

            {showPrices && clientPrices.length > 0 ? (
                <div>
                    <h3 className="mb-3">Client Base Prices for Booking #{idInput}</h3>
                    <ul className="list-group">
                        {clientPrices.map((pair, index) => (
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
                showPrices && <p>No data available.</p>
            )}
            
        </div>
    );
};

export default SetPriceDuration;