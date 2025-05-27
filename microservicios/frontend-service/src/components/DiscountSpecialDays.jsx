import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import BookingService from "../services/BookingService"

const DiscountSpecialDays = () => {
    const {id} = useParams();
    const [idInput, setIdInput] = useState(id || "");
    const [clientsDiscounts, setClientsDiscounts] = useState([]);
    const [showPrices, setShowPrices] = useState(false);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        setIdInput(e.target.value);
    }

    const discSpecialDays = () => {
        BookingService.setDiscountSpecialDays(idInput).then((response) => {
            setClientsDiscounts(response.data);
            setShowPrices(true);
       }).catch((error) => {
            console.log("Error fetching discount by special days.", error);  
       })
    }

    const handleMenuClient = () => {
        navigate("/menuClient");
    }

    return(
        <div className="container mt-4" style={{margin: "50px"}}>
            <h2>Set Discounts Special Days</h2>

            <div>
                <label htmlFor="idBooking">Enter the booking ID:</label>
                <input 
                    type="text"
                    id="idBooking"
                    className="form-control"
                    value={idInput}
                    onChange={handleInputChange}/>
            </div>

            <div>
                <button className="btn btn-primary mb-4" onClick={discSpecialDays} style={{margin:"20px"}}>
                    Discount for Special Days
                </button>
            </div>

            {showPrices && clientsDiscounts.length > 0 ? (
                <div>
                    <h3 className="mb-3">Discount By Special Days for Booking #{idInput}</h3>
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
                showPrices && <p>Data is not available</p>
            )}
        
        </div>
    );
}

export default DiscountSpecialDays;