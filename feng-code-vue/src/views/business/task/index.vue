<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!-- 条件搜索 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="任务名称" prop="title">
                <a-input v-model="queryParam.title" placeholder="请输入学习任务名称" allow-clear/>
              </a-form-item>
            </a-col>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleQuery"><a-icon type="search" />查询</a-button>
                <a-button style="margin-left: 8px" @click="resetQuery"><a-icon type="redo" />重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 操作 -->
      <div class="table-operations">
        <a-button type="primary" @click="$refs.createForm.handleAdd()" v-hasPermi="['business:task:add']">
          <a-icon type="plus" />新增
        </a-button>
        <a-button type="primary" :disabled="single" @click="$refs.createForm.handleUpdate(undefined, ids)" v-hasPermi="['business:task:edit']">
          <a-icon type="edit" />修改
        </a-button>
        <a-button type="danger" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:task:remove']">
          <a-icon type="delete" />删除
        </a-button>
        <a-button type="primary" @click="handleExport" v-hasPermi="['business:task:export']">
          <a-icon type="download" />导出
        </a-button>
        <table-setting
          :style="{float: 'right'}"
          :table-size.sync="tableSize"
          v-model="columns"
          :refresh-loading="loading"
          @refresh="getList" />
      </div>
      <!-- 增加修改 -->
      <create-form
        ref="createForm"
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
        <span slot="imgUrl" slot-scope="text">
          <img :src="text" style="max-width: 100px; height: auto" alt="">
        </span>
        <span slot="operation" slot-scope="text, record">
          <a-divider type="vertical" v-hasPermi="['business:task:management']" />
          <a @click="goManagement(record)" v-hasPermi="['business:task:management']">
            <a-icon type="solution" /> 管理计划
          </a>
          <a-divider type="vertical" v-hasPermi="['business:superviseClass:management']" />
          <a @click="goSuperviseClass(record)" v-hasPermi="['business:task:superviseClass']">
            <a-icon type="solution" /> 管理班级
          </a>
          <br>
          <a-divider type="vertical" v-hasPermi="['business:task:edit']" />
          <a @click="$refs.createForm.handleUpdate(record, undefined)" v-hasPermi="['business:task:edit']">
            <a-icon type="edit" /> 修改
          </a>
          <a-divider type="vertical" v-hasPermi="['business:task:remove']" />
          <a @click="handleDelete(record)" v-hasPermi="['business:task:remove']">
            <a-icon type="delete" /> 删除
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
import { listTask, delTask } from '@/api/business/task'
import CreateForm from './modules/CreateForm'
import { tableMixin } from '@/store/table-mixin'

export default {
  name: 'Task',
  components: {
    CreateForm
  },
  mixins: [tableMixin],
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
        title: null,
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
          title: '学习任务名称',
          dataIndex: 'title',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '子标题',
          dataIndex: 'subTitle',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '任务图片',
          dataIndex: 'imgUrl',
          ellipsis: true,
          align: 'center',
          scopedSlots: { customRender: 'imgUrl' }
        },
        {
          title: '任务简介说明',
          dataIndex: 'taskExplain',
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
    // 跳转管理页面 添加题目
    goManagement (record) {
      this.$router.push({ path: '/task/taskProblem', query: { taskId: record.id } })
    },
    // 跳转管理页面 添加班级
    goSuperviseClass (record) {
      this.$router.replace({ path: '/task/taskJoin', query: { taskId: record.id } })
    },
    /** 查询学习任务列表 */
    getList () {
      this.loading = true
      listTask(this.queryParam).then(response => {
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
        title: undefined,
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
          return delTask(ids)
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
          that.download('business/task/export', {
            ...that.queryParam
          }, `task_${new Date().getTime()}.xlsx`)
        },
        onCancel () {}
      })
    }
  }
}
</script>
