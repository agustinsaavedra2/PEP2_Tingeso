import React, { useState } from 'react';
import VoucherService from '../services/VoucherService';
import { useNavigate } from 'react-router-dom';

const GenerateClientVouchers = () => {
  const [bookingId, setBookingId] = useState('');
  const [vouchers, setVouchers] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleGenerate = (e) => {
    e.preventDefault();
    setError('');
    setVouchers([]);

    VoucherService.generateClientVouchers(bookingId)
      .then((response) => {
        setVouchers(response.data);
      })
      .catch((err) => {
        console.error("Error al generar vouchers:", err);
        setError("No se pudieron generar los vouchers.");
      });
  };

  const handleMenuClient = () => {
    navigate("/menuClient");
  }

  return (
    <div style={{ padding: '50px', fontFamily: 'Arial' }}>
      <h2 style={{ padding: '50px' }}>Generar Vouchers</h2>
      <form onSubmit={handleGenerate} style={{ marginBottom: '20px' }}>
        <input
          type="text"
          value={bookingId}
          onChange={(e) => setBookingId(e.target.value)}
          placeholder="Ingrese Booking ID"
          required
          style={{ padding: '8px', marginRight: '10px' }}
        />
        <button type="submit" className="btn btn-danger">Generar</button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {vouchers.length > 0 && (
        <div>
          <h3>Vouchers Generados</h3>
          {vouchers.map((v, i) => (
            <div
              key={i}
              style={{
                backgroundColor: '#d4edda',
                border: '1px solid #c3e6cb',
                borderRadius: '10px',
                boxShadow: '0px 2px 8px rgba(0, 0, 0, 0.1)',
                padding: '15px',
                marginBottom: '15px',
              }}
            >
              <p><strong>ID:</strong> {v.id}</p>
              <p><strong>Booking ID:</strong> {v.bookingId}</p>
              <p><strong>Fecha de reserva:</strong> {v.bookingDate}</p>
              <p><strong>Hora de reserva:</strong> {v.bookingTime}</p>
              <p><strong>Número de vueltas:</strong> {v.numberLaps}</p>
              <p><strong>Tiempo máximo:</strong> {v.maximumTime}</p>
              <p><strong>Personas:</strong> {v.numberPeople}</p>
              <p><strong>Nombre de la reserva:</strong> {v.bookingName}</p>
              <p><strong>Nombre del cliente:</strong> {v.clientName}</p>
              <p><strong>Precio base:</strong> ${v.base_price}</p>
              <p><strong>Descuento por personas:</strong> ${v.discountNumberPeople}</p>
              <p><strong>Descuento cliente frecuente:</strong> ${v.discountFrequentCustomer}</p>
              <p><strong>Descuento por días especiales:</strong> ${v.discountSpecialDays}</p>
              <p><strong>Precio final:</strong> ${v.final_price}</p>
              <p><strong>IVA:</strong> ${v.iva}</p>
              <p><strong>Total a pagar:</strong> ${v.total_price}</p>
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

export default GenerateClientVouchers;
