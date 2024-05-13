<template>
    <div class="fillcontain">
        <head-top></head-top>

        <el-row style="padding-top: 10px; padding-left: 20px;">
            <el-col :span="20">
                <el-row :gutter="10">
                    <el-col :span="4"><el-input v-model="tableFilter.materialName" placeholder="素材名称"></el-input></el-col>
                    <el-col :span="3">
                        <el-select v-model="tableFilter.materialType" placeholder="素材类型">
                            <el-option label="文档" value="0"></el-option>
                            <el-option label="图片" value="1"></el-option>
                            <el-option label="视频" value="2"></el-option>
                            <el-option label="音频" value="3"></el-option>
                            <el-option label="其他" value="4"></el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="4">
                        <el-button type="info" @click="handleFilterQuery">查 询</el-button>
                        <el-button type="warning" @click="handleFilterClear">清 空</el-button>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="4">
                <div style="text-align: right; padding-right: 40px;">
                    <el-button
                        type="success"
                        style="padding-left: 10px; padding-right: 10px;"
                        @click="handleAdd">上 传</el-button>
                </div>
            </el-col>
        </el-row>

        <el-dialog title="上传素材信息" v-model="addDialogFormVisible">
            <el-form :model="newTable" ref="newMaterialForm" :rules="rules">
                <el-form-item label="素材文件" label-width="100px" prop="fileId">
                    <el-upload
                            class="upload-demo"
                            :action="uploadUrl"
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

                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column label="素材名称" prop="materialName"></el-table-column>
                <el-table-column label="素材类型" prop="materialType" width="200"></el-table-column>
                <el-table-column label="上传用户" prop="userName" width="200"></el-table-column>
                <el-table-column label="上传日期" prop="uploadTime" width="200"></el-table-column>
                <el-table-column label="操作" width="300">
                  <template slot-scope="scope">
                    <el-button size="small" @click="handleDownload(scope.row)">下载</el-button>
<!--                    <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>-->
                      <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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

        </div>
    </div>
</template>

<script>

import headTop from '../components/headTop'
import {uploadUrl} from "@/config/env";
import {postMethod} from "@/api/getDataLocal";

export default {
    computed: {
        upload() {
            return upload
        }
    },
    data(){
        return {
            uploadUrl: uploadUrl,
            offset: 0,
            limit: 15,
            count: 0,
            tableData: [],
            currentPage: 1,
            addDialogFormVisible: false,
            newTable: {},
            tableFilter: {
                materialName: "",
                materialType: "",
            },
            rules: {
                fileId: [{required: true, message: "请上传素材文件", trigger: 'blur'}],
                materialName: [{required: true, message: "请输入素材名称", trigger: 'blur'}],
                materialType: [{required: true, message: "请选择素材类型", trigger: 'blur'}],
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
        async getMaterialList() {
            const pageRequest = {
                currentPage: this.currentPage,
                pageSize: this.limit,
            };
            const query = {
                materialName: this.tableFilter.materialName,
                materialType: this.tableFilter.materialType,
                pageRequest: pageRequest,
            };

            const res = await postMethod('/core/material/list', JSON.stringify(query));

            if (res.data.code === 200) {
                this.tableData = res.data.data.materialList;
                this.count = res.data.data.count;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg
                });
            }
        },
        async initData() {
            await this.getMaterialList();
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            this.getMaterialList();
        },
        async handleFilterQuery() {
            this.currentPage = 1;
            await this.getMaterialList();
        },
        async handleFilterClear() {
            this.tableFilter = {
                materialName: "",
                materialType: "",
            };
            this.currentPage = 1;
            await this.getMaterialList();
        },
        handleDelete(row) {
            this.$confirm("确定要删除该素材？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                showCancelButton: true,
                showClose: false,
                closeOnClickModal: false
            }).then(async () => {
                const res = await postMethod('/core/material/delete', JSON.stringify({id: row.materialId}));
                if (res.data.code === 200) {
                    this.$message({
                        type: 'success',
                        message: '删除成功',
                    });

                    this.currentPage = 1;
                    await this.getMaterialList();
                } else {
                    this.$message({
                        type: 'error',
                        message: '删除失败',
                    });
                }
            }).catch();
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
                            message: "上传成功",
                        });

                        this.addDialogFormVisible = false;
                        await this.getMaterialList();
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: "请填写相关信息"
                    });
                }
            });
        },
        handleFileUploadSuccess(response, file, fileList) {
            console.log(response)
            if (response.code === 200) {
                this.newTable.fileId = response.data.fileId;
                this.newTable.materialName = response.data.fileName;
            }
        },
        handleCancelAdd() {
            this.newTable = {
                "fileId": "",
                "materialName": "",
                "materialType": "",
            };
            this.addDialogFormVisible = false;
        },
        handleFileRemove(file, fileList) {
            this.newTable.fileId = null;
            this.newTable.materialName = null;
        },
        handleDownload(row) {
            // window.location.href = baseImgPath + row.file_path
            window.open(row.fileUrl)
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
