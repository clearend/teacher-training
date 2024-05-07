import serviceAxios from '@/config/axiosRequest'

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
        header: {
            'userId': localStorage.getItem('userId')
        },
        data: data,
    })
}
