import httpClient from "../http-common"

const setDiscountFreqClient = (id) => {
    return httpClient.put(`/api/discountFreqClients/${id}`);
}

export default {setDiscountFreqClient};