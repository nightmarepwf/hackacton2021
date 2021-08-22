import axios from "axios";

export const API_URL = "http://e4f9-195-209-251-111.ngrok.io/api/";

const $api = axios.create({
    baseURL: API_URL,
});

export default $api;