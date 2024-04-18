<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
		        <el-table-column prop="learnName" label="培训主题"></el-table-column>
		        <el-table-column prop="completed" label="完成人数"></el-table-column>
                <el-table-column prop="unfinished" label="未完成人数"></el-table-column>
		        <el-table-column prop="completedPer" label="完成率"></el-table-column>
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
    const demoCompleteData = [
        {
            "learnName": "文学艺术创作技巧分享会",
            "completedList": [
                "刘小勇",
                "吴小雨",
                "吕小明"
            ],
            "unfinishedList": [
                "刘小峰",
                "李小红",
                "王小明",
                "李小红",
                "李小红",
                "赵小涵",
                "孙小洋"
            ]
        },
        {
            "learnName": "金融投资新思路讲座",
            "completedList": [
                "赵小涵",
                "孙小洋",
                "张小娟",
                "李小红",
                "王小明"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "张小娟",
                "刘小峰",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "教育技术应用研讨会",
            "completedList": [
                "吴小雨",
                "朱小蕾",
                "吕小明",
                "张小娟"
            ],
            "unfinishedList": [
                "刘小峰",
                "李小红",
                "孙小洋"
            ]
        },
        {
            "learnName": "企业发展战略规划研讨会",
            "completedList": [
                "孙小洋",
                "张小娟",
                "李小红",
                "王小明",
                "赵小涵"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "医学科研技术交流会",
            "completedList": [
                "赵小涵",
                "孙小洋",
                "张小娟",
                "李小红",
                "王小明"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "创新教育理念研讨会",
            "completedList": [
                "张小娟",
                "刘小勇",
                "吴小雨",
                "周小倩",
                "朱小蕾"
            ],
            "unfinishedList": [
                "刘小峰",
                "李小红",
                "王小明",
                "赵小涵",
                "孙小洋"
            ]
        },
        {
            "learnName": "互联网创业成功经验分享会",
            "completedList": [
                "周小倩",
                "朱小蕾",
                "吕小明",
                "张小娟",
                "刘小峰"
            ],
            "unfinishedList": [
                "李小红",
                "王小明",
                "赵小涵",
                "李小红",
                "王小明",
                "赵小涵",
                "李小红",
                "王小明",
                "赵小涵",
                "孙小洋",
                "吴小雨"
            ]
        },
        {
            "learnName": "环境保护与可持续发展论坛",
            "completedList": [
                "赵小涵",
                "孙小洋",
                "张小娟",
                "李小红",
                "张小娟",
                "李小红",
                "张小娟",
                "李小红",
                "王小明"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "人工智能应用前景研讨会",
            "completedList": [
                "孙小洋",
                "张小娟",
                "李小红",
                "王小明",
                "赵小涵"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "张小娟",
                "刘小峰",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "教育技术应用研讨会",
            "completedList": [
                "吴小雨",
                "周小倩",
                "朱小蕾",
                "吕小明",
                "张小娟"
            ],
            "unfinishedList": [
                "刘小峰",
                "李小红",
                "王小明",
                "赵小涵",
                "孙小洋"
            ]
        },
        {
            "learnName": "企业发展战略规划研讨会",
            "completedList": [
                "孙小洋",
                "王小明",
                "赵小涵"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "医学科研技术交流会",
            "completedList": [
                "赵小涵",
                "孙小洋",
                "张小娟",
                "李小红",
                "王小明"
            ],
            "unfinishedList": [
                "刘小峰",
                "吕小明",
                "张小娟",
                "刘小峰",
                "吕小明"
            ]
        },
        {
            "learnName": "创新教育理念研讨会",
            "completedList": [
                "张小娟",
                "刘小勇",
                "张小娟",
                "刘小勇",
                "吴小雨",
                "周小倩",
                "朱小蕾"
            ],
        }
    ]
    import headTop from '../components/headTop'
    import {adminList, adminCount} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                offset: 0,
                limit: 15,
                count: 0,
                currentPage: 1,
            }
        },
    	components: {
    		headTop,
    	},
        created(){
            this.initData();
        },
        methods: {
            initData(){
                try {
                    this.count = demoCompleteData.length
                    this.getCompleteResult();
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
                this.getAdmin()
            },
            async getCompleteResult(){
                try {
                    if (1) {
                    	this.tableData = [];
                    	demoCompleteData.forEach(item => {
                    		const tableItem = {
                    			learnName: item.learnName,
						        completed: item.completedList.length,
						        unfinished: item.unfinishedList.length,
                                completedPer: Number((item.completedList.length / (item.completedList.length + item.unfinishedList.length)) * 100).toFixed(2) + '%',
                    		}
                    		this.tableData.push(tableItem)
                    	})
                    }else{
                    	throw new Error(res.message)
                    }
                }catch(err){
                    console.log('获取数据失败', err);
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


