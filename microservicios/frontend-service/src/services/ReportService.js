import httpClient from "../http-common";

const reportBookingType = (startDate, endDate) => {
    return httpClient.get(`/api/report/revenueByType`, {
        params:{
            startDate: startDate,
            endDate: endDate
        }
    });
}

const reportBookingNumberPeople = (startDate, endDate) => {
    return httpClient.get(`/api/report/revenueByGroupSize`, {
        params: {
            startDate: startDate,
            endDate: endDate
        }
    });
}

export default { reportBookingType, reportBookingNumberPeople };