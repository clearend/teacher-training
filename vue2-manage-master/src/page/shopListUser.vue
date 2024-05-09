<template>
    <div class="fillcontain">
        <head-top></head-top>

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
                    <el-form-item label="素材文件" label-width="100px" prop="fileId">
                        <el-upload
                                class="upload-demo"
                                action="http://localhost:8901/core/sysFile/upload"
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
    import {log} from "nightwatch/lib/util/logger";

    const demoLearnData = [
        {
            "name": "创新教育理念研讨会",
            "location": "北京大学教3-201室",
            "description": "探讨未来教育的创新发展方向",
            "status": "待报名",
        },
        {
            "name": "企业发展战略规划研讨会",
            "location": "清华大学经管楼B-101室",
            "description": "研究企业发展的战略性规划方法",
            "status": "已完成",
        },
        {
            "name": "科技创新前沿论坛",
            "location": "上海科技大学报告厅",
            "description": "探讨科技创新的前沿领域与趋势",
            "status": "待报名",
        },
        {
            "name": "社会心理学新进展讲座",
            "location": "南京大学社会科学楼304室",
            "description": "介绍社会心理学领域的新研究成果",
            "status": "已结束",
        },
        {
            "name": "法律法规解读研讨会",
            "location": "复旦大学法学院会议室",
            "description": "深入解析最新颁布的法律法规",
            "status": "已完成",
        },
        {
            "name": "医学科研技术交流会",
            "location": "北京协和医学院报告厅",
            "description": "分享医学科研技术的最新进展",
            "status": "待完成",
        },
        {
            "name": "互联网创业成功经验分享会",
            "location": "清华大学五道口金融学院会议室",
            "description": "交流互联网创业的成功经验与教训",
            "status": "已完成",
        },
        {
            "name": "环境保护与可持续发展论坛",
            "location": "上海交通大学环境学院报告厅",
            "description": "探讨环境保护与可持续发展的关键问题",
            "status": "待报名",
        },
        {
            "name": "人工智能应用前景研讨会",
            "location": "哈尔滨工业大学信息楼A-101室",
            "description": "探讨人工智能在各领域的应用前景",
            "status": "已完成",
        },
        {
            "name": "文学艺术创作技巧分享会",
            "location": "中央美术学院艺术创作中心",
            "description": "分享文学艺术创作的技巧与心得",
            "status": "待完成",
        },
        {
            "name": "金融投资新思路讲座",
            "location": "北京大学光华管理学院报告厅",
            "description": "探索金融投资的创新理念与策略",
            "status": "已结束",
        },
        {
            "name": "健康管理与养生知识讲座",
            "location": "上海复旦大学医学院报告厅",
            "description": "传授健康管理与养生的科学知识",
            "status": "待报名",
        },
        {
            "name": "教育技术应用研讨会",
            "location": "南京师范大学教育技术学院会议室",
            "description": "分享教育技术在课堂中的应用实践",
            "status": "已完成",
        },
        {
            "name": "人文社科研究成果汇报会",
            "location": "北京大学文史哲楼202室",
            "description": "汇报人文社科领域的研究成果",
            "status": "已结束",
        },
        {
            "name": "创业家成功经验交流会",
            "location": "清华大学科技园创业孵化中心",
            "description": "分享创业家的成功经验与启示",
            "status": "待报名",
        },
        {
            "name": "国际贸易发展趋势论坛",
            "location": "上海财经大学国际经济与贸易学院报告厅",
            "description": "探讨国际贸易发展的新趋势与挑战",
            "status": "已结束",
        },
        {
            "name": "心理健康与情绪管理讲座",
            "location": "南京师范大学心理学院报告厅",
            "description": "传授心理健康与情绪管理的技巧",
            "status": "待完成",
        },
        {
            "name": "环境保护技术应用研讨会",
            "location": "北京大学环境科学与工程学院会议室",
            "description": "探讨环境保护技术的应用与创新",
            "status": "已完成",
        },
        {
            "name": "智能制造技术发展趋势研讨会",
            "location": "清华大学智能制造研究中心报告厅",
            "description": "探讨智能制造技术的发展趋势与前景",
            "status": "待完成",
        },
        {
            "name": "文化艺术创意产业论坛",
            "location": "上海戏剧学院艺术创意中心",
            "description": "探讨文化艺术创意产业的发展与创新",
            "status": "已结束",
        },
        {
            "name": "金融科技创新发展讲座",
            "location": "南京大学金融学院报告厅",
            "description": "解析金融科技在金融行业中的应用与发展",
            "status": "待完成",
        },
    ]


    import headTop from '../components/headTop'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {postMethod} from "@/api/getDataLocal";
    export default {
        data() {
            return {
                baseUrl,
                baseImgPath,
                city: {},
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                categoryOptions: [],
                selectedCategory: [],
                address: {},
                addDialogFormVisible: false,
                newTable: {},
                finishFormVisible: false,
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
                const res = await postMethod('/core/training/list/user',  JSON.stringify({
                    pageRequest: {
                        currentPage: this.currentPage,
                        pageSize: this.limit,
                    },
                }));

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
            handleCancleJoin(row) {
                row.status = "待报名";
                this.$message({
                    type: 'success',
                    message: '取消报名成功'
                });
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
            handleDelete(index, row) {
                try{
                    demoLearnData.splice(this.offset + index, 1);
                    if (1) {
                        this.$message({
                            type: 'success',
                            message: '删除培训信息成功'
                        });
                        this.tableData.splice(index, 1);
                        this.tableData = demoLearnData.slice(this.offset, this.offset + this.limit);
                        this.count = demoLearnData.length;
                    }else{
                        throw new Error(res.message)
                    }
                }catch(err){
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除培训信息失败')
                }
            },
            updateLearn() {
                this.dialogFormVisible = false;
                try{
                    if (1) {
                        this.$message({
                            type: 'success',
                            message: '更新培训信息成功'
                        });
                        // this.getResturants();
                    }else{
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                }catch(err){
                    console.log('更新培训信息失败', err);
                }
            },
            handleAdd() {
                this.newTable = {
                    "name": "",
                    "location": "",
                    "description": "",
                };
                this.addDialogFormVisible = true;
            },
            addLearn() {
                if (this.newTable.name === "") {
                    this.$message({
                        type: 'error',
                        message: '培训主题不能为空'
                    });
                    return;
                }
                if (this.newTable.location === "") {
                    this.$message({
                        type: 'error',
                        message: '培训地点不能为空'
                    });
                    return;
                }
                if (this.newTable.description === "") {
                    this.$message({
                        type: 'error',
                        message: '培训描述不能为空'
                    });
                    return;
                }

                this.addDialogFormVisible = false;
                demoLearnData.splice(0, 0, this.newTable);
                this.count = demoLearnData.length;
                this.currentPage = 1;
                this.tableData = demoLearnData.slice(0, this.limit);

                this.$message({
                    type: 'success',
                    message: '添加成功'
                });
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
