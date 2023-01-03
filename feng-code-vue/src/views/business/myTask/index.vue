<template>
  <a-card class="body">
    <a-button-group>
      <a-button :type="status === -1 ? 'primary' : ''" @click="changeButton(-1)">全部</a-button>
      <a-button :type="status === 0 ? 'primary' : ''" @click="changeButton(0)">待完成</a-button>
      <a-button :type="status === 1 ? 'primary' : ''" @click="changeButton(1)">已完成</a-button>
      <a-button :type="status === 2 ? 'primary' : ''" @click="changeButton(2)">已过期</a-button>
    </a-button-group>
    <div>
      <!-- 数据展示 -->
      <a-table
        :loading="loading"
        :size="tableSize"
        rowKey="id"
        :columns="columns"
        :data-source="list"
        :pagination="false"
        :bordered="tableBordered"
        @change="tableChange"
        :showHeader="false"
      >
        <span slot="imgUrl" slot-scope="text">
          <img :src="text" alt="" style="width: 60px; height: 60px; border-radius: 10px">
        </span>
        <div slot="title" slot-scope="text, record">
          <div style="font-weight: bold; font-size: 16px">{{ record ? record.fengTaskInfo.title : '' }}</div>
          <div>{{ record ? parseTime(record.createTime) : '' }}</div>
        </div>
        <span slot="status" slot-scope="text, record">
          <dict-tag :options="dict.type['task_status']" :value="record.status"/>
        </span>
        <span slot="time" slot-scope="text, record">
          {{ record ? record.endTime ? parseTime(record.endTime) + ' 截止' : '' : '' }}
        </span>
        <span slot="operation" slot-scope="text, record">
          <a @click="goTask(record.taskId)">
            <a-icon type="search" /> 去查看
          </a>
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
import { tableMixin } from '@/store/table-mixin'
import { listMyTask } from '@/api/business/myTask'

export default {
  name: 'MyTask',
  components: {},

  mixins: [tableMixin],
  dicts: ['task_status'],
  data () {
    return {
      status: -1,
      list: [],
      loading: false,
      total: 0,
      // 查询参数
      queryParam: {
        status: -1,
        pageNum: 1,
        pageSize: 10
      },
      columns: [
        {
          title: '图片',
          dataIndex: 'fengTaskInfo.imgUrl',
          scopedSlots: { customRender: 'imgUrl' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '学习任务名称',
          scopedSlots: { customRender: 'title' },
          dataIndex: 'fengTaskInfo.title',
          ellipsis: true,
          align: 'left'
        },
        {
          title: '时间',
          dataIndex: 'time',
          scopedSlots: { customRender: 'time' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '操作',
          dataIndex: 'operation',
          width: '18%',
          scopedSlots: { customRender: 'operation' },
          align: 'center'
        }
      ]
    }
  },
  created () {
    this.getList()
  },
  methods: {
    goTask (taskId) {
      this.$router.push({ path: 'task/info/' + taskId })
    },
    /** 查询任务管理列表 */
    getList () {
      this.loading = true
      this.queryParam.status = this.status
      listMyTask(this.queryParam).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /**
     * 切换按钮
     * @param status
     */
    changeButton (status) {
      this.status = parseInt(status)
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
    onShowSizeChange (current, pageSize) {
      this.queryParam.pageSize = pageSize
      this.getList()
    }
  }
}
</script>

<style scoped lang="less">
.body {
  padding: 0 10px;
  margin: 0 auto;
  max-width: 1280px;
  min-width: 700px;
  height: 100%;
}

</style>
