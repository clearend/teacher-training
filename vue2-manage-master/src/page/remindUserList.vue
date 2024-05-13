<template>
    <div class="fillcontain">
        <head-top></head-top>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
                    :data="tableData"
                    highlight-current-row
                    style="width: 100%">
                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column property="remindContent" label="提醒内容"></el-table-column>
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
import headTop from "../components/headTop.vue";
import {postMethod} from "@/api/getDataLocal";

export default {
    data() {
        return {
            tableData: [],
            currentPage: 1,
            offset: 0,
            limit: 15,
            count: 0,
        }
    },
    components: {
        headTop,
    },
    async created() {
        await this.getRemindList();
    },
    methods: {
        async getRemindList() {
            let query = {
                pageRequest: {
                    currentPage: this.currentPage,
                    pageSize: this.limit,
                },
            }
            let res = await postMethod('/core/remindMessage/list', JSON.stringify(query));

            if (res.data.code === 200) {
                this.tableData = res.data.data.remindList;
                this.count = res.data.data.count;
            } else {
                this.$message({
                    type: 'error',
                    message: res.data.msg
                });
            }
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1) * this.limit;
            await this.getRemindList()
        }
    }
}
</script>

<style scoped lang="less">
@import '../style/mixin';
.table_container{
  padding: 20px;
}
.filter-input{
  width: 200px;
}
</style>
