<template>
    <div class="fillcontain">
        <head-top></head-top>
        <el-row style="padding-top: 10px; padding-left: 20px;">
            <div style="text-align: right; padding-right: 40px;">
                <el-button
                        type="success"
                        style="padding-left: 10px; padding-right: 10px;"
                        @click="handleAdd">添 加</el-button>
            </div>
        </el-row>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
                    :data="tableData"
                    highlight-current-row
                    style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column property="remindUserName" label="用户姓名" width="200"></el-table-column>
                <el-table-column property="remindStatus" label="提醒状态" width="200">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.remindStatus === 0" size="small" type="warning">未提醒</el-button>
                        <el-button v-else-if="scope.row.remindStatus === 1" size="small" type="success">已提醒</el-button>
                    </template>
                </el-table-column>
                <el-table-column property="remindContent" label="提醒内容"></el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="small" type="danger" @click="handleRemoveRemind(scope.row)">撤 销</el-button>
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
        </div>

        <el-dialog title="新增提醒消息" v-model="addRemindFormVisible">
            <el-form :model="newRemind" :rules="rules" ref="addRemindForm">
                <el-form-item label="提醒用户" label-width="100px" prop="remindUserIds">
                    <el-select v-model="newRemind.remindUserIds" filterable multiple placeholder="请选择" :filter-method="getFilterUserList">
                        <el-option
                                v-for="item in userList"
                                :value="item.userId"
                                :label="item.userName + ' ' + item.jobId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="提醒内容" label-width="100px" prop="remindContent">
                    <el-input v-model="newRemind.remindContent" type="textarea" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="提醒日期" label-width="100px" prop="remindContent">
                    <el-date-picker v-model="newRemind.remindTime" type="date" placeholder="请选择日期"></el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCancelAdd">取 消</el-button>
                <el-button type="primary" @click="handleAddRemind">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import headTop from "../components/headTop.vue";
import {postMethod} from "@/api/getDataLocal";

export default {
    data() {
        let userListValidator = (rule, value, callback) => {
            if (value.length === 0) {
                callback(new Error('请选择提醒用户'));
            } else {
                callback();
            }
        };

        return {
            tableData: [],
            currentPage: 1,
            offset: 0,
            limit: 15,
            count: 0,
            addRemindFormVisible: false,
            newRemind: {
                remindUserIds: [],
                remindContent: ''
            },
            userList: [],
            rules: {
                "remindContent": [{required: true, message: '请输入提醒内容', trigger: 'blur'}],
                "remindUserIds": [{required: true, validator: userListValidator, trainingType: 'blur'}]
            }
        }
    },
    components: {
        headTop,
    },
    async created() {
        await this.getRemindList();
    },
    methods: {
        async getRemindList() {
            let query = {
                pageRequest: {
                    currentPage: this.currentPage,
                    pageSize: this.limit,
                },
            }
            let res = await postMethod('/core/remindMessage/list', JSON.stringify(query));

            if (res.data.code === 200) {
                this.tableData = res.data.data.remindList;
                this.count = res.data.data.count;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg
                });
            }
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            await this.getRemindList()
        },
        async getFilterUserList(value) {
            let queryParam = {
                userName: value,
                pageRequest: {
                    currentPage: 1,
                    pageSize: 1000
                }
            }
            let res = await postMethod("/core/user/list", JSON.stringify(queryParam));
            if (res.data.code === 200) {
                this.userList = res.data.data.userList;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg,
                });
            }
        },
        async handleAdd() {
            await this.getFilterUserList("");
            this.addRemindFormVisible = true
        },
        handleCancelAdd() {
            this.newRemind = {};
            this.addRemindFormVisible = false;
        },
        handleAddRemind() {
            this.$refs.addRemindForm.validate(async (valid) => {
                if (valid) {
                    let addRequest = this.newRemind;
                    addRequest.remindTime = addRequest.remindTime.valueOf();
                    console.log(addRequest);
                    let res = await postMethod("/core/remindMessage/add", JSON.stringify(addRequest));

                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '添加成功'
                        });

                        await this.getRemindList();
                        this.addRemindFormVisible = false;
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg
                        });
                    }
                }
            });
        },
        async handleRemoveRemind(row) {
            let res = await postMethod("/core/remindMessage/delete", JSON.stringify({id: row.remindId}));

            console.log(res)
            if (res.data.code === 200) {
                this.$message({
                    type: 'success',
                    message: '撤销成功'
                });

                this.currentPage = 1;
                await this.getRemindList();
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg
                });
            }
        },
    }
}

</script>

<style scoped lang="less">
@import '../style/mixin';
.table_container{
  padding: 20px;
}
.filter-input{
  width: 200px;
}
</style>