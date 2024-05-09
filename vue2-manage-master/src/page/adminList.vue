<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column prop="trainingName" label="培训标题"></el-table-column>
                <el-table-column prop="trainingType" label="培训类型"></el-table-column>
                <el-table-column prop="userName" label="提交人"></el-table-column>
                <el-table-column prop="learnTime" label="学习时长"></el-table-column>
                <el-table-column prop="auditStatus" label="审核状态">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.auditStatus === '审核中'" size="small" type="warning">审核中</el-button>
                        <el-button v-if="scope.row.auditStatus === '已通过'" size="small" type="success">已通过</el-button>
                        <el-button v-if="scope.row.auditStatus === '未通过'" size="small" type="danger">未通过</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button type="primary" size="small" @click="handleDetail(scope.row)">查看详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left; margin-top: 10px;">
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

        <el-dialog title="培训报告详情" v-model="auditInfoDialogVisible" :show-close="true">
            <el-form :model="selectTable" ref="auditInfoForm" :rules="rules">
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
                <el-form-item label="学习时长" label-width="100px">
                    <el-input v-model="selectTable.learnTime" :disabled="true"></el-input>
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
                <el-form-item  label="审批意见" label-width="100px" prop="auditRemark">
                    <el-input v-model="selectTable.auditRemark" type="textarea" :disabled="selectTable.auditStatus === '已通过' || selectTable.auditStatus === '未通过'"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="auditInfoDialogVisible = false">取 消</el-button>
                <el-button v-if="selectTable.auditStatus === '已通过' || selectTable.auditStatus === '未通过'" type="primary" @click="auditInfoDialogVisible = false">确 定</el-button>
                <el-button v-if="selectTable.auditStatus === '审核中'" type="success" @click="handleAudit(1)">通 过</el-button>
                <el-button v-if="selectTable.auditStatus === '审核中'" type="danger" @click="handleAudit(0)">驳 回</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {postMethod} from "@/api/getDataLocal";

    export default {
        data() {
            return {
                tableData: [],
                currentRow: null,
                offset: 0,
                limit: 15,
                count: 0,
                currentPage: 1,
                detailDialogVisible: false,
                selectTable: {},
                auditInfoDialogVisible: false,
                rules: {
                    auditRemark: [{required: true, message: "请输入审核意见", trigger: 'blur'}],
                }
            }
        },
        components: {
            headTop,
        },
        created() {
            this.initData();
        },
        methods: {
            async initData(){
                try {
                    await this.getAuditList();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getAuditList()
            },
            async getAuditList() {
                const res = await postMethod('/core/trainingAudit/list', JSON.stringify({
                    pageRequest: {
                        currentPage: this.currentPage,
                        pageSize: this.limit,
                    }
                }));
                if (res.data.code === 200) {
                    this.tableData = res.data.data.trainingAuditList;
                    this.count = res.data.data.count;
                }
            },
            async handleAudit(result) {
                this.$refs.auditInfoForm.validate(async valid => {
                    if (valid) {
                        const res = await postMethod('/core/trainingAudit/examineTraining', JSON.stringify({
                            trainingAuditId: this.selectTable.trainingAuditId,
                            auditResult: result,
                            auditRemark: this.selectTable.auditRemark,
                        }));

                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '审批成功',
                            });

                            await this.getAuditList();
                            this.auditInfoDialogVisible = false;
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg,
                            });
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: '请将信息填写完整',
                        });
                    }
                });
            },
            async handleDetail(row) {
                const res = await postMethod('/core/trainingAudit/info', JSON.stringify(
                    {
                        id: row.trainingAuditId,
                    })
                );

                this.auditInfoDialogVisible = true;
                if (res.data.code === 200) {
                    this.selectTable = res.data.data;
                    this.selectTable.reportContent = this.selectTable.content;
                    this.selectTable.learnTime = row.learnTime;
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }

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


