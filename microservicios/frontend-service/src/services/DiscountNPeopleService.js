import httpClient from "../http-common"

const setDiscountPeopleNumber = (id) => {
    return httpClient.put(`/api/discountNumPeople/${id}`);
}

export default {setDiscountPeopleNumber}