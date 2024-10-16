// src/components/BookingsPage.tsx
import React, { useState } from 'react';
import BookingsForm from './components/BookingForm';
import UpcomingBookings from './components/UpcomingBookings';
import BookingHistory from './components/BookingHistory';
import './BookingsPage.css';
import { Booking, BookingStatus } from '../../types';


const mockCleaners = ['Anna', 'Erik', 'Maria'];

const BookingsPage: React.FC = () => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [history, setHistory] = useState<Booking[]>([]);

  const customerName = 'Kundnamn';
  const addBooking = (newBooking : Booking) => {
    setBookings((prev) => [
      ...prev,
      { ...newBooking },
    ]);
  };

  const editBooking = (editedBooking: Booking) => {
    setBookings((prev) =>
      prev.map((booking) => (booking.id === editedBooking.id ? editedBooking : booking))
    );
  };

  const deleteBooking = (id: number) => {
    setBookings((prev) => prev.filter((booking) => booking.id !== id));
  };

  const moveToHistory = (booking: Booking) => {
    deleteBooking(booking.id);
    setHistory((prev) => [...prev, booking]);
  };

  const deleteHistoryItems = (ids: number[]) => {
    setHistory((prev) => prev.filter((booking) => !ids.includes(booking.id)));
  };

  return (
    <div className="bookings-page">
      <BookingsForm
        cleaners={mockCleaners}
        onSubmit={(values) => {
          addBooking({
            id: Date.now(),
            date: values.date!,
            cleaningType: values.cleaningType,
            customerName: customerName,
            cleaner: values.cleaner,
            status: BookingStatus.Pending,
          });
        }}
      />

      <UpcomingBookings
        bookings={bookings}
        onEdit={(booking) => {
          // Implementera redigeringslogik här
          // Detta kan inkludera att öppna ett modalformulär för att redigera bokningen
          alert(`Redigera funktionalitet för bokning ${booking.id} kommer snart.`);
        }}
        onDelete={(id) => {
          deleteBooking(id);
        }}
      />

      <BookingHistory bookings={history} onDeleteSelected={deleteHistoryItems} />
    </div>
  );
};

export default BookingsPage;
