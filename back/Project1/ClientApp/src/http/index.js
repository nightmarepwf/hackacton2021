import axios from "axios";

export const API_URL = "http://185.233.2.140/hack/api/";

const $api = axios.create({
    baseURL: API_URL,
});

export default $api;