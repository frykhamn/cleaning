// src/types.ts

export enum BookingStatus {
  Pending = 'pending',
  Confirmed = 'confirmed',
  Completed = 'completed',
  Cancelled = 'cancelled',
}

export interface Booking {
  id: number;
  customerName: string;
  cleaningType: string;
  cleaner: string;
  date: Date;
  status: BookingStatus;
}