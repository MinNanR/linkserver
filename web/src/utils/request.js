import axios from 'axios'

const request = axios.create({
    baseURL: "http://localhost:8989",
    timeout: 30000,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    transformRequest: [
        (data) => {
            return JSON.stringify(data)
        }
    ]
})

request.interceptors.request.use(
    config => {
        let token = localStorage.getItem('token')
        config.headers.authorization = token
        console.log(token)
        console.log(config)
        return config;
    },
    error => {
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    response => {
        if (response.status === 200) {
            let data = response.data
            if (data.code === '000') {
                return data.data
            } else {
                Promise.reject(data.message)
            }
        } else {
            Promise.reject()
        }
    },
    error => {
        return Promise.reject(error)
    }
)

export default request
