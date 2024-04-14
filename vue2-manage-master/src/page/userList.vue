<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column
                    type="index"
                    width="100">
                </el-table-column>
                <el-table-column
                    property="registe_time"
                    label="注册日期"
                    width="220">
                </el-table-column>
                <el-table-column
                    property="username"
                    label="用户姓名"
                    width="220">
                </el-table-column>
                <el-table-column
                    property="city"
                    label="注册地址">
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="20"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import headTop from '../components/headTop'
import {getUserList, getUserCount} from '@/api/getData'
export default {
    data(){
        return {
            tableData: [
                {
                    registe_time: '2016-05-02',
                    username: '张丽丽',
                    city: '北京市朝阳区建国路 1 号'
                },
                {
                    registe_time: '2016-05-04',
                    username: '王伟伟',
                    city: '上海市黄浦区南京东路 123 号'
                },
                {
                    registe_time: '2016-05-01',
                    username: '李娜娜',
                    city: '广州市天河区天河路 789 号'
                },
                {
                    registe_time: '2016-05-03',
                    username: '刘涛涛',
                    city: '深圳市福田区深南大道 1010 号'
                },
                {
                    registe_time: '2016-05-05',
                    username: '陈敏敏',
                    city: '杭州市西湖区文二西路 202 号'
                },
                {
                    registe_time: '2016-05-07',
                    username: '吴美丽',
                    city: '南京市玄武区中山路 303 号'
                },
                {
                    registe_time: '2016-05-06',
                    username: '赵磊磊',
                    city: '武汉市江岸区中山大道 404 号'
                },
                {
                    registe_time: '2016-05-08',
                    username: '孙芳芳',
                    city: '成都市青羊区光华大道 505 号'
                },
                {
                    registe_time: '2016-05-09',
                    username: '钱强强',
                    city: '重庆市渝中区人民路 606 号'
                },
                {
                    registe_time: '2016-05-10',
                    username: '朱雪峰',
                    city: '西安市雁塔区南二环 707 号'
                },
                {
                    registe_time: '2016-05-11',
                    username: '孔雅丽',
                    city: '天津市和平区解放北路 808 号'
                },
                {
                    registe_time: '2016-05-12',
                    username: '曹波波',
                    city: '苏州市姑苏区双塔街 909 号'
                },
                {
                    registe_time: '2016-05-13',
                    username: '董莉莉',
                    city: '南昌市东湖区解放西路 1010 号'
                },
                {
                    registe_time: '2016-05-14',
                    username: '黄丹丹',
                    city: '福州市鼓楼区五一路 1111 号'
                },
                {
                    registe_time: '2016-05-15',
                    username: '肖杰杰',
                    city: '济南市市中区英雄山路 1212 号'
                },
                {
                    registe_time: '2016-05-16',
                    username: '梁婷婷',
                    city: '沈阳市和平区长白大街 1313 号'
                },
                {
                    registe_time: '2016-05-17',
                    username: '许军军',
                    city: '长沙市岳麓区梅溪湖路 1414 号'
                },
                {
                    registe_time: '2016-05-18',
                    username: '万丽丽',
                    city: '郑州市金水区农业路 1515 号'
                },
                {
                    registe_time: '2016-05-19',
                    username: '雷兵兵',
                    city: '南宁市兴宁区中山路 1616 号'
                },
                {
                    registe_time: '2016-05-20',
                    username: '韩霞霞',
                    city: '合肥市庐阳区和平路 1717 号'
                }
                // 可以继续添加更多的数据
            ],
            currentRow: null,
            offset: 0,
            limit: 20,
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
        async initData(){
            try{
                const countData = await getUserCount();
                if (countData.status == 1) {
                    this.count = countData.count;
                }else{
                    throw new Error('获取数据失败');
                }
                this.getUsers();
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
            this.getUsers()
        },
        async getUsers(){
            const Users = await getUserList({offset: this.offset, limit: this.limit});
            // this.tableData = [];
            Users.forEach(item => {
                const tableData = {};
                tableData.username = item.username;
                tableData.registe_time = item.registe_time;
                tableData.city = item.city;
                // this.tableData.push(tableData);
            })
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
