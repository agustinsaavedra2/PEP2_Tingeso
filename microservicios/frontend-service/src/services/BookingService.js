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
    return httpClient.put(`/api/booking/setPriceAndDuration/${id}`)
}

const setDiscountPeopleNumber = (id) => {
    return httpClient.put(`/api/booking/setDiscountPeopleNumber/${id}`);
}

const setDiscountFreqClient = (id) => {
    return httpClient.put(`/api/booking/discountByFrequentCustomer/${id}`);
}

const setDiscountSpecialDays = (id) => {
    return httpClient.put(`/api/booking/discountBySpecialDays/${id}`);
}

const reportBookingType = (startDate, endDate) => {
    return httpClient.get(`/api/booking/report/revenueByType`, {
        params:{
            startDate: startDate,
            endDate: endDate
        }
    });
}

const reportBookingNumberPeople = (startDate, endDate) => {
    return httpClient.get(`/api/booking/report/revenueByGroupSize`, {
        params: {
            startDate: startDate,
            endDate: endDate
        }
    });
}

export default { createBooking, getBookingById, getAllBookings, 
    updateBooking, deleteBooking, setPriceAndDuration, setDiscountPeopleNumber,
    setDiscountFreqClient, setDiscountSpecialDays, reportBookingType, reportBookingNumberPeople };