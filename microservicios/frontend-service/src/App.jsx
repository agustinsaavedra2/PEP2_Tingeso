import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "../src/components/Home"
import Navbar from "../src/components/Navbar"
import AddClient from './components/AddClient'
import LoginClient from './components/LoginClient'
import AllBookings from './components/AllBookings'
import AddBooking from './components/AddBooking'
import SetPriceDuration from './components/SetPriceDuration'
import DiscountNumPeople from './components/DiscountNumPeople'
import DiscountFreqClient from './components/DiscountFreqClient'
import DiscountSpecialDays from './components/DiscountSpecialDays'
import ReportBookingType from './components/ReportBookingType'
import ReportBookingNumPeople from './components/ReportBookingNumPeople'
import WeeklyRack from './components/WeeklyRack'
import GenerateClientVouchers from './components/GenerateClientVouchers'
import './App.css'
import MenuClient from './components/MenuClient'

function App() {

  return (
    <Router>
      <div className="container"></div>
      <Navbar></Navbar>
        <Routes>
          <Route path="/home" element={<Home/>}/>
          <Route path="/addClient" element={<AddClient/>}/>
          <Route path="/loginClient" element={<LoginClient/>}/>
          <Route path="/menuClient" element={<MenuClient/>}/>
          <Route path="/allBookings" element={<AllBookings/>}/>
          <Route path="/addBooking" element={<AddBooking/>} />
          <Route path="/setPriceAndDuration" element={<SetPriceDuration/>} />
          <Route path="/setDiscountPeopleNumber" element={<DiscountNumPeople/>} />
          <Route path="/setDiscountFreqClient" element={<DiscountFreqClient/>} />
          <Route path="/setDiscountSpecialDays" element={<DiscountSpecialDays/>}/>
          <Route path="/reportBookingType" element={<ReportBookingType/>} />
          <Route path="/reportBookingNumPeople" element={<ReportBookingNumPeople/>} />
          <Route path="/getWeeklyRack" element={<WeeklyRack/>} />
          <Route path="/generateClientVouchers" element={<GenerateClientVouchers/>} />
        </Routes>
    </Router>
  )
}

export default App
