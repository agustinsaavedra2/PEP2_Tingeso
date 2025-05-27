import React, { useState } from "react";
import BookingService from "../services/BookingService"; 
import { useNavigate } from "react-router-dom";

const ReportBookingNumPeople = () => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [revenueReport, setRevenueReport] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleGenerateReport = () => {
        if (!startDate || !endDate) {
            alert("Please select both start and end dates");
            return;
        }

        setLoading(true);
        
        BookingService.reportBookingNumberPeople(startDate, endDate).then((response) =>{
            setRevenueReport(response.data);
            setLoading(false);
        }).catch((error) => {
            console.log("Error generating revenue report", error);
            setLoading(false);
        })
    };

    const handleMenuClient = () => {
        navigate("/menuClient");
    }

    return (
        <div>
            <h2>Reporte por Número de Clientes</h2>
            <div>
                <label htmlFor="startDate" style={{margin: "20px"}}>Start Date:</label>
                <input
                    type="date"
                    id="startDate"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="endDate" style={{margin: "20px"}}>End Date:</label>
                <input
                    type="date"
                    id="endDate"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                />
            </div>
            <button className="btn btn-warning" onClick={handleGenerateReport} disabled={loading} style={{margin: "20px"}} >
                {loading ? "Generating..." : "Generate Report"}
            </button>

            {revenueReport && revenueReport.length > 0 && (
                <div>
                    <h3 style={{margin: "20px"}}>Report Summary</h3>
                    <table className="table table-bordered" style={{margin: "20px"}}>
                        <thead >
                            <tr>
                                <th>Group Range</th>
                                <th>Total Bookings</th>
                                <th>Total Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            {revenueReport.map((item, index) => (
                                <tr key={index}>
                                    <td>{item.groupRange}</td>
                                    <td>{item.totalBookings}</td>
                                    <td>${item.totalRevenue.toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <button className="btn btn-success mb-4" style={{margin:"30px"}} onClick={handleMenuClient}>
                        Return to menu client
                    </button>
                </div>
            )}

        </div>
    );
};

export default ReportBookingNumPeople;
