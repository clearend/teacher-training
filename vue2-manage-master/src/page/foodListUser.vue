<template>
    <div class="fillcontain">
        <head-top></head-top>

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
<!--                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>-->
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
    import {postMethod} from "@/api/getDataLocal"
    export default {
        data(){
            return {
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                addDialogFormVisible: false,
                newTable: {},
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
                const res = await postMethod('/core/material/list', JSON.stringify(
                    {
                        pageRequest: {
                            currentPage: this.currentPage,
                            pageSize: this.limit,
                        }
                    }
                ));

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
            handleDownload(row) {
                // window.location.href = row.fileUrl
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
