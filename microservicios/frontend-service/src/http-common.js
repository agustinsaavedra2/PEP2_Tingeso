import axios from "axios"

const pep1TingesoBackendServer = import.meta.env.VITE_PEP1_TINGESO_BACKEND_SERVER;
const pep1TingesoBackendPort = import.meta.env.VITE_PEP1_TINGESO_BACKEND_PORT;

console.log(pep1TingesoBackendServer);
console.log(pep1TingesoBackendPort);

export default axios.create({
    baseURL: `http://${pep1TingesoBackendServer}:${pep1TingesoBackendPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});