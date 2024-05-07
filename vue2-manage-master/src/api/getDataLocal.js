import serviceAxios from '@/config/axiosRequest'

export const login = data => {
    return serviceAxios({
        url: '/core/user/login',
        method: 'post',
        data,
    })
}
