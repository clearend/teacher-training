<template>
    <div class="login_page fillcontain">
        <transition name="form-fade" mode="in-out">
            <section class="form_contianer" v-show="showLogin">
                <div class="manage_tip">
                    <p>课程思政教师培训管理系统</p>
                </div>
                <el-form :model="loginForm" :rules="rules" ref="loginForm">
                    <el-form-item prop="username">
                        <el-input v-model="loginForm.username" placeholder="用户名"><span>dsfsf</span></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('loginForm')" class="submit_btn">登录</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="registerDialogFormVisible=true" class="submit_btn">注册</el-button>
                    </el-form-item>
                </el-form>
            </section>
        </transition>

        <el-dialog title="用户注册" v-model="registerDialogFormVisible">
            <el-form :model="registerTable" :rules="rules" ref="registerForm">
                <el-form-item label="用户姓名" label-width="100px" prop="userName">
                    <el-input v-model="registerTable.userName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="工号" label-width="100px" prop="jobId">
                    <el-input v-model="registerTable.jobId"></el-input>
                </el-form-item>
                <el-form-item label="密码" label-width="100px" prop="password">
                    <el-input type="password" v-model="registerTable.password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" label-width="100px" prop="confirmPassword">
                    <el-input type="password" v-model="registerTable.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="100px" prop="gender">
                    <el-select v-model="registerTable.gender" placeholder="请选择性别">
                        <el-option label="未知" value="0"></el-option>
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="电话" label-width="100px" prop="phone">
                    <el-input v-model="registerTable.phone"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" label-width="100px" prop="email">
                    <el-input v-model="registerTable.email"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCancel">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {etAdminInfo} from '@/api/getData'
import {login, postMethod} from "@/api/getDataLocal";
// import axios from "axios"
import {mapActions, mapState} from 'vuex'

export default {
    data(){
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.registerTable.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            registerTable: {},
            registerDialogFormVisible: false,
            loginForm: {
                username: '',
                password: '',
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' },],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                confirmPassword: [{ required: true, validator: validatePass2, trigger: 'blur' }],
                userName: [{ required: true, message: '请输入姓名', trigger: 'blur' },],
                jobId: [{ required: true, message: '请输入工号', trigger: 'blur' },],
                gender: [{required: true, message: '请选择性别', trigger: 'blur'},],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
                ],
                email: [
                    {required: true, message: '请输入电子邮箱', trigger: 'blur'},
                    {pattern: /^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/, message: "请输入正确的电子邮箱", trigger: "blur"}
                ]
            },
            showLogin: false,
        }
    },
    mounted(){
        this.showLogin = true;
        // if (!this.adminInfo.id) {
        //     this.getAdminData()
        // }
    },
    computed: {
        // ...mapState(['adminInfo']),
    },
    methods: {
        // ...mapActions(['getAdminData']),
        async submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    const res = await login({username: this.loginForm.username, password: this.loginForm.password})
                    // const res = {data: {code: 1900}}
                    if (res.data.code === 200) {
                        localStorage.setItem('userId', res.data.data.userId)

                        this.$message({
                            type: 'success',
                            message: '登录成功'
                        });

                        console.log(res.data.data)
                        if (res.data.data.role === 'admin') {
                            this.$router.push('manage')
                        } else if (res.data.data.role === 'user') {
                            this.$router.push('manageUser')
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg
                        });
                    }
                } else {
                    this.$notify.error({
                        title: '错误',
                        message: '请输入正确的用户名密码',
                        offset: 100
                    });
                    return false;
                }
            });
        },
        handleCancel() {
            this.registerTable = {};
            this.registerDialogFormVisible = false;
        },
        addUser() {
            this.$refs.registerForm.validate(async valid => {
                if (valid) {
                    const res = await postMethod('/core/user/upsert', this.registerTable);
                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '注册成功，请登录',
                        });

                        this.registerDialogFormVisible = false;
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg,
                        });
                    }
                }
            });
        },
    },
    watch: {
        adminInfo: function (newValue){
            if (newValue.id) {
                this.$message({
                    type: 'success',
                    message: '检测到您之前登录过，将自动登录'
                });
                this.$router.push('manage')
            }
        }
    }
}
</script>

<style lang="less" scoped>
@import '../style/mixin';
.login_page{
    background-color: #324057;
}
.manage_tip{
    position: absolute;
    width: 100%;
    top: -100px;
    left: 0;
    p{
        font-size: 34px;
        color: #fff;
    }
}
.form_contianer{
    .wh(320px, 210px);
    .ctp(320px, 210px);
    padding: 25px;
    border-radius: 5px;
    text-align: center;
    background-color: #fff;
    .submit_btn{
        width: 100%;
        font-size: 16px;
    }
}
.tip{
    font-size: 12px;
    color: red;
}
.form-fade-enter-active, .form-fade-leave-active {
    transition: all 1s;
}
.form-fade-enter, .form-fade-leave-active {
    transform: translate3d(0, -50px, 0);
    opacity: 0;
}
</style>
