// src/components/BookingHistory.tsx
import React, { useState } from 'react';

interface Booking {
  id: number;
  date: Date;
  cleaningType: string;
  cleaner: string;
}

interface BookingHistoryProps {
  bookings: Booking[];
  onDeleteSelected: (ids: number[]) => void;
}

const BookingHistory: React.FC<BookingHistoryProps> = ({ bookings, onDeleteSelected }) => {
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const handleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((selectedId) => selectedId !== id) : [...prev, id]
    );
  };

  const handleDeleteSelected = () => {
    onDeleteSelected(selectedIds);
    setSelectedIds([]);
  };

  return (
    <div className="booking-history">
      <h2>Historik över Utförda Städningar</h2>
      {bookings.length === 0 ? (
        <p>Ingen historik att visa.</p>
      ) : (
        <>
          <table>
            <thead>
              <tr>
                <th>Välj</th>
                <th>Datum och Tid</th>
                <th>Typ av Städning</th>
                <th>Städare</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((booking) => (
                <tr key={booking.id}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedIds.includes(booking.id)}
                      onChange={() => handleSelect(booking.id)}
                    />
                  </td>
                  <td>{booking.date.toLocaleString()}</td>
                  <td>{booking.cleaningType}</td>
                  <td>{booking.cleaner}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <button onClick={handleDeleteSelected} disabled={selectedIds.length === 0}>
            Ta bort valda
          </button>
        </>
      )}
    </div>
  );
};

export default BookingHistory;
