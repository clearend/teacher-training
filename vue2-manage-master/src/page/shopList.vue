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
            <el-form :model="newTable">
                <el-form-item label="培训主题" label-width="100px">
                    <el-input v-model="newTable.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="培训地点" label-width="100px">
                    <el-input v-model="newTable.location"></el-input>
                </el-form-item>
                <el-form-item label="培训描述" label-width="100px">
                    <el-input v-model="newTable.description"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addLearn">确 定</el-button>
            </div>
        </el-dialog>

        <div class="table_container"  style="padding-top: 10px;">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column label="培训主题" property="name"></el-table-column>
                <el-table-column label="培训地点" property="location"></el-table-column>
                <el-table-column label="培训描述" property="description"></el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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

            <el-dialog title="修改培训信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="培训主题" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="培训地点" label-width="100px">
                        <el-input v-model="selectTable.location"></el-input>
                    </el-form-item>
                    <el-form-item label="培训描述" label-width="100px">
                        <el-input v-model="selectTable.description"></el-input>
                    </el-form-item>
                </el-form>

                <h3 style="margin-bottom: 10px;">培训教师列表</h3>
                <el-row style="overflow: auto; text-align: left;">

                    <el-table
                        :data="selectTable.trainList"
                        style="margin-bottom: 20px;"
                        :row-class-name="tableRowClassName">
                        <el-table-column prop="name" label="教师姓名"></el-table-column>
                        <el-table-column prop="jobId" label="工号"></el-table-column>
                        <el-table-column prop="date" label="报名日期"></el-table-column>
                        <el-table-column label="操作" >
                        <template slot-scope="scope">
                            <el-button size="small" type="danger" @click="deleteItem(scope.$index)">删除</el-button>
                        </template>
                        </el-table-column>
					</el-table>

                    <div style="text-align: center;">
                        <el-button type="primary" @click="addItemForm()" style="margin-bottom: 10px;">添加教师</el-button>
                    </div>
				</el-row>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateLearn">确 定</el-button>
                </div>
            </el-dialog>

            <el-dialog title="添加教师" v-model="specsFormVisible">
                <!-- <el-form :model="addItemList"> -->

                    <el-select v-model="selectedTeachers" multiple placeholder="请选择">
                        <el-option
                            v-for="item in teacherList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
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
    const demoLearnData = [
        {
            "name": "创新教育理念研讨会",
            "location": "北京大学教3-201室",
            "description": "探讨未来教育的创新发展方向"
        },
        {
            "name": "企业发展战略规划研讨会",
            "location": "清华大学经管楼B-101室",
            "description": "研究企业发展的战略性规划方法"
        },
        {
            "name": "科技创新前沿论坛",
            "location": "上海科技大学报告厅",
            "description": "探讨科技创新的前沿领域与趋势"
        },
        {
            "name": "社会心理学新进展讲座",
            "location": "南京大学社会科学楼304室",
            "description": "介绍社会心理学领域的新研究成果"
        },
        {
            "name": "法律法规解读研讨会",
            "location": "复旦大学法学院会议室",
            "description": "深入解析最新颁布的法律法规"
        },
        {
            "name": "医学科研技术交流会",
            "location": "北京协和医学院报告厅",
            "description": "分享医学科研技术的最新进展"
        },
        {
            "name": "互联网创业成功经验分享会",
            "location": "清华大学五道口金融学院会议室",
            "description": "交流互联网创业的成功经验与教训"
        },
        {
            "name": "环境保护与可持续发展论坛",
            "location": "上海交通大学环境学院报告厅",
            "description": "探讨环境保护与可持续发展的关键问题"
        },
        {
            "name": "人工智能应用前景研讨会",
            "location": "哈尔滨工业大学信息楼A-101室",
            "description": "探讨人工智能在各领域的应用前景"
        },
        {
            "name": "文学艺术创作技巧分享会",
            "location": "中央美术学院艺术创作中心",
            "description": "分享文学艺术创作的技巧与心得"
        },
        {
            "name": "金融投资新思路讲座",
            "location": "北京大学光华管理学院报告厅",
            "description": "探索金融投资的创新理念与策略"
        },
        {
            "name": "健康管理与养生知识讲座",
            "location": "上海复旦大学医学院报告厅",
            "description": "传授健康管理与养生的科学知识"
        },
        {
            "name": "教育技术应用研讨会",
            "location": "南京师范大学教育技术学院会议室",
            "description": "分享教育技术在课堂中的应用实践"
        },
        {
            "name": "人文社科研究成果汇报会",
            "location": "北京大学文史哲楼202室",
            "description": "汇报人文社科领域的研究成果"
        },
        {
            "name": "创业家成功经验交流会",
            "location": "清华大学科技园创业孵化中心",
            "description": "分享创业家的成功经验与启示"
        },
        {
            "name": "国际贸易发展趋势论坛",
            "location": "上海财经大学国际经济与贸易学院报告厅",
            "description": "探讨国际贸易发展的新趋势与挑战"
        },
        {
            "name": "心理健康与情绪管理讲座",
            "location": "南京师范大学心理学院报告厅",
            "description": "传授心理健康与情绪管理的技巧"
        },
        {
            "name": "环境保护技术应用研讨会",
            "location": "北京大学环境科学与工程学院会议室",
            "description": "探讨环境保护技术的应用与创新"
        },
        {
            "name": "智能制造技术发展趋势研讨会",
            "location": "清华大学智能制造研究中心报告厅",
            "description": "探讨智能制造技术的发展趋势与前景"
        },
        {
            "name": "文化艺术创意产业论坛",
            "location": "上海戏剧学院艺术创意中心",
            "description": "探讨文化艺术创意产业的发展与创新"
        },
        {
            "name": "金融科技创新发展讲座",
            "location": "南京大学金融学院报告厅",
            "description": "解析金融科技在金融行业中的应用与发展"
        },
    ]
    const demoTrainList = [
        {
            name: "张三",
            jobId: "2019001",
            date: "2020-01-01",
        },
        {
            name: "李四",
            jobId: "2019002",
            date: "2020-01-02",
        },
        {
            name: "王五",
            jobId: "2019003",
            date: "2020-01-03",
        },
    ]
    const demoTeacherList = [
        {
            name: "李占峰",
            jobId: "2019001",
        },
        {
            name: "张永年",
            jobId: "2019002",
        },
        {
            name: "崔宇",
            jobId: "2019003",
        },
        {
            name: "周志华",
            jobId: "2019004",
        },
        {
            name: "李建华",
            jobId: "2019005",
        },
        {
            name: "王小云",
            jobId: "2019006",
        },
    ]


    import headTop from '../components/headTop'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {cityGuess, getResturants, getResturantsCount, foodCategory, updateResturant, searchplace, deleteResturant} from '@/api/getData'
    export default {
        data(){
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
                specsFormVisible: false,
                addItemList: {},
                teacherList: [],
                selectedTeachers: []
            }
        },
        created(){
            this.initData();
        },
        components: {
            headTop,
        },
        methods: {
            initData(){
                this.getLearns();
                this.count = demoLearnData.length;
            },
            async getLearns(){
                this.tableData = demoLearnData.slice(this.offset, this.offset + this.limit);
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getLearns()
            },
            handleEdit(row) {
                // console.log(row)
                this.selectTable = row;
                this.selectTable.trainList = demoTrainList;
                this.dialogFormVisible = true;
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
            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            deleteItem(index) {
                this.selectTable.trainList.splice(index, 1);
                this.$message({
                    type: 'success',
                    message: '删除成功'
                });
                // this.selectTable.trainList = demoTrainList;
            },
            addItem() {
                // this.addItemList = {
                //     "name": "",
                //     "jobId": "",
                //     "date": "",
                // };

                let now = new Date();
                this.selectedTeachers.forEach(element => {
                    let newTeacher = demoTeacherList[element];
                    this.selectTable.trainList.push({
                        name: newTeacher.name,
                        jobId: newTeacher.jobId,
                        date: now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate(),
                    });
                });

                // this.selectTable.trainList.push(this.addItemList);
                this.specsFormVisible = false;
                this.$message({
                    type: 'success',
                    message: '添加成功'
                });
            },
            addItemForm() {
                this.teacherList = [];
                this.selectedTeachers = [];
                demoTeacherList.forEach((element, index) => {
                    this.teacherList.push({
                        key: element.jobId,
                        value: index,
                        label: element.name + element.jobId,
                    })
                });
                // console.log(this.teacherList);
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
