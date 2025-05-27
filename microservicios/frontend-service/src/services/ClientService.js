import httpClient from "../http-common"

const createClient = (data) => {
    return httpClient.post("/api/client/", data);
}

const getClientById = (id) => {
    return httpClient.get(`/api/client/id/${id}`);
}

const getClientByEmail = (email) => {
    return httpClient.get(`/api/client/email/${email}`);
}

const getAllClients = () => {
    return httpClient.get("/api/client/");
}

const updateClient = (data) => {
    return httpClient.put("/api/client/", data);
}

const deleteClient = (id) => {
    return httpClient.delete(`/api/client/${id}`);
}

export default { createClient, getClientById, getClientByEmail, getAllClients, updateClient, deleteClient }; 