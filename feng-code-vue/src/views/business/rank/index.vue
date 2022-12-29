<template>
  <a-card class="body">
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
      <div slot="successRate" slot-scope="text, record">
        <div :title="JSON.stringify(record)">{{ ((record.successCount / record.totalCount) * 100).toFixed(2) }} %</div>
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
</template>

<script>
import { listRank } from '@/api/business/queue'
import { tableMixin } from '@/store/table-mixin'

export default {
  name: 'Rank',
  mixins: [tableMixin],
  data () {
    return {
      list: [],
      loading: false,
      total: 0,
      queryParam: {
        pageNum: 1,
        pageSize: 50
      },
      columns: [
        {
          title: '排名',
          dataIndex: 'ranking',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '用户昵称',
          dataIndex: 'nickname',
          ellipsis: true,
          align: 'center'
        },
        {
          title: '做题次数',
          dataIndex: 'totalCount',
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
          title: '正确率',
          dataIndex: 'successRate',
          scopedSlots: { customRender: 'successRate' },
          ellipsis: true,
          align: 'center'
        }
      ]
    }
  },
  created () {
    this.getList()
  },
  methods: {
    /** 查询任务管理列表 */
    getList () {
      this.loading = true
      listRank(this.queryParam).then(response => {
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
  max-width: 1280px;
  min-width: 700px;
  height: 100%;
}
</style>
