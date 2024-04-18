<template>
    <div class="fillcontain">
        <head-top></head-top>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
			    :data="tableData"
			    highlight-current-row
			    style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
			    <el-table-column label="提示人" prop="username"></el-table-column>
			    <el-table-column label="提示内容" prop="content"></el-table-column>
			    <el-table-column label="提示时间" prop="noticeDate"></el-table-column>
<!--                <el-table-column label="操作" width="200">-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">撤销</el-button>-->
<!--                    </template>-->
<!--                </el-table-column>-->
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
    </div>
</template>

<script>
    const demoNotifyData = [
        {
            "username": "管理员",
            "content": "请记及时修改密码以提高账号安全性",
            "noticeDate": "2024-04-05 18:30:00"
        },
        {
            "username": "李老师",
            "content": "今天有重要的在线讨论活动，请准时参加",
            "noticeDate": "2024-04-10 20:15:00"
        },
        {
            "username": "张老师",
            "content": "请务必在截止日期前完成作业提交",
            "noticeDate": "2024-04-15 17:45:00"
        },
    ]

    import headTop from '../components/headTop'
    import {getOrderList, getOrderCount, getResturantDetail, getUserInfo, getAddressById} from '@/api/getData'
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
                newTable: {},
                ansUserList: [
                    {
                        "value": 1,
                        "label": "王小明"
                    },
                    {
                        "value": 2,
                        "label": "李娜"
                    },
                    {
                        "value": 3,
                        "label": "张伟"
                    },
                    {
                        "value": 4,
                        "label": "刘洋"
                    },
                    {
                        "value": 5,
                        "label": "陈艳"
                    },
                    {
                        "value": 6,
                        "label": "赵宇航"
                    },
                    {
                        "value": 7,
                        "label": "孙梦琪"
                    },
                    {
                        "value": 8,
                        "label": "周阳光"
                    },
                    {
                        "value": 9,
                        "label": "吴丽丽"
                    },
                    {
                        "value": 10,
                        "label": "郑小凯"
                    },
                    {
                        "value": 11,
                        "label": "马晓洁"
                    },
                    {
                        "value": 12,
                        "label": "黄晓雯"
                    },
                    {
                        "value": 13,
                        "label": "朱小红"
                    },
                    {
                        "value": 14,
                        "label": "刘小勇"
                    },
                    {
                        "value": 15,
                        "label": "吕小芳"
                    },
                    {
                        "value": 16,
                        "label": "张小敏"
                    },
                    {
                        "value": 17,
                        "label": "李小刚"
                    },
                    {
                        "value": 18,
                        "label": "王小婷"
                    },
                    {
                        "value": 19,
                        "label": "赵小涵"
                    },
                    {
                        "value": 20,
                        "label": "孙小洋"
                    },
                    {
                        "value": 21,
                        "label": "周小倩"
                    },
                    {
                        "value": 22,
                        "label": "吴小雨"
                    },
                    {
                        "value": 23,
                        "label": "郑小楠"
                    },
                    {
                        "value": 24,
                        "label": "马小明"
                    },
                    {
                        "value": 25,
                        "label": "黄小静"
                    },
                    {
                        "value": 26,
                        "label": "朱小蕾"
                    },
                    {
                        "value": 27,
                        "label": "刘小峰"
                    },
                    {
                        "value": 28,
                        "label": "吕小明"
                    },
                    {
                        "value": 29,
                        "label": "张小娟"
                    },
                    {
                        "value": 30,
                        "label": "李小红"
                    }
                ]
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
            async initData(){
                try{
                    this.getNotice();
                    this.count = demoNotifyData.length;
                }catch(err){
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getNotice()
            },
            async getNotice(){
                this.tableData = demoNotifyData.slice(this.offset, this.offset + this.limit);
            },
            handleDelete(index, row) {
                try{
                    // const res = await deleteFood(row.item_id);
                    demoNotifyData.splice(this.offset + index, 1);
                    if (1) {
                        this.$message({
                            type: 'success',
                            message: '删除提醒成功'
                        });
                        // this.tableData.splice(index, 1);
                        this.tableData = demoNotifyData.slice(this.offset, this.offset + this.limit);
                        this.count = demoNotifyData.length;
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除提醒失败')
                }
            },
            handleAdd() {
                this.newTable = {
                    "username": "",
                    "content": "",
                    "noticeDate": "",
                    "ansUserList": this.ansUserList
                };
                this.addDialogFormVisible = true;
            },
            addUser() {
                if (this.newTable.username === "") {
                    this.$message({
                        type: 'error',
                        message: '被提醒人不能为空'
                    });
                    return;
                }
                if (this.newTable.content === "") {
                    this.$message({
                        type: 'error',
                        message: '提醒内容不能为空'
                    });
                    return;
                }
                if (this.newTable.noticeDate === "") {
                    this.$message({
                        type: 'error',
                        message: '提醒日期不能为空'
                    });
                    return;
                }

                this.addDialogFormVisible = false;
                demoNotifyData.splice(0, 0, this.newTable);
                this.count = demoNotifyData.length;
                this.currentPage = 1;
                this.tableData = demoNotifyData.slice(0, this.limit);

                this.$message({
                    type: 'success',
                    message: '添加成功'
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
