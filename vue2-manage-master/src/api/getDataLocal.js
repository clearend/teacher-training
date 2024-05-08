import serviceAxios from '@/config/axiosRequest'
import da from "element-ui/lib/locale/lang/da";

export const login = data => {
    return serviceAxios({
        url: '/core/user/login',
        method: 'post',
        data,
    })
}

export const getUserList = data => {
    return serviceAxios({
        url: '/core/user/list',
        method: 'post',
        data: data,
    })
}

export const getUserInfo = data => {
    return serviceAxios({
        url: '/core/user/info',
        method: 'post',
        data: data,
    })
}

export const upsertUser = data => {
    return serviceAxios({
        url: 'core/user/upsert',
        method: 'post',
        data: data
    })
}

export const postMethod = (url, data) => {
    return serviceAxios({
        url: url,
        method: 'post',
        data: data,
    })
}
