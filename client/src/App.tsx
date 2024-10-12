import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import LoginPage from './pages/login/LoginPage';
import BookingsPage from './pages/bookings/BookingsPage';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<BookingsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
