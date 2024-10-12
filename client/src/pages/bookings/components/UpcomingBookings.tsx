// src/components/UpcomingBookings.tsx
import React from 'react';

interface Booking {
  id: number;
  date: Date;
  cleaningType: string;
  cleaner: string;
}

interface UpcomingBookingsProps {
  bookings: Booking[];
  onEdit: (booking: Booking) => void;
  onDelete: (id: number) => void;
}

const UpcomingBookings: React.FC<UpcomingBookingsProps> = ({ bookings, onEdit, onDelete }) => {
  return (
    <div className="upcoming-bookings">
      <h2>Kommande Bokningar</h2>
      {bookings.length === 0 ? (
        <p>Inga kommande bokningar.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Datum och Tid</th>
              <th>Typ av Städning</th>
              <th>Städare</th>
              <th>Åtgärder</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.id}>
                <td>{booking.date.toLocaleString()}</td>
                <td>{booking.cleaningType}</td>
                <td>{booking.cleaner}</td>
                <td>
                  <button onClick={() => onEdit(booking)}>Redigera</button>
                  <button onClick={() => onDelete(booking.id)}>Ta bort</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UpcomingBookings;
