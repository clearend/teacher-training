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

        <el-dialog title="上传素材信息" v-model="addDialogFormVisible">
            <el-form :model="newTable">
                <el-form-item label="素材名称" label-width="100px">
                    <el-input v-model="newTable.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="素材类型" label-width="100px">
                    <el-input v-model="newTable.type"></el-input>
                </el-form-item>
                <el-form-item label="上传日期" label-width="100px">
                    <el-input v-model="newTable.uploadDate"></el-input>
                </el-form-item>

                <el-form-item label="素材文件" label-width="100px">
                    <el-upload
                        class="avatar-uploader"
                        :action="baseUrl + '/v1/addimg/food'"
                        :show-file-list="true"
                        :on-success="handleServiceAvatarScucess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="selectTable.file_path" :src="baseImgPath + selectTable.file_path" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addFile">确 定</el-button>
            </div>
        </el-dialog>

        <div class="table_container" style="padding-top: 10px;">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">

                <el-table-column type="index" width="100"></el-table-column>
                <el-table-column label="素材名称" prop="name"></el-table-column>
                <el-table-column label="素材类型" prop="type"></el-table-column>
                <el-table-column label="上传日期" prop="uploadDate"></el-table-column>
                <el-table-column label="操作" width="300">
                  <template slot-scope="scope">
                    <el-button size="small" @click="handleDownload(scope.row)">下载</el-button>
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

            <el-dialog title="修改素材信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="素材名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="素材类型" label-width="100px">
                        <el-input v-model="selectTable.type"></el-input>
                    </el-form-item>
                    <el-form-item label="上传日期" label-width="100px">
                        <el-input v-model="selectTable.uploadDate"></el-input>
                    </el-form-item>

<!--                    <el-form-item label="食品图片" label-width="100px">-->
<!--                        <el-upload-->
<!--                          class="avatar-uploader"-->
<!--                          :action="baseUrl + '/v1/addimg/food'"-->
<!--                          :show-file-list="false"-->
<!--                          :on-success="handleServiceAvatarScucess"-->
<!--                          :before-upload="beforeAvatarUpload">-->
<!--                          <img v-if="selectTable.image_path" :src="baseImgPath + selectTable.image_path" class="avatar">-->
<!--                          <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--                        </el-upload>-->
<!--                    </el-form-item>-->
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateFile">确 定</el-button>
              </div>
            </el-dialog>

        </div>
    </div>
</template>

<script>
    const demoFileData = [
        {
            "name": "学术论文",
            "type": "PDF",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "培训视频",
            "type": "视频",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "年度报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "工作总结",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "会议记录",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "市场调研报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "项目计划书",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "数据分析报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "招聘启事",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "产品说明书",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "学术论文",
            "type": "PDF",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "培训视频",
            "type": "视频",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "年度报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "工作总结",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "会议记录",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "市场调研报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "项目计划书",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "数据分析报告",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "招聘启事",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        },
        {
            "name": "产品说明书",
            "type": "文档",
            "uploadDate": "2024-04-01 18:00:00",
            "file_path": "18ef2121c8b117294.jpg"
        }
    ]

    import headTop from '../components/headTop'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {getFoods, getFoodsCount, getMenu, updateFood, deleteFood, getResturantDetail, getMenuById} from '@/api/getData'
    export default {
        data(){
            return {
                baseUrl,
                baseImgPath,
                restaurant_id: null,
                city: {},
                offset: 0,
                limit: 15,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                menuOptions: [],
                selectMenu: {},
                selectIndex: null,
                specsForm: {
		          	specs: '',
		          	packing_fee: 0,
		          	price: 20,
		        },
                specsFormrules: {
		        	specs: [
						{ required: true, message: '请输入规格', trigger: 'blur' },
					],
		        },
		        specsFormVisible: false,
                expendRow: [],
                addDialogFormVisible: false,
                newTable: {},
            }
        },
        created() {
        	// this.restaurant_id = this.$route.query.restaurant_id;
            this.initData();
        },
        computed: {
        	specs: function (){
        		let specs = [];
        		if (this.selectTable.specfoods) {
	        		this.selectTable.specfoods.forEach(item => {
	        			specs.push({
	        				specs: item.specs_name,
	        				packing_fee: item.packing_fee,
	        				price: item.price,
	        			})
	        		})
        		}
        		return specs
        	}
        },
    	components: {
    		headTop,
    	},
        methods: {
            initData(){
                try{
                    // const countData = await getFoodsCount({restaurant_id: this.restaurant_id});
                    // if (countData.status == 1) {
                    //     this.count = countData.count;
                    // }else{
                    //     throw new Error('获取数据失败');
                    // }
                    this.count = demoFileData.length;
                    this.getFiles();
                }catch(err){
                    console.log('获取数据失败', err);
                }
            },
            async getFiles(){
                this.tableData = demoFileData.slice(this.offset, this.offset + this.limit);
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getFoods()
            },
            handleEdit(row) {
                this.selectTable = row;
                this.dialogFormVisible = true;
            },
            handleSelect(index){
            	this.selectIndex = index;
            	this.selectMenu = this.menuOptions[index];
            },
            handleDelete(index, row) {
                try{
                    demoFileData.splice(this.offset + index, 1);
                    if (1) {
                        this.$message({
                            type: 'success',
                            message: '删除素材成功'
                        });
                        this.tableData = demoFileData.slice(this.offset, this.offset + this.limit);
                        this.count = demoFileData.length;
                    }else{
                        throw new Error(res.message)
                    }
                }catch(err){
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除素材失败')
                }
            },
            updateFile() {
                this.dialogFormVisible = false;
                try {
                    // const subData = {new_category_id: this.selectMenu.value, specs: this.specs};
                    // const postData = {...this.selectTable, ...subData};
                    // const res = await updateFood(postData)
                    if (1) {
                        this.$message({
                            type: 'success',
                            message: '更新素材信息成功'
                        });
                        // this.getFoods();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                }catch(err){
                    console.log('更新素材信息失败', err);
                }
            },
            handleAdd() {
                this.newTable = {
                    "name": "",
                    "type": "",
                    "uploadDate": "",
                    "file_path": "",
                };
                this.addDialogFormVisible = true;
            },
            addFile() {
                if (this.newTable.name === "") {
                    this.$message({
                        type: 'error',
                        message: '素材名称不能为空'
                    });
                    return;
                }
                if (this.newTable.type === "") {
                    this.$message({
                        type: 'error',
                        message: '素材类型不能为空'
                    });
                    return;
                }
                if (this.newTable.uploadDate === "") {
                    this.$message({
                        type: 'error',
                        message: '上传日期不能为空'
                    });
                    return;
                }
                if (this.newTable.file_path === "") {
                    this.$message({
                        type: 'error',
                        message: '请上传素材',
                    });
                    return;
                }

                this.addDialogFormVisible = false;
                // demoFileData.splice(0, 0, this.newTable);
                // this.count = demoFileData.length;
                // this.currentPage = 1;
                // this.tableData = demoFileData.slice(0, this.limit);

                this.$message({
                    type: 'success',
                    message: '添加成功，等待管理员审核'
                });
            },
            handleServiceAvatarScucess(res, file) {
                console.log(res)
                if (res.status === 1) {
                    this.newTable.file_path = res.image_path;
                }else{
                    this.$message.error('上传图片失败！');
                }
            },
            beforeAvatarUpload(file) {
                // const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                // const isLt2M = file.size / 1024 / 1024 < 2;

                // if (!isRightType) {
                //     this.$message.error('上传头像图片只能是 JPG 格式!');
                // }
                // if (!isLt2M) {
                //     this.$message.error('上传头像图片大小不能超过 2MB!');
                // }
                return true;
            },
            handleDownload(row) {
                window.location.href = baseImgPath + row.file_path
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
