import httpClient from "../http-common"

const createVoucher = (data) => {
    return httpClient.post("/api/voucher/", data);
}

const getVoucherById = (id) => {
    return httpClient.get(`/api/voucher/id/${id}`);
}

const getAllVouchers = () => {
    return httpClient.get("/api/voucher/");
}

const updateVoucher = (data) => {
    return httpClient.put("/api/voucher/", data);
}

const deleteVoucher = (id) => {
    return httpClient.delete(`/api/voucher/${id}`);
}

const getAllBookings = () => {
    return httpClient.get("/api/voucher/bookings");
}

const generateClientVouchers = (bookingId) => {
    return httpClient.get(`/api/voucher/generateClientVouchers/${bookingId}`);
}

export default {createVoucher, getVoucherById, getAllVouchers, updateVoucher, deleteVoucher,
    getAllBookings, generateClientVouchers
}