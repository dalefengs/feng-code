<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!-- 条件搜索 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="结束时间" prop="endTime">
                <a-date-picker style="width: 100%" v-model="queryParam.endTime" format="YYYY-MM-DD HH:mm:ss" allow-clear/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="批阅时间" prop="checkTime">
                  <a-date-picker style="width: 100%" v-model="queryParam.checkTime" format="YYYY-MM-DD HH:mm:ss" allow-clear/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleQuery"><a-icon type="search" />查询</a-button>
                <a-button style="margin-left: 8px" @click="resetQuery"><a-icon type="redo" />重置</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 操作 -->
      <div class="table-operations">
        <a-button type="primary" @click="$refs.selectProblemJoin.handleAdd()" v-hasPermi="['business:taskJoin:add']" v-if="taskId !== ''">
          <a-icon type="plus" /> 指定班级
        </a-button>
        <a-button type="danger" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:taskJoin:remove']">
          <a-icon type="delete" /> 删除
        </a-button>
        <table-setting
          :style="{float: 'right'}"
          :table-size.sync="tableSize"
          v-model="columns"
          :refresh-loading="loading"
          @refresh="getList" />
      </div>
      <select-problem-join
        ref="selectProblemJoin"
        :task-id="taskId"
        @ok="getList"
      />
      <!-- 数据展示 -->
      <a-table
        :loading="loading"
        :size="tableSize"
        rowKey="id"
        :columns="columns"
        :data-source="list"
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :pagination="false"
        :bordered="tableBordered"
        @change="tableChange"
      >
        <span slot="status" slot-scope="text, record">
          <dict-tag :options="dict.type['task_status']" :value="record.status"/>
        </span>
        <span slot="endTime" slot-scope="text, record" :title="parseTime(record.endTime)">
          {{ parseTime(record.endTime) }}
        </span>
        <span slot="submitTime" slot-scope="text, record" :title="parseTime(record.submitTime)">
          {{ parseTime(record.submitTime) }}
        </span>
        <span slot="checkTime" slot-scope="text, record" :title="parseTime(record.checkTime)">
          {{ parseTime(record.checkTime) }}
        </span>
        <span slot="operation" slot-scope="text, record">
          <a-divider type="vertical" v-hasPermi="['business:taskJoin:edit']" />
          <a @click="$refs.createForm.handleUpdate(record, undefined)" v-hasPermi="['business:taskJoin:edit']">
            <a-icon type="edit" />修改
          </a>
          <a-divider type="vertical" v-hasPermi="['business:taskJoin:remove']" />
          <a @click="handleDelete(record)" v-hasPermi="['business:taskJoin:remove']">
            <a-icon type="delete" />删除
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
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { listTaskJoin, delTaskJoin } from '@/api/business/taskJoin'
import SelectProblemJoin from '@/views/business/taskJoin/modules/SelectProblemJoin'
import { tableMixin } from '@/store/table-mixin'

export default {
  name: 'TaskJoin',
  mixins: [tableMixin],
  components: {
    SelectProblemJoin
  },
  dicts: ['task_status'],
  data () {
    return {
      taskId: '',
      list: [],
      selectedRowKeys: [],
      selectedRows: [],
      // 高级搜索 展开/关闭
      advanced: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      ids: [],
      loading: false,
      total: 0,
      // 查询参数
      queryParam: {
        status: null,
        endTime: null,
        checkTime: null,
        pageNum: 1,
        pageSize: 10
      },
      columns: [
        {
          title: '编号',
          dataIndex: 'id',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '班级',
          dataIndex: 'className',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '用户昵称',
          dataIndex: 'nickName',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '状态',
          dataIndex: 'status',
          ellipsis: true,
          align: 'center',
          scopedSlots: { customRender: 'status' }
        },
        // {
        //   title: '正确题目数',
        //   dataIndex: 'correctCount',
        //   ellipsis: true,
        //   align: 'center'
        // },
        {
          title: '分数',
          dataIndex: 'score',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '答题时间',
          dataIndex: 'submitTime',
          scopedSlots: { customRender: 'submitTime' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '批阅时间',
          dataIndex: 'checkTime',
          scopedSlots: { customRender: 'checkTime' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '结束时间',
          dataIndex: 'endTime',
          scopedSlots: { customRender: 'endTime' },
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
  filters: {
  },
  created () {
    if (this.$route.query.taskId) {
      this.taskId = this.$route.query.taskId
    }
    this.getList()
  },
  computed: {
  },
  watch: {
    $route (to, from) {
      if (this.$route.query.taskId) {
        this.taskId = this.$route.query.taskId
      }
    }
  },
  methods: {
    /** 查询学习任务学生参与列表 */
    getList () {
      this.loading = true
      this.queryParam.taskId = this.taskId
      listTaskJoin(this.queryParam).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
    },

    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParam.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.queryParam = {
        status: undefined,
        endTime: undefined,
        checkTime: undefined,
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'id',
        isAsc: 'ascend'
      }
      this.handleQuery()
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
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
      this.ids = this.selectedRows.map(item => item.id)
      this.single = selectedRowKeys.length !== 1
      this.multiple = !selectedRowKeys.length
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      var that = this
      const ids = row.id || this.ids
      this.$confirm({
        title: '确认删除所选中数据?',
        content: '当前选中编号为' + ids + '的数据',
        onOk () {
          return delTaskJoin(ids)
            .then(() => {
              that.onSelectChange([], [])
              that.getList()
              that.$message.success(
                '删除成功',
                3
              )
          })
        },
        onCancel () {}
      })
    },
    /** 导出按钮操作 */
    handleExport () {
      var that = this
      this.$confirm({
        title: '是否确认导出?',
        content: '此操作将导出当前条件下所有数据而非选中数据',
        onOk () {
          that.download('business/taskJoin/export', {
            ...that.queryParam
          }, `taskJoin_${new Date().getTime()}.xlsx`)
        },
        onCancel () {}
      })
    }
  }
}
</script>
