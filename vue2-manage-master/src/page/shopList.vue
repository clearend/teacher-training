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

        <el-dialog title="新增培训信息" v-model="addDialogFormVisible">
            <el-form :model="newTable" ref="newTrainForm" :rules="rules">
                <el-form-item label="培训名称" label-width="100px" prop="trainingName">
                    <el-input v-model="newTable.trainingName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="培训地址" label-width="100px" prop="trainingAddress">
                    <el-input v-model="newTable.trainingAddress"></el-input>
                </el-form-item>
                <el-form-item label="培训时间" label-width="100px" prop="trainingTime">
                    <el-date-picker v-model="newTable.trainingTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="培训类型" label-width="100px" prop="trainingType">
                    <el-select v-model="newTable.trainingType" placeholder="请选择培训类型">
                        <el-option label="校级" value="100"></el-option>
                        <el-option label="省市级" value="200"></el-option>
                        <el-option label="国家级" value="300"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="培训内容" label-width="100px" prop="trainingContent">
                    <el-input type="textarea" v-model="newTable.trainingContent"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addLearn">确 定</el-button>
            </div>
        </el-dialog>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column label="培训名称" property="trainingName"></el-table-column>
                <el-table-column label="培训地点" property="trainingAddress"></el-table-column>
                <el-table-column label="培训时间" property="trainingTime"></el-table-column>
                <el-table-column label="培训类型" property="trainingType"></el-table-column>
                <el-table-column label="完成进度" property="trainingProgress"></el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">详情</el-button>
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

            <el-dialog title="培训详情" v-model="dialogFormVisible">
                <el-form :model="selectTable" ref="trainingInfoForm" :rules="rules">
                    <el-form-item label="培训标题" label-width="100px" prop="trainingName">
                        <el-input v-model="selectTable.trainingName" auto-complete="off" :disabled="updateTrainingInfoSwitch"></el-input>
                    </el-form-item>
                    <el-form-item label="培训地点" label-width="100px" prop="trainingAddress">
                        <el-input v-model="selectTable.trainingAddress" :disabled="updateTrainingInfoSwitch"></el-input>
                    </el-form-item>
                    <el-form-item label="培训时间" label-width="100px" prop="trainingTime">
                        <el-date-picker v-model="selectTable.trainingTime" type="datetime" placeholder="选择日期时间" :disabled="updateTrainingInfoSwitch"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="培训类型" label-width="100px" prop="trainingType">
                        <el-select v-model="selectTable.trainingType" placeholder="请选择培训类型" :disabled="updateTrainingInfoSwitch">
                            <el-option label="校级" value="100"></el-option>
                            <el-option label="省市级" value="200"></el-option>
                            <el-option label="国家级" value="300"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="培训内容" label-width="100px" prop="trainingContent" >
                        <el-input type="textarea" v-model="selectTable.trainingContent" :disabled="updateTrainingInfoSwitch"></el-input>
                    </el-form-item>
                </el-form>

                <h3 style="margin-bottom: 10px;">培训教师列表</h3>
                <el-row style="overflow: auto; text-align: left;">
                    <el-table
                        :data="selectTable.trainingUserList"
                        style="margin-bottom: 20px;"
                        :row-class-name="tableRowClassName">
                        <el-table-column prop="userName" label="教师姓名"></el-table-column>
                        <el-table-column prop="jobId" label="工号"></el-table-column>
                        <el-table-column prop="status" label="完成状态">
                            <template slot-scope="scope">
                                <el-button v-if="scope.row.status === '100'" size="small" type="info">待完成</el-button>
                                <el-button v-if="scope.row.status === '200'" size="small" type="success">已完成</el-button>
                                <el-button v-if="scope.row.status === '300'" size="small" type="danger">未完成</el-button>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" >
                        <template slot-scope="scope">
                            <el-button size="small" type="danger" @click="deleteItem(scope.row)">删除</el-button>
                        </template>
                        </el-table-column>
					</el-table>

                    <div style="text-align: center;">
                        <el-button type="primary" @click="addItemForm()" style="margin-bottom: 10px;">添加教师</el-button>
                    </div>
				</el-row>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="updateTrainingInfoSwitch === true ? dialogFormVisible = false : updateTrainingInfoSwitch = true">关 闭</el-button>
                    <el-button type="warning" @click="updateTrainingInfoSwitch = false" :disabled="!updateTrainingInfoSwitch">编 辑</el-button>
                    <el-button type="primary" @click="updateLearn" :disabled="updateTrainingInfoSwitch">确 定</el-button>
                </div>
            </el-dialog>

            <el-dialog title="添加教师" v-model="specsFormVisible">
                <!-- <el-form :model="addItemList"> -->

                    <el-select v-model="selectedTeachers" multiple placeholder="请选择">
                        <el-option
                            v-for="item in teacherList" :key="item.userId"
                                :label="item.userName + ' ' + item.jobId"
                                :value="item.userId">
                        </el-option>
                    </el-select>

                    <!-- <el-form-item label="规格" label-width="100px" prop="specs">
                        <el-input v-model="addItemList.specs" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="包装费" label-width="100px">
						<el-input-number v-model="addItemList.packing_fee" :min="0" :max="100"></el-input-number>
					</el-form-item>
					<el-form-item label="价格" label-width="100px">
						<el-input-number v-model="addItemList.price" :min="0" :max="10000"></el-input-number>
					</el-form-item> -->
                <!-- </el-form> -->

                <div slot="footer" class="dialog-footer">
                    <el-button @click="specsFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addItem">确 定</el-button>
                </div>

			</el-dialog>

        </div>
    </div>
</template>

<script>

    import headTop from '../components/headTop'
    import {postMethod} from "@/api/getDataLocal";
    import de from "element-ui/lib/locale/lang/de";
    export default {
        data() {
            return {
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
                specsFormVisible: false,
                addItemList: {},
                teacherList: [],
                selectedTeachers: [],
                updateTrainingInfoSwitch: true,
                rules: {
                    trainingName: [{required: true, message: '请输入培训名称', trigger: 'blur'}],
                    trainingAddress: [{required: true, message: '请输入培训地址', trigger: 'blur'}],
                    trainingTime: { type: 'date', required: true, message: '请选择日期', trigger: 'change' },
                    trainingType: [{required: true, message: '请选择培训类型', trigger: 'blur'}],
                    trainingContent: [{required: true, message: '请填写培训内容', trigger: 'blur'}],
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
                const res = await postMethod('/core/training/findTrainList',  JSON.stringify({
                    pageRequest: {
                        currentPage: this.currentPage,
                        pageSize: this.limit,
                    },
                }));

                if (res.data.code === 200) {
                    this.count = res.data.data.count;
                    this.tableData = res.data.data.trainingList;
                    this.tableData.forEach(item => {
                        item.trainingProgress = item.trainingProgress + "%";
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
                this.getTrainingList();
            },
            async handleEdit(row) {
                this.updateTrainingInfoSwitch = true;
                await this.getTrainingInfo(row.trainingId)

                this.dialogFormVisible = true;
            },
            async getTrainingInfo(trainingId) {
                const res = await postMethod("/core/training/info", JSON.stringify(
                    {
                        id: trainingId,
                    }
                ));

                if (res.data.code === 200) {
                    this.selectTable = res.data.data;
                    switch (this.selectTable.trainingType) {
                        case "校级": this.selectTable.trainingType = "100"; break;
                        case "省市级": this.selectTable.trainingType = "200"; break;
                        case "国家级": this.selectTable.trainingType = "300"; break;
                        default: this.selectTable.trainingType = "100";
                    }
                    this.selectTable.trainingTime = new Date(this.selectTable.trainingTime);
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }
            },
            handleDelete(row) {
                this.$confirm("确定要删除该培训？", "提示", {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    showCancelButton: true,
                    showClose: false,
                    closeOnClickModal: false
                }).then(async () => {
                    const res = await postMethod('/core/training/delete', JSON.stringify({id: row.trainingId}));
                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '删除成功',
                        });
                        await this.getTrainingList();
                    } else {
                        this.$message({
                            type: 'error',
                            message: '删除失败',
                        });
                    }
                }).catch();
            },
            updateLearn() {
                this.$refs.trainingInfoForm.validate(async (valid) => {
                    if (valid) {
                        var updateTrainingInfo = this.selectTable
                        updateTrainingInfo.trainingTime = updateTrainingInfo.trainingTime.valueOf();

                        console.log(updateTrainingInfo)

                        const res = await postMethod('/core/training/update', JSON.stringify(updateTrainingInfo));
                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '更新培训信息成功'
                            });

                            this.updateTrainingInfoSwitch = true;
                            await this.getTrainingInfo(updateTrainingInfo.trainingId);
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg,
                            });
                        }
                    }
                });
            },
            handleAdd() {
                this.newTable = {
                    trainingName: "",
                    trainingAddress: "",
                    trainingContent: "",
                    trainingTime: "",
                    trainingType: "",
                };
                this.addDialogFormVisible = true;
            },
            addLearn() {
                this.$refs.newTrainForm.validate(async valid => {
                    if (valid) {
                        const newTrain = this.newTable;
                        newTrain.trainingTime = newTrain.trainingTime.valueOf();
                        newTrain.trainingType = Number(newTrain.trainingType)
                        console.log(newTrain);
                        const res = await postMethod('/core/training/create', JSON.stringify(newTrain));

                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '添加成功',
                            });

                            this.addDialogFormVisible = false;
                            await this.getTrainingList()
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg,
                            });
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: '请填写完整信息'
                        });
                    }
                })

                // this.addDialogFormVisible = false;
                // demoLearnData.splice(0, 0, this.newTable);
                // this.count = demoLearnData.length;
                // this.currentPage = 1;
                // this.tableData = demoLearnData.slice(0, this.limit);
                //
                // this.$message({
                //     type: 'success',
                //     message: '添加成功'
                // });
            },
            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            deleteItem(row) {
                this.$confirm("确定要删将该用户从培训中移除？", "提示", {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    showCancelButton: true,
                    showClose: false,
                    closeOnClickModal: false
                }).then(async () => {
                    const res = await postMethod('/core/trainingUser/deletePerson', JSON.stringify(
                        {
                            trainingId: this.selectTable.trainingId,
                            userId: row.userId,
                        })
                    );
                    if (res.data.code === 200) {
                        this.$message({
                            type: 'success',
                            message: '删除成功',
                        });
                        await this.getTrainingInfo(this.selectTable.trainingId);
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg,
                        });
                    }
                }).catch();

            },
            async addItem() {

                const res = await postMethod('/core/trainingUser/addPerson', JSON.stringify({
                    trainingId: this.selectTable.trainingId,
                    userList: this.selectedTeachers,
                }));

                if (res.data.code === 200) {
                    this.$message({
                        type: 'success',
                        message: '添加成功'
                    });

                    await this.getTrainingInfo(this.selectTable.trainingId);
                } else {
                    this.$message({
                        type: 'error',
                        message: res.data.msg,
                    });
                }

                this.specsFormVisible = false;

            },
            async addItemForm() {
                this.selectedTeachers = [];
                const res = await postMethod('/core/training/notInUserList', JSON.stringify({
                    id: this.selectTable.trainingId,
                }));
                if (res.data.code === 200) {
                    this.teacherList = res.data.data;
                }

                this.specsFormVisible = true;
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
