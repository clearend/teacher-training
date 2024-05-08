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
            <el-form :model="newTable" :rules="rules" ref="addUserForm">
                <el-form-item label="用户姓名" label-width="100px" prop="userName">
                    <el-input v-model="newTable.userName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="工号" label-width="100px" prop="jobId">
                    <el-input v-model="newTable.jobId"></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="100px" prop="gender">
                    <el-select v-model="newTable.gender" placeholder="请选择性别">
                        <el-option label="未知" value="0"></el-option>
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

        <div class="table_container" style="padding-top: 10px;">
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
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
                <el-form :model="selectTable" ref="updateUserForm" :rules="rules">
                    <el-form-item label="用户姓名" label-width="100px" prop="userName">
                        <el-input v-model="selectTable.userName" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="工号" label-width="100px" prop="jobId">
                        <el-input v-model.number="selectTable.jobId"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" label-width="100px" prop="password">
                        <el-input v-model="selectTable.password" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" label-width="100px" prop="gender">
                        <el-select v-model="selectTable.gender" placeholder="请选择性别">
                            <el-option label="未知" value="0"></el-option>
                            <el-option label="男" value="1"></el-option>
                            <el-option label="女" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="电话" label-width="100px" prop="phone">
                        <el-input v-model="selectTable.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="电子邮箱" label-width="100px" prop="email">
                        <el-input v-model="selectTable.email"></el-input>
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

import headTop from '../components/headTop';
import {getUserList, getUserInfo, postMethod} from '@/api/getDataLocal';
export default {
    data() {
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
        async handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            await this.getUsers()
        },
        handleAdd() {
            this.newTable = {
                userName: "",
                jobId: "",
                gender: "",
                phone: "",
                email: "",
            };
            this.$refs.newUserForm = this.newTable;
            this.addDialogFormVisible = true;
        },
        async handleEdit(row) {
            this.selectTable = await this.userInfo(row.userId);
            console.log(this.selectTable)
            this.dialogFormVisible = true;
        },
        async handleDelete(row) {
            this.$confirm("确定要删除该用户？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                showCancelButton: true,
                showClose: false,
                closeOnClickModal: false
            }).then(async () => {
                const res = await postMethod('/core/user/delete', JSON.stringify({id: row.userId}))
                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '删除用户成功'
                        });

                        await this.getUsers();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg
                        });
                    }
            }).catch(() => {
            });
        },
        addUser() {
            this.$refs.addUserForm.validate(async valid => {
                if (valid) {
                    const res = await postMethod('/core/user/upsert', this.newTable);
                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '添加成功',
                        });

                        this.addDialogFormVisible = false;

                        await this.getUsers();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg,
                        });
                    }
                }
            });

        },
        async getUsers() {
            const res = await getUserList({
                "pageRequest": {
                    "currentPage": this.currentPage,
                    "pageSize": this.limit,
                }
            });

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
        async userInfo(userId) {
            const res = await getUserInfo(JSON.stringify({id: userId}));
            if (res.data.code === 200) {
                switch (res.data.data.gender) {
                    case "男": res.data.data.gender = "1"; break;
                    case "女": res.data.data.gender = "2"; break;
                    default: res.data.data.gender = "0";
                }
                return res.data.data;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg,
                });
            }
        },
        async updateUser() {
            try {
                this.$refs.updateUserForm.validate(async valid => {
                    if (valid) {
                        const res = await postMethod('/core/user/upsert', this.selectTable);
                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '更新成功',
                            });

                            this.dialogFormVisible = false;

                            await this.getUsers();
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg,
                            });
                        }
                    }
                });
            } catch(err) {
                console.log('更新用户信息失败', err);
            }
        },
    },
}
</script>

<style lang="less">
@import '../style/mixin';
.table_container{
    padding: 20px;
}
</style>
