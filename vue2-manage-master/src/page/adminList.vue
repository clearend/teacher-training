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
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button type="primary" size="small" @click="handleDetail(scope.$index)">查看详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left; margin-top: 10px;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="limit"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>

            <el-dialog title="培训完成信息" v-model="detailDialogVisible">
                <el-form :model="selectTable">
                    <el-form-item label="培训主题" label-width="100px">
                        <el-input v-model="selectTable.learnName" auto-complete="off" disabled></el-input>
                    </el-form-item>
                </el-form>

                <h3 style="margin-bottom: 10px;">完成教师列表</h3>
                <el-row style="overflow: auto; text-align: left;">
                    <el-table
                        :data="selectTable.completedList"
                        style="margin-bottom: 20px;">
                        <el-table-column prop="name" label="教师姓名"></el-table-column>
                        <el-table-column prop="jobId" label="工号"></el-table-column>
                        <el-table-column prop="date" label="报名日期"></el-table-column>
                        <el-table-column prop="finishDate" label="完成日期"></el-table-column>
					</el-table>
                </el-row>

                <h3 style="margin-bottom: 10px;">未完成教师列表</h3>
                <el-row style="overflow: auto; text-align: left;">
                    <el-table
                        :data="selectTable.unfinishedList"
                        style="margin-bottom: 20px;">
                        <el-table-column prop="name" label="教师姓名"></el-table-column>
                        <el-table-column prop="jobId" label="工号"></el-table-column>
                        <el-table-column prop="date" label="报名日期"></el-table-column>
                        <el-table-column prop="finishDate" label="完成日期"></el-table-column>
                        <el-table-column label="操作" >
                        <template slot-scope="scope">
                            <el-button size="small" type="danger" @click="handleNotify(scope.$index)">提醒</el-button>
                        </template>
                        </el-table-column>
					</el-table>
                </el-row>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    const demoCompleteData = [
        {
            "learnName": "文学艺术创作技巧分享会",
            "completedList": [
                {
                    name: "刘小勇",
                    jobId: "2019201",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019202",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019203",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "周小倩",
                    jobId: "2019204",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019205",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": []
        },
        {
            "learnName": "金融投资新思路讲座",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019211",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019212",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019213",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019214",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019215",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019216",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019217",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019218",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019219",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019220",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "教育技术应用研讨会",
            "completedList": [
                {
                    name: "吴小雨",
                    jobId: "2019221",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "周小倩",
                    jobId: "2019222",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019223",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019224",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019225",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "刘小峰",
                    jobId: "2019226",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "李小红",
                    jobId: "2019227",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019228",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019229",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019230",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "企业发展战略规划研讨会",
            "completedList": [
                {
                    name: "孙小洋",
                    jobId: "2019231",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019232",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019233",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "刘小峰",
                    jobId: "2019234",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019235",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019236",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019237",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019238",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "医学科研技术交流会",
            "completedList": [
                {
                    name: "赵小涵",
                    jobId: "2019239",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019240",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019241",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "李小红",
                    jobId: "2019242",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019243",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "刘小峰",
                    jobId: "2019244",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019245",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019246",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019247",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019248",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "创新教育理念研讨会",
            "completedList": [
                {
                    name: "张小娟",
                    jobId: "2019249",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小勇",
                    jobId: "2019250",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019251",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小勇",
                    jobId: "2019252",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019253",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "周小倩",
                    jobId: "2019254",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019255",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "刘小峰",
                    jobId: "2019256",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "李小红",
                    jobId: "2019257",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019258",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019259",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019260",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "互联网创业成功经验分享会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019261",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019262",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019263",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019264",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019265",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019266",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019267",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019268",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019269",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019270",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "环境保护与可持续发展论坛",
            "completedList": [
                {
                    name: "吴小雨",
                    jobId: "2019271",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "周小倩",
                    jobId: "2019272",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019273",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019274",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019275",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "刘小峰",
                    jobId: "2019276",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "李小红",
                    jobId: "2019277",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019278",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019279",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019280",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "人工智能应用前景研讨会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019281",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019282",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019283",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019284",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019285",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019286",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019287",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019288",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019289",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019290",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "教育技术应用研讨会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019281",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019282",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019283",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019284",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019285",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019286",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019287",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019288",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019289",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019290",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "企业发展战略规划研讨会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019281",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019282",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019283",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019284",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019285",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019286",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019287",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019288",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019289",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019290",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "医学科研技术交流会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019281",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019282",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019283",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019284",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019285",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019286",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019287",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019288",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019289",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019290",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
        },
        {
            "learnName": "创新教育理念研讨会",
            "completedList": [
                {
                    name: "周小倩",
                    jobId: "2019281",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "朱小蕾",
                    jobId: "2019282",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吕小明",
                    jobId: "2019283",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "张小娟",
                    jobId: "2019284",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "刘小峰",
                    jobId: "2019285",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ],
            "unfinishedList": [
                {
                    name: "李小红",
                    jobId: "2019286",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "王小明",
                    jobId: "2019287",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "赵小涵",
                    jobId: "2019288",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "孙小洋",
                    jobId: "2019289",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                },
                {
                    name: "吴小雨",
                    jobId: "2019290",
                    date: "2020-01-01",
                    finishDate: "2020-01-02",
                }
            ]
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
                detailDialogVisible: false,
                selectTable: {},
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
                    } else {
                        throw new Error(res.message)
                    }
                }catch(err){
                    console.log('获取数据失败', err);
                }
            },
            handleDetail(index) {
                console.log(index);
                this.selectTable = demoCompleteData[index];
                this.detailDialogVisible = true;
            },
            handleNotify(index) {
                this.$message({
                    type: 'success',
                    message: '提醒成功',
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
</style>


