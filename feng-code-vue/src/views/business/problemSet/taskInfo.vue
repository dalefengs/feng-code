<template>
  <a-card class="body">
    <div class="go-back" @click="$router.go(-1)">
      <a-icon type="caret-left" style="margin-right: 8px;" />
      <span>返回</span>
    </div>
    <div class="task-tilte">
      <div class="task-tilte-left">
        <img :src="taskInfo.imgUrl" alt="" class="task-img" v-show="taskInfo.imgUrl">
      </div>
      <div class="task-tilte-center">
        <span>{{ taskInfo.title }}</span>
        <br>
        <div class="task-sub-title">
          <h1>{{ taskInfo.subTitle }}</h1>
        </div>
      </div>
      <div class="task-tilte-right" v-show="taskInfo.isJoin === '0'">
        <a-button type="primary" @click="showConfirm">
          立即参与
        </a-button>
      </div>
    </div>
    <div class="task-explain">{{ taskInfo.taskExplain }}</div>
    <div style="margin-top: 24px; position: relative">
      <div class="cover-block" v-show="taskInfo.isJoin === '0'">
        <div class="cover-block-join" @click="showConfirm">
          <a-icon type="lock" />
        </div>
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
        <span slot="level" slot-scope="text, record">
          <dict-tag :options="dict.type['difficulty_level']" :value="record.level" />
        </span>

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
    </div>
  </a-card>
</template>

<script>

import { getTask } from '@/api/business/task'
import { addTaskJoin } from '@/api/business/taskJoin'
import { tableMixin } from '@/store/table-mixin'
import { listProblemTask } from '@/api/business/problem'

export default {
  name: 'TaskInfo',
  dicts: ['difficulty_level', 'ownness', 'is_auto'],
  mixins: [tableMixin],
  data () {
    return {
      taskId: undefined,
      taskInfo: {},
      // 查询参数
      loading: false,
      total: 0,
      list: [],
      queryParam: {
        title: '',
        pageNum: 1,
        pageSize: 20
      },
      // 绑定每行单击事件
      problemRowClick: record => ({
        on: {
          click: () => {
            console.log('click:', record.id)
            this.$router.push({ path: '/problemSet/' + record.id, query: { taskId: this.taskId } })
          }
        }
      }),
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
          ellipsis: true,
          align: 'center'
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
          title: '提交次数',
          dataIndex: 'submitCount',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '通过次数',
          dataIndex: 'successCount',
          ellipsis: true,
          align: 'center'
        }
      ]
    }
  },
  created () {
    if (!this.$route.params.id || this.$route.params.id === '0') {
      this.$message.error('非法访问')
      this.$router.replace({ path: '/problemSet' })
    }
    this.taskId = this.$route.params.id
    getTask(this.taskId).then(res => {
      this.taskInfo = res.data
    })
    this.getList()
  },
  methods: {
    showConfirm () {
      const param = {
        taskId: this.taskId
      }
      const self = this
      this.$confirm({
        title: '确认提示',
        content: `您是否要参加 ${this.taskInfo.title} 学习计划？`,
        onOk () {
          return new Promise((resolve, reject) => {
            addTaskJoin(param).then(res => {
              if (res.code === 200) {
                self.$message.success('加入学习计划成功！')
                getTask(self.taskId).then(res => {
                  self.taskInfo = res.data
                })
              }
              resolve()
            })
          })
        },
        onCancel () {
        }
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
      query.taskId = this.taskId
      console.log(this.queryParam)
      listProblemTask(query).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
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
    }
  }
}
</script>

<style scoped lang="less">
.body {
  padding: 0 10px;
  margin: 0 auto;
  width: 950px;
  min-width: 950px;
  height: 100%;
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

.cover-block {
  position: absolute;
  z-index: 2000;
  background-color: rgba(0, 0, 0, 0.2);
  height: 100%;
  width: 100%;
  text-align: center;
  .cover-block-join {
    position: relative;
    top: 50%;
    margin-top: -20px;
    display: inline-block;
    z-index: 2001;
    cursor: pointer;
    width: 150px;
    height: 50px;
    line-height: 50px;
    color: #ffffff;
    text-align: center;
    font-size: 50px;
  }
}

.go-back{
  cursor: pointer;
}

.task-tilte {
  .task-tilte-left {
    width: 12%;
    height: 100px;
    margin-top: 22px;
    margin-right: 20px;
  }
  .task-tilte-center {
    width: 65%;
    vertical-align: middle;
    span {
      display: inline-block;
      font-weight: bold;
      font-size: 23px;
    }
  }
  .task-tilte-right {
    width: 15%;
    text-align: right;
  }
  div {
    display: inline-block;
  }
}
.task-img {
  width: 100px;
  height: 100px;
  border-radius: 10px;
}
.task-sub-title {
  margin-top: 16px;
}
.task-explain {
  margin-top: 20px;
}
</style>
