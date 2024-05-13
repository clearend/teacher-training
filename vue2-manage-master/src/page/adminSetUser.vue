<template>
    <div class="fillcontain">
        <head-top></head-top>
        <header class="admin_title">个人信息</header>
        <div class="admin_set">
            <el-row >
                <div style="text-align: end; margin-bottom: 10px;">
                    <el-button v-if="editing" type="primary" @click="handleEdit">编 辑</el-button>
                    <el-button v-if="!editing" type="success" @click="handleSave">保 存</el-button>
                </div>
            </el-row>

            <el-form :model="userInfo" ref="userInfoForm" :rules="rules">
                <el-form-item label="用户姓名" label-width="100px" prop="userName">
                    <el-input v-model="userInfo.userName" auto-complete="off" :disabled="editing"></el-input>
                </el-form-item>
                <el-form-item label="工号" label-width="100px" prop="jobId">
                    <el-input v-model.number="userInfo.jobId" disabled></el-input>
                </el-form-item>
                <el-form-item label="密码" label-width="100px" prop="password" >
                    <el-input v-model="userInfo.password" type="password" :disabled="editing"></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="100px" prop="gender">
                    <el-select v-model="userInfo.gender" placeholder="请选择性别" :disabled="editing">
                        <el-option label="未知" value="0"></el-option>
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="电话" label-width="100px" prop="phone">
                    <el-input v-model="userInfo.phone" :disabled="editing"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" label-width="100px" prop="email">
                    <el-input v-model="userInfo.email" :disabled="editing"></el-input>
                </el-form-item>
                <el-form-item label="提醒方式" label-width="100px" prop="gender">
                    <el-select v-model="userInfo.remindType" placeholder="请选择" :disabled="editing">
                        <el-option label="站内" value=0></el-option>
                        <el-option label="邮件" value=1></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
<!--            <ul>-->
<!--                <li>-->
<!--                    <span>姓名：</span><span>{{adminInfo.user_name}}</span>-->
<!--                </li>-->
<!--                <li>-->
<!--                    <span>注册时间：</span><span>{{adminInfo.create_time}}</span>-->
<!--                </li>-->
<!--                <li>-->
<!--                    <span>用户权限：</span><span>{{adminInfo.admin}}</span>-->
<!--                </li>-->
<!--                <li>-->
<!--                    <span>用户 ID：</span><span>{{adminInfo.id}}</span>-->
<!--                </li>-->
<!--                <li>-->
<!--                    <span>更换头像：</span>-->
<!--                    <el-upload-->
<!--                      class="avatar-uploader"-->
<!--                      :action="baseUrl + '/admin/update/avatar/' + adminInfo.id"-->
<!--                      :show-file-list="false"-->
<!--                      :on-success="uploadImg"-->
<!--                      :before-upload="beforeImgUpload">-->
<!--                      <img v-if="adminInfo.avatar" :src="baseImgPath + adminInfo.avatar" class="avatar">-->
<!--                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--                    </el-upload>-->
<!--                </li>-->
<!--            </ul>-->
        </div>
    </div>
</template>

<script>
	import headTop from '../components/headTop'
    import {mapState} from 'vuex'
    import {postMethod, getUserInfo} from "@/api/getDataLocal";

    export default {
        data(){
            return {
                userInfo: {},
                editing: true,
                rules: {
                    userName: [{required: true, message: '请输入姓名', trigger: 'blur'}],
                    jobId: [{required: true, message: '请输入正确的工号', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                    gender: [
                        {required: true, message: '请选择性别', trigger: 'blur'},
                    ],
                    phone: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},
                        {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
                    ],
                    email: [
                        {required: true, message: '请输入电子邮箱', trigger: 'blur'},
                        {pattern: /^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/, message: "请输入正确的电子邮箱", trigger: "blur"}
                    ]
                },
            }
        },
    	components: {
    		headTop,
    	},
        created() {
            this.getUserDetail()
        },
        computed: {
            ...mapState(['adminInfo']),
        },
        methods: {
            async getUserDetail() {
                const res = await getUserInfo(JSON.stringify({id: localStorage.getItem("userId")}));
                if (res.data.code === 200) {
                    switch (res.data.data.gender) {
                        case "男": res.data.data.gender = "1"; break;
                        case "女": res.data.data.gender = "2"; break;
                        default: res.data.data.gender = "0";
                    }
                    res.data.data.remindType = String(res.data.data.remindType);
                    this.userInfo = res.data.data;
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }
            },
            handleEdit() {
                this.editing = false;
            },
            handleSave() {
                this.$refs.userInfoForm.validate(async (valid) => {
                    if (valid) {
                        this.userInfo.userId = localStorage.getItem("userId");
                        const res = await postMethod('/core/user/upsert', this.userInfo);
                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '更新成功',
                            });

                            await this.getUserDetail();
                            this.editing = true;
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg,
                            });
                        }
                    }
                });
            }
        },
    }
</script>

<style lang="less">
	@import '../style/mixin';
	.explain_text{
		margin-top: 20px;
		text-align: center;
		font-size: 20px;
		color: #333;
	}
    .admin_set{
        width: 60%;
        background-color: #F9FAFC;
        min-height: 480px;
        margin: 20px auto 0;
        padding-right: 20px;
        padding-top: 20px;
        border-radius: 10px;
        ul > li{
            padding: 20px;
            span{
                color: #666;
            }
        }
    }
    .admin_title{
        margin-top: 20px;
        .sc(24px, #666);
        text-align: center;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        margin-top: 10px;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
</style>
