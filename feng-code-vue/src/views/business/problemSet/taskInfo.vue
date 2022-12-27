<template>
  <a-card class="body">
    <div class="go-back" @click="$router.go(-1)">
      <a-icon type="caret-left" style="margin-right: 8px;" />
      <span>返回</span>
    </div>
    <div class="task-tilte">
      <div class="task-tilte-left">
        <img :src="taskInfo.imgUrl" alt="" class="task-img">
      </div>
      <div class="task-tilte-right">
        <span>{{ taskInfo.title }}</span>
        <br>
        <div class="task-sub-title">
          <h1>{{ taskInfo.subTitle }}</h1>
        </div>
      </div>
    </div>
    <div class="task-explain">{{ taskInfo.taskExplain }}</div>
    <div style="margin-top: 24px">
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
        <span slot="level" slot-scope="text, record">
          <dict-tag :options="dict.type['difficulty_level']" :value="record.level" />
        </span>
        <span slot="ownness" slot-scope="text, record">
          <dict-tag :options="dict.type['ownness']" :value="record.ownness" />
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
    getTask(this.taskId).then(res => { this.taskInfo = res.data })
    this.getList()
  },
  methods: {
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

.go-back{
  cursor: pointer;
}

.task-tilte {
  .task-tilte-left {
    width: 100px;
    height: 100px;
    margin-top: 22px;
    margin-right: 20px;
  }
  .task-tilte-right {
    vertical-align: middle;
    span {
      display: inline-block;
      font-weight: bold;
      font-size: 23px;
    }
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
