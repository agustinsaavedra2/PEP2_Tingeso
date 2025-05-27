import React, { useState, useEffect } from 'react';
import RackService from '../services/RackService';
import { useNavigate } from 'react-router-dom';

const WeeklyRack = () => {
  const [rackData, setRackData] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [startDate, setStartDate] = useState('');
  const navigate = useNavigate();

  const fetchWeeklyRacks = () => {
    setLoading(true);
    RackService.getWeeklyRacks(startDate)
      .then((response) => {
        setRackData(response.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Hubo un error al cargar los racks.');
        setLoading(false);
      });
  };

  useEffect(() => {
    if (startDate) {
      fetchWeeklyRacks();
    }
  }, [startDate]);

  const handleDateChange = (e) => {
    setStartDate(e.target.value);
  };

  const handleMenuClient = () => {
    navigate("/menuClient");
  }

  return (
    <div style={{ padding: '1rem', fontFamily: 'Arial, sans-serif' }}>
      <h2 style={{margin: "50px"}}>Weekly Racks</h2>
      <label htmlFor="startDate">Choose your start date:</label>
      <input
        type="date"
        id="startDate"
        value={startDate}
        onChange={handleDateChange}
        style={{ marginLeft: '1rem' }}
      />

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {!loading && rackData && Object.keys(rackData).length > 0 && (
        <div style={{ marginTop: '1.5rem' }}>
          <h3>Week of {startDate}</h3>

          {Object.entries(rackData).map(([day, slots]) => (
            <div key={day} style={{ marginBottom: '2rem', margin: "20px"}}>
              <h4>{day}</h4>
              <table
                style={{
                  width: '100%',
                  borderCollapse: 'collapse',
                  textAlign: 'left',
                }}
              >
                <thead>
                  <tr>
                    <th style={{ border: '1px solid #ccc', padding: '8px' }}>Hora</th>
                    <th style={{ border: '1px solid #ccc', padding: '8px' }}>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  {Object.entries(slots).map(([time, booking], idx) => (
                    <tr key={idx}>
                      <td style={{ border: '1px solid #ccc', padding: '8px' }}>{time}</td>
                      <td
                        style={{
                          border: '1px solid #ccc',
                          padding: '8px',
                          backgroundColor: booking ? '#ffe5e5' : '#e5ffe5',
                        }}
                      >
                        {booking ? `Client: ${booking.nameBooking}` : 'Available'}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ))}
          <button className="btn btn-success mb-4" style={{margin:"30px"}} onClick={handleMenuClient}>
              Return to menu client
          </button>
        </div>
        
      )}

    </div>
  );
};

export default WeeklyRack;
