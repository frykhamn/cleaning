// src/components/BookingsPage.tsx
import React, { useState } from "react";
import BookingsForm from "./components/BookingForm";
import UpcomingBookings from "./components/UpcomingBookings";
import BookingHistory from "./components/BookingHistory";
import "./BookingsPage.css";
import { Booking, BookingStatus } from "../../types";
import { useBookings } from "../../context/BookingsContext";

const mockCleaners = ["Anna", "Erik", "Maria"];
const customerName = "John Doe";

const BookingsPage: React.FC = () => {
  const {
    bookings,
    history,
    addBooking,
    editBooking,
    removeBooking,
    deleteHistoryItems,
  } = useBookings();

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
          alert(
            `Redigera funktionalitet för bokning ${booking.id} kommer snart.`
          );
        }}
        onDelete={(id) => {
          removeBooking(id);
        }}
      />

      <BookingHistory
        bookings={history}
        onDeleteSelected={deleteHistoryItems}
      />
    </div>
  );
};

export default BookingsPage;
