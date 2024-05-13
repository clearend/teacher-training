<template>
    <div class="fillcontain">
        <head-top></head-top>

        <el-row style="padding-top: 10px; padding-left: 20px;">
            <el-col :span="20">
                <el-row :gutter="10">
                    <el-col :span="4"><el-input v-model="tableFilter.trainingName" placeholder="培训名称"></el-input></el-col>
                    <el-col :span="4"><el-input v-model="tableFilter.trainingAddress" placeholder="培训地址"></el-input></el-col>
                    <el-col :span="3">
                        <el-select v-model="tableFilter.trainingType" placeholder="培训类型">
                            <el-option label="校级" value="100"></el-option>
                            <el-option label="省市级" value="200"></el-option>
                            <el-option label="国家级" value="300"></el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="3">
                        <el-date-picker v-model="tableFilter.startDate" type="date" placeholder="开始日期" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col :span="3">
                        <el-date-picker v-model="tableFilter.endDate" type="date" placeholder="结束日期" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col :span="4">
                        <el-button type="primary" @click="handleFilterQuery">查 询</el-button>
                        <el-button type="warning" @click="handleFilterClear">清 空</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>

        <div class="table_container"  style="padding-top: 10px;">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column label="培训名称" property="trainingName"></el-table-column>
                <el-table-column label="培训地点" property="trainingAddress"></el-table-column>
                <el-table-column label="培训时间" property="trainingTime"></el-table-column>
                <el-table-column label="培训类型" property="trainingType"></el-table-column>
                <el-table-column label="状态" property="status">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status === '0'" size="small" type="info">未参加</el-button>
                        <el-button v-if="scope.row.status === '100'" size="small" type="warning">待完成</el-button>
                        <el-button v-if="scope.row.status === '150'" size="small" type="primary">待审核</el-button>
                        <el-button v-if="scope.row.status === '200'" size="small" type="success">已完成</el-button>
                        <el-button v-if="scope.row.status === '300'" size="small" type="danger">未完成</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status === '0'" size="small" type="success" @click="handleJoin(scope.row)">报名</el-button>
                        <el-button v-if="scope.row.status === '100'" size="small" type="warning" @click="handleCancelJoin(scope.row)">取消报名</el-button>
                        <el-button v-if="scope.row.status === '100'" size="small" type="info" @click="handleReport(scope.row)">学习上报</el-button>
                        <el-button v-if="scope.row.status === '150'" size="small" type="danger" @click="handleCancelReport(scope.row)">撤销上报</el-button>
                        <el-button v-if="scope.row.status === '150'" size="small" type="primary" @click="handleReportInfo(scope.row)">查看详情</el-button>
                        <el-button v-if="scope.row.status === '200'" size="small" type="primary" @click="handleReportInfo(scope.row)">查看详情</el-button>
                        <el-button v-if="scope.row.status === '300'" size="small" type="primary" @click="handleReportInfo(scope.row)">查看详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="limit"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>

            <el-dialog title="培训完成上报" v-model="dialogFormVisible">
                <el-form :model="selectTable" ref="reportForm" :rules="rules">
                    <el-form-item label="培训标题" label-width="100px">
                        <el-input v-model="selectTable.trainingName" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训地点" label-width="100px">
                        <el-input v-model="selectTable.trainingAddress" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训时间" label-width="100px">
                        <el-input v-model="selectTable.trainingTime" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训类型" label-width="100px">
                        <el-input v-model="selectTable.trainingType" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训内容" label-width="100px">
                        <el-input v-model="selectTable.trainingContent" type="textarea" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训心得" label-width="100px" prop="reportContent">
                        <el-input v-model="selectTable.reportContent" type="textarea"></el-input>
                    </el-form-item>
                    <el-form-item label="附件" label-width="100px" prop="fileId">
                        <el-upload
                                class="upload-demo"
                                :action="uploadUrl"
                                :on-success="handleFileUploadSuccess"
                                :on-remove="handleFileRemove">
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">文件大小不超过100MB</div>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="report">确 定</el-button>
                </div>
            </el-dialog>

            <el-dialog title="培训完成详情" v-model="finishFormVisible" :show-close="true">
                <el-form :model="selectTable">
                    <el-form-item label="培训标题" label-width="100px">
                        <el-input v-model="selectTable.trainingName" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训地点" label-width="100px">
                        <el-input v-model="selectTable.trainingAddress" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训时间" label-width="100px">
                        <el-input v-model="selectTable.trainingTime" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训类型" label-width="100px">
                        <el-input v-model="selectTable.trainingType" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训内容" label-width="100px">
                        <el-input v-model="selectTable.trainingContent" type="textarea" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="培训心得" label-width="100px" prop="reportContent">
                        <el-input v-model="selectTable.reportContent" type="textarea" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="附件" label-width="100px" prop="reportContent">
                        <a v-if="selectTable.fileName !== ''" type="primary" :href="selectTable.fileUrl" :download="selectTable.fileName">{{selectTable.fileName}}</a>
                        <span v-if="selectTable.fileName === null">无</span>
                    </el-form-item>
                    <el-form-item label="审批状态" label-width="100px" prop="reportContent">
                        <el-input v-model="selectTable.auditStatus" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item v-if="selectTable.auditStatus === '已通过' || selectTable.auditStatus === '未通过'" label="审批意见" label-width="100px" prop="reportContent">
                        <el-input v-model="selectTable.auditRemark" :disabled="true"></el-input>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import headTop from '../components/headTop'
import {postMethod} from "@/api/getDataLocal";
import {uploadUrl} from "@/config/env";
export default {
    data() {
        return {
            uploadUrl: uploadUrl,
            offset: 0,
            limit: 15,
            count: 0,
            tableData: [],
            currentPage: 1,
            selectTable: {},
            dialogFormVisible: false,
            newTable: {},
            finishFormVisible: false,
            tableFilter: {
                trainingName: "",
                trainingType: "",
                trainingAddress: "",
                startDate: "",
                endDate: "",
            },
            rules: {
                reportContent: [{required: true, message: "请输入学习心得", trigger: 'blur'}]
            }
        }
    },
    created() {
        this.initData();
    },
    components: {
        headTop,
    },
    methods: {
        async initData() {
            await this.getTrainingList();
        },
        async getTrainingList() {
            const pageRequest = {
                currentPage: this.currentPage,
                pageSize: this.limit,
            };
            const query = {
                trainingName: this.tableFilter.trainingName,
                trainingType: this.tableFilter.trainingType,
                trainingAddress: this.tableFilter.trainingAddress,
                startDate: this.tableFilter.startDate.valueOf(),
                endDate: this.tableFilter.endDate.valueOf(),
                pageRequest: pageRequest
            };
            const res = await postMethod('/core/training/list/user',  JSON.stringify(query));

            if (res.data.code === 200) {
                this.count = res.data.data.count;
                this.tableData = res.data.data.trainingList;
                this.tableData.forEach(item => {
                     switch (item.status) {
                         case "待完成": item.status = "100"; break;
                         case "已完成": item.status = "200"; break;
                         case "未完成": item.status = "300"; break;
                         case "审核中": item.status = "150"; break;
                         default: item.status = "0";
                     }
                })
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg,
                });
            }
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            this.getTrainingList()
        },
        async handleFilterQuery() {
            this.currentPage = 1;
            await this.getTrainingList();
        },
        async handleFilterClear() {
            this.currentPage = 1;
            this.tableFilter = {
                trainingName: "",
                trainingType: "",
                trainingAddress: "",
                startDate: "",
                endDate: "",
            };
            await this.getTrainingList();
        },
        handleEdit(row) {
            // console.log(row)
            this.selectTable = row;
            this.dialogFormVisible = true;
        },
        async handleJoin(row) {
            const res = await postMethod('/core/trainingUser/addPerson',  JSON.stringify({
                trainingId: row.trainingId,
                userList: [
                    localStorage.getItem("userId")
                ],
            }));

            if (res.data.code === 200) {
                this.$message({
                    type: 'success',
                    message: '报名成功，请记得按时参与'
                });

                await this.getTrainingList();
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg,
                });
            }
        },
        async handleCancelJoin(row) {
            this.$confirm("确定要取消参与此次培训？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                showCancelButton: true,
                showClose: false,
                closeOnClickModal: false
            }).then(async () => {
                const res = await postMethod('/core/trainingUser/deletePerson', JSON.stringify(
                    {
                        trainingId: row.trainingId,
                        userId: localStorage.getItem("userId"),
                    })
                );
                if (res.data.code === 200) {
                    this.$message({
                        type: 'success',
                        message: '取消报名成功',
                    });
                    await this.getTrainingList();
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }
            }).catch();
        },
        handleCancelReport(row) {
            this.$confirm("确定要撤销此次学习汇报？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                showCancelButton: true,
                showClose: false,
                closeOnClickModal: false
            }).then(async () => {
                const res = await postMethod('/core/trainingAudit/cancel', JSON.stringify(
                    {
                        id: row.trainingId,
                    })
                );
                if (res.data.code === 200) {
                    this.$message({
                        type: 'success',
                        message: '撤销学习报告成功',
                    });
                    await this.getTrainingList();
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }
            }).catch();
        },
        async handleReportInfo(row) {
            const res = await postMethod('/core/trainingAudit/info', JSON.stringify(
                {
                    id: row.trainingId,
                })
            );

            this.finishFormVisible = true;
            if (res.data.code === 200) {
                console.log(res.data.data);
                this.selectTable = res.data.data;
                this.selectTable.reportContent = this.selectTable.content;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg,
                });
            }
        },
        handleReport(row) {
            this.selectTable = row;
            this.dialogFormVisible = true;
        },
        report() {
            this.$refs.reportForm.validate(async valid => {
                if (valid) {
                    const res = await postMethod('/core/trainingAudit/uploadLearnRecord', JSON.stringify(
                        {
                            trainingId: this.selectTable.trainingId,
                            content: this.selectTable.reportContent,
                            fileId: this.selectTable.fileId,
                        }
                    ));

                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '上报成功'
                        });

                        await this.getTrainingList();
                        this.dialogFormVisible = false;
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg,
                        });
                    }
                }
            })
        },
        handleFileUploadSuccess(response, file, fileList) {
            console.log(response)
            if (response.code === 200) {
                this.selectTable.fileId = response.data.fileId;
            }
        },
        handleFileRemove(file, fileList) {
            this.selectTable.fileId = "";
        }
    },
}
</script>

<style lang="less">
	@import '../style/mixin';
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
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
