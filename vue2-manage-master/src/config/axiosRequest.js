import axios from "axios"

const serviceAxios = axios.create({
    baseURL: "http://localhost:8901",
    timeout: 5000,
    withCredentials: false,
});

serviceAxios.interceptors.request.use(
    (config) => {
        config.headers["userId"] = localStorage.getItem("userId");
        return config;
    },
    (error) => {
        Promise.reject(error).then(r => console.log(r));
    }
)

export default serviceAxios;
