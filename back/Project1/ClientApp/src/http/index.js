import axios from "axios";

export const API_URL = "http://24de-195-209-251-111.ngrok.io/api/";

const $api = axios.create({
    baseURL: API_URL,
});

export default $api;