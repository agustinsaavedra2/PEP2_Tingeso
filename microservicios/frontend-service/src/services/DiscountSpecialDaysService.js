import httpClient from "../http-common"

const setDiscountSpecialDays = (id) => {
    return httpClient.put(`/api/discountSpecialDays/${id}`);
}

export default {setDiscountSpecialDays};