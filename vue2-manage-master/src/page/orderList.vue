<template>
    <div class="fillcontain">
        <head-top></head-top>

<!--        <el-dialog title="新增提醒信息" v-model="addDialogFormVisible">-->
<!--            <el-form :model="newTable">-->
<!--                <el-form-item label="被提示人" label-width="100px">-->
<!--&lt;!&ndash;                    <el-input v-model="newTable.username" auto-complete="off"></el-input>&ndash;&gt;-->
<!--                    <el-select v-model="newTable.username" placeholder="请选择">-->
<!--                        <el-option-->
<!--                            v-for="item in newTable.ansUserList"-->
<!--                            :key="item.value"-->
<!--                            :label="item.label"-->
<!--                            :value="item.value">-->
<!--                        </el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="提示内容" label-width="100px">-->
<!--                    <el-input v-model="newTable.content"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="提示时间" label-width="100px">-->
<!--                    <el-input v-model="newTable.noticeDate"></el-input>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--            <div slot="footer" class="dialog-footer">-->
<!--                <el-button @click="addDialogFormVisible = false">取 消</el-button>-->
<!--                <el-button type="primary" @click="addUser">确 定</el-button>-->
<!--            </div>-->
<!--        </el-dialog>-->

        <div class="table_container" style="padding-top: 10px;">
            <el-table
			    :data="tableData"
			    highlight-current-row
			    style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
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
            <el-form :model="selectTable" ref="auditInfoForm" :rules="rules">
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

                <el-form-item v-if="selectTable.auditStatus !== '审核中'" label="审批意见" label-width="100px">
                    <el-input v-model="selectTable.auditRemark" type="textarea" :disabled="true"></el-input>
                </el-form-item>

                <el-form-item v-if="selectTable.auditStatus !== '审核中'" label="审批时间" label-width="100px">
                    <el-input v-model="selectTable.auditTime" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleAuditCancel">取 消</el-button>
                <el-button type="danger" @click="handleAudit(0)">驳 回</el-button>
                <el-button type="success" @click="handleAudit(1)">通 过</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import fa from "element-ui/lib/locale/lang/fa";

    const demoNotifyData = [
        {
            "username": "王小明",
            "content": "请记得完成今天的在线学习任务",
            "noticeDate": "2024-04-05 18:30:00"
        },
        {
            "username": "李娜",
            "content": "别忘了观看今天的课程视频",
            "noticeDate": "2024-04-10 20:15:00"
        },
        {
            "username": "张伟",
            "content": "请务必在截止日期前完成作业提交",
            "noticeDate": "2024-04-15 17:45:00"
        },
        {
            "username": "刘洋",
            "content": "今天有重要的在线讨论活动，请准时参加",
            "noticeDate": "2024-04-20 16:00:00"
        },
        {
            "username": "陈艳",
            "content": "请确保阅读完本周的课程材料",
            "noticeDate": "2024-04-25 19:30:00"
        },
        {
            "username": "赵宇航",
            "content": "记得完成本周的学习任务",
            "noticeDate": "2024-04-02 14:00:00"
        },
        {
            "username": "孙梦琪",
            "content": "今天是提交作业的截止日期，请及时完成",
            "noticeDate": "2024-04-07 21:00:00"
        },
        {
            "username": "周阳光",
            "content": "请认真复习今天的课程内容",
            "noticeDate": "2024-04-12 16:45:00"
        },
        {
            "username": "吴丽丽",
            "content": "别忘了参加今天的小组讨论",
            "noticeDate": "2024-04-17 19:20:00"
        },
        {
            "username": "郑小凯",
            "content": "请查看今天的课程公告",
            "noticeDate": "2024-04-22 11:30:00"
        },
        {
            "username": "马晓洁",
            "content": "记得及时提交作业",
            "noticeDate": "2024-04-27 22:10:00"
        },
        {
            "username": "黄晓雯",
            "content": "请务必完成今天的在线测验",
            "noticeDate": "2024-04-03 17:00:00"
        },
        {
            "username": "朱小红",
            "content": "别忘了下载本周的课程资料",
            "noticeDate": "2024-04-08 14:45:00"
        },
        {
            "username": "刘小勇",
            "content": "请尽快完成今天的课程笔记",
            "noticeDate": "2024-04-13 12:20:00"
        },
        {
            "username": "吕小芳",
            "content": "今天有重要的在线讨论，请准时参加",
            "noticeDate": "2024-04-18 18:30:00"
        },
        {
            "username": "张小敏",
            "content": "请确认已阅读本周的课程公告",
            "noticeDate": "2024-04-23 15:00:00"
        },
        {
            "username": "李小刚",
            "content": "记得在截止日期前完成作业提交",
            "noticeDate": "2024-04-28 20:40:00"
        },
        {
            "username": "王小婷",
            "content": "别忘了观看今天的课程视频",
            "noticeDate": "2024-04-04 19:15:00"
        },
        {
            "username": "赵小涵",
            "content": "请确保阅读完本周的课程材料",
            "noticeDate": "2024-04-09 14:30:00"
        },
        {
            "username": "孙小洋",
            "content": "今天有重要的小组讨论，请准时参加",
            "noticeDate": "2024-04-14 17:20:00"
        },
        {
            "username": "周小倩",
            "content": "请务必在截止日期前完成作业提交",
            "noticeDate": "2024-04-19 21:50:00"
        },
        {
            "username": "吴小雨",
            "content": "今天是提交作业的截止日期，请及时完成",
            "noticeDate": "2024-04-24 16:40:00"
        },
        {
            "username": "郑小楠",
            "content": "请认真复习今天的课程内容",
            "noticeDate": "2024-04-29 18:10:00"
        },
        {
            "username": "马小明",
            "content": "别忘了参加今天的小组讨论",
            "noticeDate": "2024-04-01 20:30:00"
        },
        {
            "username": "黄小静",
            "content": "请查看今天的课程公告",
            "noticeDate": "2024-04-06 13:50:00"
        },
        {
            "username": "朱小蕾",
            "content": "请记得及时提交作业",
            "noticeDate": "2024-04-11 22:25:00"
        },
        {
            "username": "刘小峰",
            "content": "请务必完成今天的在线测验",
            "noticeDate": "2024-04-16 16:15:00"
        },
        {
            "username": "吕小明",
            "content": "别忘了下载本周的课程资料",
            "noticeDate": "2024-04-21 18:05:00"
        },
        {
            "username": "张小娟",
            "content": "请尽快完成今天的课程笔记",
            "noticeDate": "2024-04-26 11:55:00"
        },
        {
            "username": "李小红",
            "content": "今天有重要的在线讨论，请准时参加",
            "noticeDate": "2024-04-30 19:00:00"
        },
    ]

    import headTop from '../components/headTop'
    import {postMethod} from "@/api/getDataLocal";
    export default {
        data(){
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
                    auditRemark: [
                        {required: true, message: '请输入审核备注', trigger: 'blur'}
                    ]
                },
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
            handleAudit(result) {
                this.$refs.auditInfoForm.validate(async valid => {
                    if (valid) {
                        const res = await postMethod("/core/materialAudit/audit", JSON.stringify(
                            {
                                auditRemark: this.selectTable.auditRemark,
                                auditResult: result,
                                materialAuditId: this.selectTable.materialAuditId
                            }
                        ));

                        if (res.data.code === 200) {
                            this.$message({
                                type: 'success',
                                message: '审核成功'
                            });

                            this.currentPage = 1;
                            await this.getMaterialAuditList();
                            this.auditInfoDialogVisible = false;
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.msg
                            });
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: "请填写审核意见"
                        });
                    }
                });
            }
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
