import { useNavigate } from "react-router-dom";

const MenuClient = () => {
    const navigate = useNavigate();

    return (
        <div className="container mt-5 text-center">
            <h2 className="mb-4">Client Menu</h2>

            <div className="d-grid gap-3 col-6 mx-auto">
                <button className="btn btn-primary" onClick={() => navigate("/allBookings")}>
                    View All Bookings
                </button>
                <button className="btn btn-success" onClick={() => navigate("/addBooking")}>
                    Add Booking
                </button>
                <button className="btn btn-warning" onClick={() => navigate("/setPriceAndDuration")}>
                    Set Price and Duration
                </button>
                <button className="btn btn-info" onClick={() => navigate("/setDiscountPeopleNumber")}>
                    Discount by Number of People
                </button>
                <button className="btn btn-secondary" onClick={() => navigate("/setDiscountFreqClient")}>
                    Discount for Frequent Clients
                </button>
                <button className="btn btn-danger" onClick={() => navigate("/setDiscountSpecialDays")}>
                    Discount for Special Days
                </button>
                <button className="btn btn-outline-danger" onClick={() => navigate("/generateClientVouchers")}>
                    Generate Client Vouchers
                </button>
                <button className="btn btn-dark" onClick={() => navigate("/reportBookingType")}>
                    Report by Booking Type
                </button>
                <button className="btn btn-outline-primary" onClick={() => navigate("/reportBookingNumPeople")}>
                    Report by Number of People
                </button>
                <button className="btn btn-outline-success" onClick={() => navigate("/getWeeklyRack")}>
                    Weekly Rack
                </button>
            </div>
        </div>
    );
};

export default MenuClient;
