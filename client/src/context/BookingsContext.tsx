// src/context/BookingsContext.tsx
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { Booking, BookingStatus } from '../types';
import { fetchBookings, createBooking, updateBooking, deleteBooking } from "../api/bookings";

interface BookingsContextProps {
  bookings: Booking[];
  history: Booking[];
  addBooking: (newBooking: Booking) => Promise<void>;
  editBooking: (editedBooking: Booking) => Promise<void>;
  removeBooking: (id: number) => Promise<void>;
  moveToHistory: (booking: Booking) => void;
  deleteHistoryItems: (ids: number[]) => void;
}

const BookingsContext = createContext<BookingsContextProps | undefined>(undefined);

export const useBookings = () => {
  const context = useContext(BookingsContext);
  if (!context) {
    throw new Error('useBookings must be used within a BookingsProvider');
  }
  return context;
};

export const BookingsProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [history, setHistory] = useState<Booking[]>([]);

  useEffect(() => {
    const loadBookings = async () => {
      const fetchedBookings = await fetchBookings();
      setBookings(fetchedBookings);
    };

    loadBookings();
  }, []);

  const addBooking = async (newBooking: Booking) => {
    const createdBooking = await createBooking(newBooking);
    setBookings((prev) => [...prev, createdBooking]);
  };

  const editBooking = async (editedBooking: Booking) => {
    const updatedBooking = await updateBooking(editedBooking);
    setBookings((prev) =>
      prev.map((booking) => (booking.id === updatedBooking.id ? updatedBooking : booking))
    );
  };

  const removeBooking = async (id: number) => {
    await deleteBooking(id);
    setBookings((prev) => prev.filter((booking) => booking.id !== id));
  };

  const moveToHistory = (booking: Booking) => {
    removeBooking(booking.id);
    setHistory((prev) => [...prev, booking]);
  };

  const deleteHistoryItems = (ids: number[]) => {
    setHistory((prev) => prev.filter((booking) => !ids.includes(booking.id)));
  };

  return (
    <BookingsContext.Provider
      value={{
        bookings,
        history,
        addBooking,
        editBooking,
        removeBooking,
        moveToHistory,
        deleteHistoryItems,
      }}
    >
      {children}
    </BookingsContext.Provider>
  );
};