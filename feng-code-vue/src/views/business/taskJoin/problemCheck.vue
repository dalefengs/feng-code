<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!-- 条件搜索 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="用户昵称" prop="userId">
                <a-input v-model="queryParam.nickname" placeholder="请输入昵称" allow-clear/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="题目标题" prop="title">
                <a-input v-model="queryParam.title" placeholder="请输入标题" allow-clear/>
              </a-form-item>
            </a-col>
            <a-col :md="!advanced && 2 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleQuery"><a-icon type="search" />查询</a-button>
              </span>
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
        @change="tableChange"
      >
        <span slot="className" slot-scope="text, record">
          {{ record.className ?? '个人参与' }}
        </span>
        <span slot="type" slot-scope="text, record">
          <dict-tag :options="dict.type['code_language']" :value="record.type"/>
        </span>
        <span slot="status" slot-scope="text, record">
          <dict-tag :options="dict.type['queue_status']" :value="record.status"/>
        </span>
        <span slot="operation" slot-scope="text, record">
          <a @click="check(record)">
            <a-icon type="search" /> 批阅
          </a>
          <a-divider type="vertical" />
          <a @click="handleDelete(record)">
            <a-icon type="delete" /> 退回
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
      <check-quque ref="checkQuque" @ok="getList" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { tableMixin } from '@/store/table-mixin'
import { delQueue, listCheck } from '@/api/business/queue'
import CheckQuque from '@/views/business/taskJoin/modules/checkQuquq'

export default {
  name: 'ProblemCheck',
  components: { CheckQuque },
  mixins: [tableMixin],
  dicts: ['code_language', 'queue_status'],
  data () {
    return {
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
        nickname: null,
        title: null,
        type: -1,
        status: undefined,
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
          title: '参与方式(班级 | 个人)',
          dataIndex: 'className',
          scopedSlots: { customRender: 'className' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '昵称',
          dataIndex: 'nickname',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '题目',
          dataIndex: 'title',
          ellipsis: true,
          align: 'center'
        },
        // {
        //   title: '语言类型',
        //   dataIndex: 'type',
        //   scopedSlots: { customRender: 'type' },
        //   ellipsis: true,
        //   align: 'center'
        // },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '提交时间',
          dataIndex: 'createTime',
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
    this.getList()
  },
  computed: {
  },
  watch: {
  },
  methods: {
    check (record) {
      this.$refs.checkQuque.handleAdd(record.id)
    },
    /** 查询任务管理列表 */
    getList () {
      this.loading = true
      listCheck(this.queryParam).then(response => {
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
        userId: undefined,
        problemId: undefined,
        type: -1,
        status: undefined,
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
        title: '确认退回所选中数据?',
        content: '当前选中编号为' + ids + '的数据',
        onOk () {
          return delQueue(ids)
            .then(() => {
              that.onSelectChange([], [])
              that.getList()
              that.$message.success(
                '退回成功',
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
          that.download('business/queue/export', {
            ...that.queryParam
          }, `queue_${new Date().getTime()}.xlsx`)
        },
        onCancel () {}
      })
    }
  }
}
</script>
