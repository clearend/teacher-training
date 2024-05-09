<template>
    <div class="fillcontain">
        <head-top></head-top>

        <div style="text-align: right; padding-right: 40px; margin-top: 10px;">
            <el-button
                    size="small"
                    type="success"
                    style="padding-left: 10px; padding-right: 10px;"
                    @click="handleAdd">我要上传</el-button>
        </div>

        <el-dialog title="上传素材信息" v-model="addDialogFormVisible">
            <el-form :model="newTable" ref="newMaterialForm" :rules="rules">
                <el-form-item label="素材文件" label-width="100px" prop="fileId">
                    <el-upload
                            class="upload-demo"
                            action="http://114.116.252.42/server/core/sysFile/upload"
                            :on-success="handleFileUploadSuccess"
                            :on-remove="handleFileRemove">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">文件大小不超过100MB</div>
                    </el-upload>
                </el-form-item>

                <el-form-item label="素材名称" label-width="100px" prop="materialName">
                    <el-input v-model="newTable.materialName" auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="素材类型" label-width="100px" prop="materialType">
                    <el-select v-model="newTable.materialType" placeholder="请选择素材类型">
                        <el-option label="文档" value="0"></el-option>
                        <el-option label="图片" value="1"></el-option>
                        <el-option label="视频" value="2"></el-option>
                        <el-option label="音频" value="3"></el-option>
                        <el-option label="其他" value="4"></el-option>
                    </el-select>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCancelAdd">取 消</el-button>
                <el-button type="primary" @click="addFile">确 定</el-button>
            </div>
        </el-dialog>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
			    :data="tableData"
			    highlight-current-row
			    style="width: 100%">
                <el-table-column label="素材名称" prop="materialName"></el-table-column>
                <el-table-column label="素材类型" prop="materialType"></el-table-column>
                <el-table-column label="上传用户" prop="userName"></el-table-column>
                <el-table-column label="上传日期" prop="uploadTime"></el-table-column>
                <el-table-column label="审批状态" prop="auditStatus">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.auditStatus === '审核中'" size="small" type="warning">审核中</el-button>
                        <el-button v-if="scope.row.auditStatus === '已通过'" size="small" type="success">已发布</el-button>
                        <el-button v-if="scope.row.auditStatus === '未通过'" size="small" type="danger">未通过</el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="small" type="info" @click="handleAuditInfo(scope.row)">查看详情</el-button>
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

        <el-dialog title="素材审批详情" v-model="auditInfoDialogVisible" :show-close="true">
            <el-form :model="selectTable" ref="auditInfoForm">
                <el-form-item label="素材名称" label-width="100px">
                    <el-input v-model="selectTable.materialName" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="素材类型" label-width="100px">
                    <el-input v-model="selectTable.materialType" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="用户名称" label-width="100px">
                    <el-input v-model="selectTable.userName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="上传时间" label-width="100px">
                    <el-input v-model="selectTable.uploadTime" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="审批状态" label-width="100px">
                    <el-input v-model="selectTable.auditStatus" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="下载链接" label-width="100px">
                    <a :href="selectTable.fileUrl" :download="selectTable.fileName">{{selectTable.fileName}}</a>
                </el-form-item>

                <el-form-item label="审批意见" label-width="100px">
                    <el-input v-model="selectTable.auditRemark" type="textarea" :disabled="selectTable.auditStatus !== '审核中'"></el-input>
                </el-form-item>

                <el-form-item v-if="selectTable.auditStatus !== '审核中'" label="审批时间" label-width="100px">
                    <el-input v-model="selectTable.auditTime" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleAuditCancel">取 消</el-button>
                <el-button type='info' @click="handleAuditCancel">确 定</el-button>

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
                restaurant_id: null,
                expendRow: [],
                addDialogFormVisible: false,
                auditInfoDialogVisible: false,
                newTable: {},
                selectTable: {},
                rules: {
                    fileId: [{required: true, message: "请上传素材文件", trigger: 'blur'}],
                    materialName: [{required: true, message: "请输入素材名称", trigger: 'blur'}],
                    materialType: [{required: true, message: "请选择素材类型", trigger: 'blur'}],
                }
            }
        },
    	components: {
    		headTop,
    	},
        created(){
        	// this.restaurant_id = this.$route.query.restaurant_id;
            this.initData();
        },
        mounted(){

        },
        methods: {
            async getMaterialAuditList() {
                const res = await postMethod('/core/materialAudit/list', JSON.stringify(
                    {
                        pageRequest: {
                            currentPage: this.currentPage,
                            pageSize: this.limit,
                        }
                    }
                ));

                console.log(res.data.data.materialAuditList);
                if (res.data.code === 200) {
                    this.tableData = res.data.data.materialAuditList;
                    this.count = res.data.data.count;
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg
                    });
                }
            },
            async initData(){
                await this.getMaterialAuditList();
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            async handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1) * this.limit;
                await this.getMaterialAuditList()
            },
            handleAuditInfo(row) {
                this.selectTable = row;
                this.auditInfoDialogVisible = true;
            },
            handleAuditCancel() {
                this.auditInfoDialogVisible = false;
                this.selectTable = {};
            },
            handleAdd() {
                this.newTable = {
                    "fileId": "",
                    "materialName": "",
                    "materialType": "",
                };
                this.addDialogFormVisible = true;
            },
            addFile() {
                this.$refs.newMaterialForm.validate(async valid => {
                    if (valid) {
                        const res = await postMethod('/core/material/upload', JSON.stringify(this.newTable));

                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: "上传成功，等待管理员审核发布",
                            });

                            this.addDialogFormVisible = false;
                            await this.getMaterialAuditList();
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: "请填写相关信息"
                        });
                    }
                });
            },
            handleCancelAdd() {
                this.newTable = {
                    "fileId": "",
                    "materialName": "",
                    "materialType": "",
                };
                this.addDialogFormVisible = false;
            },
            handleFileUploadSuccess(response, file, fileList) {
                console.log(response)
                if (response.code === 200) {
                    this.newTable.fileId = response.data.fileId;
                    this.newTable.materialName = response.data.fileName;
                }
            },
            handleFileRemove(file, fileList) {
                this.newTable.fileId = null;
                this.newTable.materialName = null;
            },

        },
    }
</script>

<style lang="less">
	@import '../style/mixin';
    .table_container{
        padding: 20px;
    }
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
</style>
