<template>
  <a-modal
    ref="selectProblem"
    :title="'选择题目'"
    :width="900"
    :visible="visible"
    :confirm-loading="submitLoading"
    @cancel="close"
    @ok="confirm"
  >

    <div class="page-header-content">
      <a-card :bordered="false" class="content">
        <!-- 条件搜索 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="48">
              <a-col :md="8" :sm="24">
                <a-form-item label="题目标题">
                  <a-input v-model="queryParam.userName" placeholder="请输入" allow-clear/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <span class="table-page-search-submitButtons">
                  <a-button type="primary" @click="handleQuery"><a-icon type="search" />查询</a-button>
                  <a-button style="margin-left: 8px" @click="resetQuery"><a-icon type="redo" />重置</a-button>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <a-table
          :loading="loading"
          size="small"
          rowKey="id"
          :columns="columns"
          :data-source="list"
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
          :scroll="{ y: tableHeight }"
          :pagination="false">
          <span slot="level" slot-scope="text, record">
            <dict-tag :options="dict.type['difficulty_level']" :value="record.level"/>
          </span>
          <span slot="createTime" slot-scope="text, record">
            {{ parseTime(record.createTime) }}
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
    </div>
  </a-modal>
</template>

<script>

import { listProblem } from '@/api/business/problem'
import { addTaskProblem } from '@/api/business/taskProblem'

export default {
  name: 'SelectProblem',
  props: {
    taskId: {
      type: String,
      required: true
    }
  },
  components: {
  },
  dicts: ['difficulty_level'],
  data () {
    return {
      // 表格数据
      list: [],
      selectedRowKeys: [],
      selectedRows: [],
        // 表格的高度
      tableHeight: document.documentElement.scrollHeight - 500 + 'px',
      // 选中表数组
      problemIds: [],
      loading: false,
      total: 0,
      // 当前控件配置:
      submitLoading: false,
      visible: false,
      queryParam: {
        pageNum: 1,
        pageSize: 10,
        title: undefined
      },
      // 表格属性
      columns: [
        {
          title: '标题',
          dataIndex: 'title',
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
          title: '通过次数',
          dataIndex: 'successCount',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          scopedSlots: { customRender: 'createTime' },
          ellipsis: true,
          align: 'center'
        }
      ]
    }
  },
  created () {
  },
  methods: {
    // 查询表数据
    getList () {
      this.loading = true
      this.queryParam.taskId = parseInt(this.taskId)
      listProblem(this.queryParam).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 关闭模态框
    close () {
      this.visible = false
      this.selectedRowKeys = []
      this.selectedRows = []
    },
    // 打开(由外面的组件调用)
    handleAdd () {
      this.queryParam.taskId = parseInt(this.taskId)
      this.visible = true
      this.getList()
    },
    // 确认
    confirm () {
      const param = {
        taskId: this.queryParam.taskId,
        problemIds: this.problemIds
      }
      console.log('submit', param)
      this.submitLoading = true
      addTaskProblem(param).then(res => {
        this.$message.success(res.msg)
        this.visible = false
        this.$emit('ok')
      }).finally(() => {
        this.submitLoading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParam.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.dateRange = []
      this.queryParam = {
        pageNum: 1,
        pageSize: 10,
        taskId: this.queryParam.taskId,
        title: undefined
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
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
      this.problemIds = this.selectedRows.map(item => item.id)
      this.single = selectedRowKeys.length !== 1
      this.multiple = !selectedRowKeys.length
    }
  }
}
</script>
