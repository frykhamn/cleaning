// src/api/bookings.ts
import axios from 'axios';
import { Booking } from '../types';

const API_URL = "http://localhost:8081";

export const fetchBookings = async (): Promise<Booking[]> => {
  const response = await axios.get(`${API_URL}/bookings`);
  return response.data;
};

export const createBooking = async (booking: Booking): Promise<Booking> => {
  const response = await axios.post(`${API_URL}/bookings`, booking);
  return response.data;
};

export const updateBooking = async (booking: Booking): Promise<Booking> => {
  const response = await axios.put(`${API_URL}/bookings/${booking.id}`, booking);
  return response.data;
};

export const deleteBooking = async (id: number): Promise<void> => {
  await axios.delete(`${API_URL}/bookings/${id}`);
};