import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BookingService from "../services/BookingService";

const AddBooking = () => {
    const [nameBooking, setNameBooking] = useState("");
    const [lapsNumber, setLapsNumber] = useState(0);
    const [maximumTime, setMaximumTime] = useState(0);
    const [bookingDate, setBookingDate] = useState("");
    const [bookingTime, setBookingTime] = useState("");
    const [basePrice, setBasePrice] = useState(0.0);
    const [discountByPeopleNumber, setDiscountByPeopleNumber] = useState(0.0);
    const [discountByFrequentCustomer, setDiscountByFrequentCustomer] = useState(0.0);
    const [discountBySpecialDays, setDiscountBySpecialDays] = useState(0.0);
    const [clients, setClients] = useState([]);

    const navigate = useNavigate();

    const createBooking = (e) => {
        e.preventDefault();
        
        const clientIds = clients.split(',').map(id => ({ id: parseInt(id.trim()) }));

        const booking = {nameBooking, lapsNumber, maximumTime, bookingDate, bookingTime,
            basePrice, discountByPeopleNumber, discountByFrequentCustomer, discountBySpecialDays, clients: clientIds};
        
        BookingService.createBooking(booking).then(
            (response) => {
                console.log("Booking created"),
                console.log(response.data)
                navigate("/allBookings");
            }).catch((error) =>{
                console.log("The booking wasn't created", error);
            });
    }

    return (
        <form>
            <div className="form-group">
                <label htmlFor="formGroupExampleInput">Name Booking</label>
                <input type="text" className="form-control" 
                    id="formGroupExampleInput" placeholder="Ex:Fernando Zampedri" value={nameBooking} onChange={(e) => setNameBooking(e.target.value)}/>
            </div>
            
            <div className="form-group">
                <label htmlFor="formGroupExampleInput2">Laps Number</label>
                <input type="text" className="form-control" id="formGroupExampleInput2" placeholder="Ex: 10, 15, 20" value={lapsNumber} onChange={(e) => setLapsNumber(e.target.value)}/>
            </div>

            <div className="form-group">
                <label htmlFor="formGroupExampleInput3">Maximum Time</label>
                <input type="text" className="form-control" id="formGroupExampleInput3" placeholder="Ex: 10, 20, 30" value={maximumTime} onChange={(e) => setMaximumTime(e.target.value)}/>
            </div>

            <div className="form-group">
                <label htmlFor="formGroupExampleInput4">Booking Date</label>
                <input type="date" className="form-control" id="formGroupExampleInput4" placeholder="Ex: 2025-4-23" value={bookingDate} onChange={(e) => setBookingDate(e.target.value)}/>
            </div>

            <div className="form-group">
                <label htmlFor="formGroupExampleInput5">Booking Time</label>
                <input type="time" className="form-control" id="formGroupExampleInput5" placeholder="Ex: 18:36" value={bookingTime} onChange={(e) => setBookingTime(e.target.value)}/>
            </div>

            <div className="form-group">
                <label htmlFor="formGroupExampleInput6">List Clients</label>
                <input type="text" className="form-control" id="formGroupExampleInput6" placeholder="Ex: 1, 2, 3,..." value={clients} onChange={(e) => setClients(e.target.value)}/>
            </div>

            <button type="submit" style={{margin: "20px"}} className="btn btn-primary" onClick={(e) => createBooking(e)}>Add Booking</button>
        </form>
    );
}

export default AddBooking;