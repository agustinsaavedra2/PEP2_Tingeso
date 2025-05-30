import React, { useState, useEffect } from 'react';
import RackService from '../services/RackService';
import { useNavigate } from 'react-router-dom';

const SLOT_MINUTES = 20;
const START_HOUR = 10;
const END_HOUR = 22;

// Genera todos los slots de tiempo desde 10:00 hasta 21:40 (el último slot empieza a las 21:40)
const generateTimeSlots = () => {
  const slots = [];
  for(let hour = START_HOUR; hour < END_HOUR; hour++) {
    for(let min = 0; min < 60; min += SLOT_MINUTES) {
      slots.push(`${hour.toString().padStart(2,'0')}:${min.toString().padStart(2,'0')}`);
    }
  }
  return slots;
};

const timeSlots = generateTimeSlots();

const daysOfWeekOrdered = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

const WeeklyRack = () => {
  const [rackData, setRackData] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [startDate, setStartDate] = useState('');
  const navigate = useNavigate();

  const fetchWeeklyRacks = () => {
    if (!startDate) return;
    setLoading(true);
    setError(null);
    RackService.getWeeklyRacks(startDate)
      .then(response => {
        setRackData(response.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Hubo un error al cargar los racks.');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchWeeklyRacks();
  }, [startDate]);

  const handleDateChange = (e) => {
    setStartDate(e.target.value);
  };

  const handleMenuClient = () => {
    navigate('/menuClient');
  };

  return (
    <div style={{ padding: '1rem', fontFamily: 'Arial, sans-serif' }}>
      <h2 style={{ margin: '50px' }}>Weekly Racks</h2>

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

      {!loading && Object.keys(rackData).length > 0 && (
        <div style={{ marginTop: '1.5rem', overflowX: 'auto' }}>
          <h3>Week of {startDate}</h3>

          <div
            style={{
              display: 'grid',
              gridTemplateColumns: `80px repeat(${daysOfWeekOrdered.length}, 1fr)`,
              gap: '1px',
              backgroundColor: '#ccc',
              userSelect: 'none',
            }}
          >
            {/* Primera fila: espacio vacío + días */}
            <div style={{ backgroundColor: '#f0f0f0' }}></div>
            {daysOfWeekOrdered.map(day => (
              <div
                key={day}
                style={{
                  backgroundColor: '#f0f0f0',
                  textAlign: 'center',
                  padding: '8px 4px',
                  fontWeight: 'bold',
                  textTransform: 'capitalize',
                }}
              >
                {day.toLowerCase()}
              </div>
            ))}

            {/* Filas por hora */}
            {timeSlots.map(time => (
              <React.Fragment key={time}>
                {/* Columna izquierda con la hora */}
                <div
                  style={{
                    backgroundColor: '#f9f9f9',
                    padding: '6px 4px',
                    fontSize: '0.85rem',
                    borderRight: '1px solid #ccc',
                    textAlign: 'right',
                    fontFamily: 'monospace',
                  }}
                >
                  {time}
                </div>

                {/* Celdas para cada día en esa hora */}
                {daysOfWeekOrdered.map(day => {
                  const dayData = rackData[day] || {};
                  const booking = dayData[time] || null;

                  return (
                    <div
                      key={`${day}-${time}`}
                      title={booking ? `Client: ${booking.nameBooking}` : 'Available'}
                      style={{
                        backgroundColor: booking ? '#ffe5e5' : '#e5ffe5',
                        padding: '6px 4px',
                        cursor: booking ? 'default' : 'pointer',
                        textAlign: 'center',
                        fontSize: '0.9rem',
                        borderLeft: '1px solid #ccc',
                        borderBottom: '1px solid #ccc',
                        userSelect: 'none',
                      }}
                    >
                      {booking ? booking.nameBooking : ''}
                    </div>
                  );
                })}
              </React.Fragment>
            ))}
          </div>

          <button
            className="btn btn-success mb-4"
            style={{ margin: '30px' }}
            onClick={handleMenuClient}
          >
            Return to menu client
          </button>
        </div>
      )}
    </div>
  );
};

export default WeeklyRack;
