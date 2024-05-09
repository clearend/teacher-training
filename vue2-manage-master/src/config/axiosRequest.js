import axios from "axios"

const serviceAxios = axios.create({
    baseURL: "http://114.116.252.42/server",
    timeout: 5000,
    withCredentials: false,
});

serviceAxios.interceptors.request.use(
    (config) => {
        config.headers["userId"] = localStorage.getItem("userId");
        config.headers["Content-Type"] = "application/json";
        return config;
    },
    (error) => {
        Promise.reject(error).then(r => console.log(r));
    }
)

export default serviceAxios;
