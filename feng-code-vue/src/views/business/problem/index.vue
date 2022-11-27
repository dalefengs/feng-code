<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!-- 条件搜索 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="题目标题" prop="title">
                <a-input v-model="queryParam.title" placeholder="请输入题目标题" allow-clear/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="难度等级" prop="level">
                  <a-select placeholder="请选择难度等级" v-model="queryParam.level" style="width: 100%" allow-clear>
                    <a-select-option v-for="(d, index) in dict.type.difficulty_level" :key="index" :value="d.value">{{ d.label }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="标签id" prop="tagId">
                  <a-input v-model="queryParam.tagId" placeholder="请输入标签id" allow-clear/>
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
        <a-button type="primary" @click="add()" v-hasPermi="['business:problem:add']">
          <a-icon type="plus" />新增
        </a-button>
        <a-button type="danger" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:problem:remove']">
          <a-icon type="delete" />删除
        </a-button>
        <a-button type="primary" @click="handleExport" v-hasPermi="['business:problem:export']">
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
        :levelOptions="dict.type.difficulty_level"
        :isAutoOptions="dict.type.is_auto"
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
        <span slot="level" slot-scope="text, record">
          <dict-tag :options="dict.type['difficulty_level']" :value="record.level"/>
        </span>
        <span slot="operation" slot-scope="text, record">
          <a-divider type="vertical" v-hasPermi="['business:problem:edit']" />
          <a @click="update(record)" v-hasPermi="['business:problem:edit']">
            <a-icon type="edit" />修改
          </a>
          <a-divider type="vertical" v-hasPermi="['business:problem:remove']" />
          <a @click="handleDelete(record)" v-hasPermi="['business:problem:remove']">
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
import { listProblem, delProblem } from '@/api/business/problem'
import CreateForm from './modules/CreateForm'
import { tableMixin } from '@/store/table-mixin'

export default {
  name: 'Problem',
  components: {
    CreateForm
  },
  mixins: [tableMixin],
  dicts: ['difficulty_level', 'is_auto'],
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
        userId: null,
        level: null,
        description: null,
        categoryId: null,
        tagId: null,
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
          title: '题目标题',
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
        // {
        //   title: '题目说明',
        //   dataIndex: 'description',
        //   ellipsis: true,
        //   align: 'center'
        // },
        {
          title: '所属分类',
          dataIndex: 'categoryName',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '标签',
          dataIndex: 'tagName',
          ellipsis: true,
          align: 'center'
        },
        // {
        //   title: '提示',
        //   dataIndex: 'hint',
        //   ellipsis: true,
        //   align: 'center'
        // },
        {
          title: '提交次数',
          dataIndex: 'submitCount',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '排序',
          dataIndex: 'sort',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '通过次数',
          dataIndex: 'successCount',
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
    // 监听路由变化
    $route (to, from) {
      if (to.query.reload === 'true') {
        this.getList()
      }
    }
  },
  methods: {
    /** 查询题目管理列表 */
    getList () {
      this.loading = true
      listProblem(this.queryParam).then(response => {
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
    /** 新增按钮操作 */
    add () {
      this.$router.push({ path: '/study/business/problem/createProblem', query: { } })
    },
    /** 修改按钮操作 */
    update (row) {
      this.$router.push({ path: '/study/business/problem/createProblem', query: { id: row.id } })
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
          return delProblem(ids)
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
          that.download('business/problem/export', {
            ...that.queryParam
          }, `problem_${new Date().getTime()}.xlsx`)
        },
        onCancel () {}
      })
    }
  }
}
</script>
