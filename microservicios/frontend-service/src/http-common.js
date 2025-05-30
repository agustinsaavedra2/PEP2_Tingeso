import axios from "axios"

const pep2TingesoBackendServer = import.meta.env.VITE_PEP2_TINGESO_BACKEND_SERVER;
const pep2TingesoBackendPort = import.meta.env.VITE_PEP2_TINGESO_BACKEND_PORT;

console.log(pep2TingesoBackendServer);
console.log(pep2TingesoBackendPort);

export default axios.create({
    baseURL: `http://${pep2TingesoBackendServer}:${pep2TingesoBackendPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});