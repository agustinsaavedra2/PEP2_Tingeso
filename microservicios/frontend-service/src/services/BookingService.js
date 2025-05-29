import httpClient from "../http-common"

const createBooking = (data) => {
    return httpClient.post("/api/booking/", data);
}

const getBookingById = (id) => {
    return httpClient.get(`/api/booking/${id}`);
}

const getAllBookings = () => {
    return httpClient.get("/api/booking/");
}

const updateBooking = (data) => {
    return httpClient.put("/api/booking/", data);
}

const deleteBooking = (id) => {
    return httpClient.delete(`/api/booking/${id}`);
}

const setPriceAndDuration = (id) => {
    return httpClient.put(`/api/booking/setPriceDuration/${id}`)
}

export default { createBooking, getBookingById, getAllBookings, 
    updateBooking, deleteBooking, setPriceAndDuration };