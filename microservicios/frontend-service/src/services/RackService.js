import httpClient from "../http-common"

const createRack = (data) => {
    return httpClient.post("/api/rack/", data);
}

const getRackById = (id) => {
    return httpClient.get(`/api/rack/id/${id}`);
}

const getAllRacks = () => {
    return httpClient.get("/api/rack/");
}

const updateRack = (data) => {
    return httpClient.put("/api/rack/", data);
}

const deleteRack = (id) => {
    return httpClient.delete(`/api/rack/${id}`);
}

const getWeeklyRacks = (startDate) => {
    return httpClient.get(`/api/rack/weekly?startDate=${startDate}`);
}

export default { createRack, getRackById, getAllRacks, updateRack, deleteRack, getWeeklyRacks }; 