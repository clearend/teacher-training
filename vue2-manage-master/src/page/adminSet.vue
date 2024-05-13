<template>
    <div class="fillcontain">
        <head-top></head-top>
        <header class="admin_title">管理员信息</header>
        <div class="admin_set">
            <el-row>
                <el-col :span="18"></el-col>
                <el-col :span="6">
                    <el-button type="primary" @click="editing = !editing">{{editing ? '保存' : '编辑'}}</el-button>
                </el-col>
            </el-row>

            <el-form :model="userInfo" ref="userInfoForm" :rules="rules">
                <el-form-item label="用户姓名" label-width="100px" prop="userName">
                    <el-input v-model="userInfo.userName" auto-complete="off" :disabled="editing"></el-input>
                </el-form-item>
                <el-form-item label="工号" label-width="100px" prop="jobId" :disabled="true">
                    <el-input v-model.number="userInfo.jobId"></el-input>
                </el-form-item>
                <el-form-item label="密码" label-width="100px" prop="password" :disabled="editing">
                    <el-input v-model="userInfo.password" type="password"></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="100px" prop="gender" :disabled="editing">
                    <el-select v-model="userInfo.gender" placeholder="请选择性别">
                        <el-option label="未知" value="0"></el-option>
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="电话" label-width="100px" prop="phone" :disabled="editing">
                    <el-input v-model="userInfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" label-width="100px" prop="email" :disabled="editing">
                    <el-input v-model="userInfo.email"></el-input>
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
<!--                    <span>管理员权限：</span><span>{{adminInfo.admin}}</span>-->
<!--                </li>-->
<!--                <li>-->
<!--                    <span>管理员 ID：</span><span>{{adminInfo.id}}</span>-->
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
<!--                </li>    -->
<!--            </ul>-->
        </div>
    </div>
</template>

<script>
	import headTop from '../components/headTop'
    import {mapState} from 'vuex'
    import {baseUrl, baseImgPath} from '@/config/env'

    export default {
        data(){
            return {
                baseUrl,
                baseImgPath,
            }
        },
    	components: {
    		headTop,
    	},
        computed: {
            ...mapState(['adminInfo']),
        },
        methods: {
            uploadImg(res, file) {
                if (res.status == 1) {
                    this.adminInfo.avatar = res.image_path;
                }else{
                    this.$message.error('上传图片失败！');
                }
            },
            beforeImgUpload(file) {
                const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isRightType) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isRightType && isLt2M;
            },
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
        min-height: 400px;
        margin: 20px auto 0;
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
