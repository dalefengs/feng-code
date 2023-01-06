<template>
  <a-card class="body">
    <a-row>
      <!--   左   -->
      <a-col :span="18">
        <a-row>
          <a-col id="left">
            <h3 class="task-h3">学习计划</h3>
            <div style="margin-left: 30px">
              <div v-for="(item, index) in taskList" :key="index" class="task" :title="item.title" @click="goTaskInfo(item.id)">
                <img :src="item.imgUrl" alt="" class="task-img">
                <div class="task-title">{{ item.title }}</div>
              </div>
            </div>
            <br>
            <a-radio-group default-value="a" button-style="solid" v-model="queryParam.categoryId" style="margin-left: 15px" >
              <a-radio-button :value="0" class="category-button" @click="getList">
                <div class="category-all-icon">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="1em" height="1em" fill="currentColor">
                    <path fill-rule="evenodd" d="M20 10v9a2 2 0 01-2 2H6a2 2 0 01-2-2v-9a2 2 0 01-2-2V6a2 2 0 012-2h16a2 2 0 012 2v2a2 2 0 01-2 2zM6 10v9h12v-9H6zM4 6v2h16V6H4zm6 7.5a1 1 0 110-2h4a1 1 0 110 2h-4z" clip-rule="evenodd"></path>
                  </svg>
                </div>
                <span>全部题目</span>
              </a-radio-button>
              <a-radio-button v-for="(category, index) in categoryList" :value="category.id" :key="index" class="category-button">
                {{ category.title }}
              </a-radio-button>
            </a-radio-group>
          </a-col>
        </a-row>
        <a-card :bordered="false">
          <!-- 条件搜索 -->
          <div class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :md="8" :sm="24">
                  <a-form-item label="题目" prop="title">
                    <a-input v-model="queryParam.title" placeholder="搜索题目" allow-clear/>
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <a-form-item label="状态" prop="level">
                    <a-select placeholder="状态" v-model="queryParam.ownness" style="width: 100%" allow-clear>
                      <a-select-option v-for="(d, index) in dict.type.difficulty_level" :key="index" :value="d.value">{{ d.label }}</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <a-form-item label="等级" prop="level">
                    <a-select placeholder="等级" v-model="queryParam.level" style="width: 100%" allow-clear>
                      <a-select-option v-for="(d, index) in dict.type.difficulty_level" :key="index" :value="d.value">{{ d.label }}</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <!-- 数据展示 -->
          <a-table
            :loading="loading"
            :size="tableSize"
            rowKey="id"
            :columns="columns"
            :data-source="list"
            :pagination="false"
            :bordered="tableBordered"
            :customRow="problemRowClick"
            @change="tableChange"
          >
            <span slot="title" slot-scope="text, record">
              {{ record ? `${record.id}.${record.title}` : '' }}
            </span>
            <span slot="level" slot-scope="text, record">
              <dict-tag :options="dict.type['difficulty_level']" :value="record.level" />
            </span>
            <span slot="successRate" slot-scope="text, record">
              {{ record.submitCount > 0 ? ((record.successCount / record.submitCount) * 100).toFixed(2) : '0.00' }} %
            </span>
            <div slot="ownness" slot-scope="text, record">
              <div v-if="text === 1" class="ownness" > <!-- 已解答 -->
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  width="1em"
                  height="1em"
                  fill="currentColor"
                  style="color: rgb(45 181 93)"
                >
                  <path
                    fill-rule="evenodd"
                    d="M20 12.005v-.828a1 1 0 112 0v.829a10 10 0 11-5.93-9.14 1 1 0 01-.814 1.826A8 8 0 1020 12.005zM8.593 10.852a1 1 0 011.414 0L12 12.844l8.293-8.3a1 1 0 011.415 1.413l-9 9.009a1 1 0 01-1.415 0l-2.7-2.7a1 1 0 010-1.414z"
                    clip-rule="evenodd"></path>
                </svg>
                <dict-tag :showBadge="false" :options="dict.type['ownness']" :value="record.ownness" />
              </div>
              <div v-if="text === 2" class="ownness"> <!-- 尝试过 -->
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  width="1em"
                  height="1em"
                  fill="currentColor"
                  style="color: orange">
                  <path fill-rule="evenodd" d="M8.972 3a1 1 0 01.964.649l4.978 13.274 1.632-5.221A1 1 0 0117.5 11H21a1 1 0 110 2h-2.765l-2.28 7.298a1 1 0 01-1.891.053L9.086 7.077l-1.632 5.221A1 1 0 016.5 13H3a1 1 0 110-2h2.765l2.28-7.298A1 1 0 018.973 3z" clip-rule="evenodd"></path>
                </svg>
                <dict-tag :showBadge="false" :options="dict.type['ownness']" :value="record.ownness" />
              </div>
              <div v-if="text === 3" class="ownness"> <!-- 待批阅 -->
                <a-icon class="ownness-icon" type="file-search" style="color: #1890ff" />
                <dict-tag :showBadge="false" :options="dict.type['ownness']" :value="record.ownness" />
              </div>
            </div>
          </a-table>
          <!-- 分页 -->
          <a-pagination
            class="ant-table-pagination"
            show-size-changer
            show-quick-jumper
            :current="queryParam.pageNum"
            :total="total"
            :page-size="queryParam.pageSize"
            :showTotal="total => `共 ${total} 条`"
            @showSizeChange="onShowSizeChange"
            @change="changeSize"
          />
        </a-card>
      </a-col>
      <!--   右   -->
      <a-col :span="6">
        <a-card class="right-shadow" style="margin-top: 64px">
          <!--    每日一题      -->
          <a-calendar :fullscreen="false" :header-render="headerRender" @panelChange="onPanelChange" @select="onCalendarSelect" >
            <div slot="dateFullCellRender" slot-scope="value" style="position: absolute; cursor: pointer">
              <a-tooltip>
                <template slot="title">
                  {{ value.format('YYYY-MM-DD') }}
                  {{ everydayListObj[value.format('YYYY-MM-DD')] ? everydayListObj[value.format('YYYY-MM-DD')].title : '暂无' }}
                  <span v-if="everydayListObj[value.format('YYYY-MM-DD')]">
                    <span v-if="everydayListObj[value.format('YYYY-MM-DD')].ownness === 1"> - 已解答</span>
                    <span v-else-if="everydayListObj[value.format('YYYY-MM-DD')].ownness === 2"> - 尝试过</span>
                    <span v-else> - 未开始</span>
                  </span>
                </template>
                <td role="gridcell" class="ant-fullcalendar-cell">
                  <div class="ant-fullcalendar-date">
                    <div class="ant-fullcalendar-value">{{ value.date() }}</div>
                    <div class="ant-fullcalendar-content"></div>
                  </div>
                </td>
                <div style="width:100%; position: absolute; left: 35%; top: 18px">
                  <div v-if="everydayListObj[value.format('YYYY-MM-DD')]" >
                    <a-badge status="success" v-if="everydayListObj[value.format('YYYY-MM-DD')].ownness === 1" /> <!-- 已解答 -->
                    <a-badge status="warning" v-else-if="everydayListObj[value.format('YYYY-MM-DD')].ownness === 2" /> <!-- 尝试过 -->
                    <a-badge status="processing" v-else /> <!-- 未执行 -->
                  </div>
                </div>
              </a-tooltip>

            </div>
          </a-calendar>
        </a-card>
        <a-card class="right-shadow" style="height: 95px; cursor: pointer" @click="$notification.warn({ message: '学习广场暂未开放，敬请期待！'})" >
          <div style="display: inline-block; vertical-align: text-bottom;">
            <img
              src="https://static.leetcode-cn.com/cn-frontendx-assets/production/_next/static/images/icon-study-plan-d9ac428f64fe3787cd9224c67132b8b3.svg"
              alt="Study plan"
              class="h-10 w-10">
          </div>
          <div style="display: inline-block; margin-left: 10px" >
            <div style="display: inline-block; font-size: 18px">学习计划广场</div>
            <br>
            <div style="display: inline-block; color: #7f8c8d">学习计划广场</div>
          </div>
          <a-icon type="right" style="float: right; margin-top: 18px" />
        </a-card>
      </a-col>
    </a-row>
  </a-card>

</template>

<script>
import { listAllProblemCategory } from '@/api/business/problemCategory'
import { listAllTags } from '@/api/business/tags'
import { listProblemSet } from '@/api/business/problem'
import { listTask } from '@/api/business/task'
import { monthListEveryday } from '@/api/business/everyday'
import { tableMixin } from '@/store/table-mixin'

import moment from 'moment'

export default {
  name: 'ProblemSet',
  mixins: [tableMixin],
  dicts: ['difficulty_level', 'is_auto', 'ownness'],
  data () {
    return {
      list: [],
      taskList: [],
      everydayListObj: {},
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      loading: false,
      total: 0,
      // 查询参数
      queryParam: {
        categoryId: 0,
        title: '',
        level: undefined,
        ownness: undefined,
        pageNum: 1,
        pageSize: 20
      },
      columns: [
        {
          title: '状态',
          dataIndex: 'ownness',
          scopedSlots: { customRender: 'ownness' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '题目',
          dataIndex: 'title',
          scopedSlots: { customRender: 'title' },
          ellipsis: true,
          align: 'left'
        },
        {
          title: '作者',
          dataIndex: 'nickName',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '难度等级',
          dataIndex: 'level',
          scopedSlots: { customRender: 'level' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '通过率',
          dataIndex: 'successRate',
          scopedSlots: { customRender: 'successRate' },
          ellipsis: true,
          align: 'center'
        }
        // {
        //   title: '提交次数',
        //   dataIndex: 'submitCount',
        //   ellipsis: true,
        //   align: 'center'
        // },
        // {
        //   title: '通过次数',
        //   dataIndex: 'successCount',
        //   ellipsis: true,
        //   align: 'center'
        // }
      ],
      categoryList: [],
      tsgsList: [],
      // 绑定每行单击事件
      problemRowClick: record => ({
        on: {
          click: () => {
            console.log('click:', record.id)
            this.$router.push({ path: '/problemSet/' + record.id })
          }
        }
      })
    }
  },
  created () {
    listAllProblemCategory().then(res => {
      this.categoryList = res.data
    })
    listAllTags().then(res => {
      this.tsgsList = res.data
    })
    const taskParam = {
      teacherId: 0,
      pageNum: 1,
      pageSize: 7
    }
    listTask(taskParam).then(res => {
      this.taskList = res.rows
    })
    this.getList()
    this.getEverydayList(moment())
  },
  watch: {
    'queryParam.categoryId': {
      handler (newVal, oldVal) {
        this.getList()
      }
    },
    'queryParam.title': {
      handler (newVal, oldVal) {
        this.getList()
      }
    },
    'queryParam.level': {
      handler (newVal, oldVal) {
        this.getList()
      }
    },
    'queryParam.ownness': {
      handler (newVal, oldVal) {
        this.getList()
      }
    }
  },
  methods: {
    getEverydayList (momentObj) {
      const dateTime = momentObj.format('YYYY-MM-DD HH:mm:ss')
      const query = {
        dateTime: dateTime
      }
      const obj = {}
      monthListEveryday(query).then(res => {
        res.data.forEach(item => {
          obj[item.dateDay] = item
        })
        this.everydayListObj = Object.assign({}, this.everydayListObj, obj)
      })
    },
    /** 查询题目管理列表 */
    getList () {
      this.loading = true
      // 深拷贝
      const query = Object.assign({}, this.queryParam)
      if (query.categoryId === 0) {
        query.categoryId = undefined
      }
      listProblemSet(query).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    goTaskInfo (id) {
      this.$router.push({ path: 'task/info/' + id })
    },
    onShowSizeChange (current, pageSize) {
      this.queryParam.pageSize = pageSize
      this.getList()
    },
    changeSize (current, pageSize) {
      this.queryParam.pageNum = current
      this.queryParam.pageSize = pageSize
      this.getList()
    },
    tableChange (pagination, filters, sorter) {
      this.queryParam.orderByColumn = sorter.columnKey
      this.queryParam.isAsc = sorter.order
      this.getList()
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParam.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.queryParam = {
        title: undefined,
        userId: undefined,
        level: undefined,
        description: undefined,
        categoryId: undefined,
        tagId: undefined,
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'id',
        isAsc: 'ascend'
      }
      this.handleQuery()
    },
    onPanelChange (value, mode) {
      console.log('面板发生了更改', value, mode)
      this.getEverydayList(value)
    },
    // 日历点击话跳转
    onCalendarSelect (m) {
      const index = m.format('YYYY-MM-DD')
      if (this.everydayListObj[index]) {
        const everyday = this.everydayListObj[index]
        this.$router.push({ path: '/problemSet/' + everyday.problemId, query: { everydayId: everyday.id } })
      }
    },
    /* 渲染日历头部内容 */
    headerRender ({ value, type, onChange, onTypeChange }) {
      const start = 0
      const end = 12
      const monthOptions = []

      const current = value.clone()
      const localeData = value.localeData()
      const months = []
      for (let i = 0; i < 12; i++) {
        current.month(i)
        months.push(localeData.monthsShort(current))
      }

      for (let index = start; index < end; index++) {
        monthOptions.push(
          <a-select-option class="month-item" key={`${index}`}>
            {months[index]}
          </a-select-option>
        )
      }
      const month = value.month()

      const year = value.year()
      const options = []
      for (let i = year - 10; i < year + 10; i += 1) {
        options.push(
          <a-select-option key={i} value={i} class="year-item">
            {i}
          </a-select-option>
        )
      }
      return (
        <div style={{ padding: '10px' }}>
          <div style={{ marginBottom: '10px', fontSize: '18px' }}>每日一题</div>
          <a-row type="flex" justify="space-between">
            <a-col>
              <a-select
                size="small"
                dropdownMatchSelectWidth={false}
                class="my-year-select"
                onChange={newYear => {
                  const now = value.clone().year(newYear)
                  onChange(now)
                }}
                value={String(year)}
              >
                {options}
              </a-select>
              <a-select
                size="small"
                dropdownMatchSelectWidth={false}
                value={String(month)}
                onChange={selectedMonth => {
                  const newValue = value.clone()
                  newValue.month(parseInt(selectedMonth, 10))
                  onChange(newValue)
                }}
              >
                {monthOptions}
              </a-select>
            </a-col>
          </a-row>
        </div>
      )
    }
  }
}
</script>

<style scoped lang="less">
.body{
  padding: 30px 10px;
  max-width: 1280px;
  min-width: 1000px;
  height: 100%;
  margin: 0 auto;
}

.ownness {
  vertical-align: middle;
  svg, .ownness-icon {
    font-size: 18px;
    margin-right: 4px;
    vertical-align: middle;
  }
  div {
    display: inline-block;
    vertical-align: middle;
  }
}

.category-button {
  padding: 4px 18px;
  font-size: 13px;
  margin-left: 14px;
  border-radius: 50px;
  vertical-align: bottom;
  height: auto;
  background-color: #f9fafc;
}

.category-all-icon {
  font-size: 19px;
  margin-right: 8px;
  display: inline-block;
  vertical-align: bottom;

}
/*.ant-radio-button-wrapper-checked {*/
/*  background-color: #262626 !important*/
/*}*/

.ant-radio-button-wrapper:not(:first-child)::before {
  height: 0;
  width: 0;
}
.right-shadow {
  box-shadow: 0 0 5px #f0f2f5;
  margin-bottom: 15px;
}

.task-h3 {
  font-size: 1.125rem;
  color: rgba(38,38,38,.75);
  margin-bottom: 14px;
  margin-left: 32px;
}

.task {
  width: 100px;
  height: 100px;
  display: inline-block;
  margin-right: 28px;
  .task-img {
    width: 100px;
    height: 100px;
    border-radius: 10px;
  }
  .task-title {
    margin-top: 6px;
    text-align: center;
  }
}
</style>
