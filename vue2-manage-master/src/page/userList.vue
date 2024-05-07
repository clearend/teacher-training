<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div style="text-align: right; padding-right: 40px; margin-top: 10px;">
            <el-button
                size="small"
                type="success"
                style="padding-left: 10px; padding-right: 10px;"
                @click="handleAdd">添加</el-button>
        </div>

        <el-dialog title="新增用户信息" v-model="addDialogFormVisible">
            <el-form :model="newTable" :rules="rules">
                <el-form-item label="用户姓名" label-width="100px" prop="name">
                    <el-input v-model="newTable.userName" auto-complete="off" clearable></el-input>
                </el-form-item>
                <el-form-item label="工号" label-width="100px" prop="jobId">
                    <el-input v-model="newTable.jobId"></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="100px" prop="gender">
                    <el-select v-model="newTable.gender" placeholder="请选择性别">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
<!--                    <el-input v-model="newTable.registeDate"></el-input>-->
                </el-form-item>
                <el-form-item label="电话" label-width="100px" prop="phone">
                    <el-input v-model="newTable.phone"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" label-width="100px" prop="email">
                    <el-input v-model="newTable.email"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </div>
        </el-dialog>

        <div class="table_container"  style="padding-top: 10px;">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column property="userName" label="用户姓名"></el-table-column>
                <el-table-column property="jobId" label="工号"></el-table-column>
                <el-table-column property="gender" label="性别"></el-table-column>
                <el-table-column property="phone" label="手机号码"></el-table-column>
                <el-table-column property="email" label="电子邮件"></el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="limit"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>

            <el-dialog title="修改用户信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="用户姓名" label-width="100px">
                        <el-input v-model="selectTable.userName" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="注册地点" label-width="100px">
                        <el-input v-model="selectTable.registeLocation"></el-input>
                    </el-form-item>
                    <el-form-item label="注册日期" label-width="100px">
                        <el-input v-model="selectTable.registeDate"></el-input>
                    </el-form-item>
                    <el-form-item label="账号" label-width="100px">
                        <el-input v-model="selectTable.loginId"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" label-width="100px">
                        <el-input v-model="selectTable.password"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateUser">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import {log} from "nightwatch/lib/util/logger";

    const demoUserData = [
        {
            registeDate: '2016-05-02',
            name: '张丽丽',
            registeLocation: '北京市朝阳区建国路 1 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-04',
            name: '王伟伟',
            registeLocation: '上海市黄浦区南京东路 123 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-01',
            name: '李娜娜',
            registeLocation: '广州市天河区天河路 789 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-03',
            name: '刘涛涛',
            registeLocation: '深圳市福田区深南大道 1010 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-05',
            name: '陈敏敏',
            registeLocation: '杭州市西湖区文二西路 202 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-07',
            name: '吴美丽',
            registeLocation: '南京市玄武区中山路 303 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-06',
            name: '赵磊磊',
            registeLocation: '武汉市江岸区中山大道 404 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-08',
            name: '孙芳芳',
            registeLocation: '成都市青羊区光华大道 505 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-09',
            name: '钱强强',
            registeLocation: '重庆市渝中区人民路 606 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-10',
            name: '朱雪峰',
            registeLocation: '西安市雁塔区南二环 707 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-11',
            name: '孔雅丽',
            registeLocation: '天津市和平区解放北路 808 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-12',
            name: '曹波波',
            registeLocation: '苏州市姑苏区双塔街 909 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-13',
            name: '董莉莉',
            registeLocation: '南昌市东湖区解放西路 1010 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-14',
            name: '黄丹丹',
            registeLocation: '福州市鼓楼区五一路 1111 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-15',
            name: '肖杰杰',
            registeLocation: '济南市市中区英雄山路 1212 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-16',
            name: '梁婷婷',
            registeLocation: '沈阳市和平区长白大街 1313 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-17',
            name: '许军军',
            registeLocation: '长沙市岳麓区梅溪湖路 1414 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-18',
            name: '万丽丽',
            registeLocation: '郑州市金水区农业路 1515 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-19',
            name: '雷兵兵',
            registeLocation: '南宁市兴宁区中山路 1616 号',
            loginId: '123456',
            password: '123456',
        },
        {
            registeDate: '2016-05-20',
            name: '韩霞霞',
            registeLocation: '合肥市庐阳区和平路 1717 号',
            loginId: '123456',
            password: '123456',
        }
        // 可以继续添加更多的数据
    ]

import headTop from '../components/headTop';
import {getUserList} from '@/api/getDataLocal';
export default {
    data() {
        var checkNumber = (rule, value, callback) => {
            setTimeout(() => {
                if (!Number.isInteger(value)) {
                    callback(new Error('请输入数字值'));
                } else {
                    callback();
                }
            }, 1000);
        };

        var checkEmail = (rule, value, callback) => {
            if (/^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/i.test(value) == false) {
                callback(new Error("邮箱格式错误"));
            } else {
                callback();
            }
        }

        return {
            tableData: [],
            currentRow: null,
            offset: 0,
            limit: 15,
            count: 0,
            currentPage: 1,
            addDialogFormVisible: false,
            newTable: {},
            selectTable: {},
            dialogFormVisible: false,
            rules: {
                name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
                jobId: [
                    {required: true, message: '请输入工号', trigger: 'blur'},
                    {validator: checkNumber, trigger: 'blur'},
                ],
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
        this.initData();
    },
    methods: {
        async initData() {
            try {
                await this.getUsers();
            } catch(err) {
                console.log('获取数据失败', err);
            }
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            this.getUsers()
        },
        async getUsers() {
            const res = await getUserList({
                "pageRequest": {
                    "currentPage": this.currentPage,
                    "pageSize": this.limit,
                }
            });

            console.log(res.data.data)

            if (res.data.code === 200) {
                this.tableData = res.data.data.userList;
                this.count = res.data.data.count;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg
                });
            }
        },
        handleEdit(row) {
            this.selectTable = row;
            this.dialogFormVisible = true;
        },
        handleDelete(index, row) {
            try{
                // const res = await deleteFood(row.item_id);
                demoUserData.splice(this.offset + index, 1);
                if (1) {
                    this.$message({
                        type: 'success',
                        message: '删除用户成功'
                    });
                    // this.tableData.splice(index, 1);
                    this.tableData = demoUserData.slice(this.offset, this.offset + this.limit);
                    this.count = demoUserData.length;
                } else {
                    throw new Error(res.message)
                }
            } catch (err) {
                this.$message({
                    type: 'error',
                    message: err.message
                });
                console.log('删除用户失败')
            }
        },
        updateUser() {
            this.dialogFormVisible = false;
            try {
                // const subData = {new_category_id: this.selectMenu.value, specs: this.specs};
                // const postData = {...this.selectTable, ...subData};
                // const res = await updateFood(postData)
                if (1) {
                    this.$message({
                        type: 'success',
                        message: '更新用户信息成功'
                    });
                    // this.getFoods();
                } else {
                    this.$message({
                        type: 'error',
                        message: res.message
                    });
                }
            }catch(err){
                console.log('更新用户信息失败', err);
            }
        },
        handleAdd() {
            this.newTable = {
                "name": "",
                "registeDate": "",
                "registeLocation": "",
                "loginId": "",
                "password": "",
            };
            this.addDialogFormVisible = true;
        },
        addUser() {
            if (this.newTable.name === "") {
                this.$message({
                    type: 'error',
                    message: '用户姓名不能为空'
                });
                return;
            }
            if (this.newTable.registeDate === "") {
                this.$message({
                    type: 'error',
                    message: '注册日期不能为空'
                });
                return;
            }
            if (this.newTable.registeLocation === "") {
                this.$message({
                    type: 'error',
                    message: '注册地点不能为空'
                });
                return;
            }
            if (this.newTable.loginId === "") {
                this.$message({
                    type: 'error',
                    message: '账号不能为空'
                });
                return;
            }
            if (this.newTable.password === "") {
                this.$message({
                    type: 'error',
                    message: '密码不能为空'
                });
                return;
            }

            this.addDialogFormVisible = false;
            demoUserData.splice(0, 0, this.newTable);
            this.count = demoUserData.length;
            this.currentPage = 1;
            this.tableData = demoUserData.slice(0, this.limit);

            this.$message({
                type: 'success',
                message: '添加成功'
            });
        }
    },
}
</script>

<style lang="less">
@import '../style/mixin';
.table_container{
    padding: 20px;
}
</style>
