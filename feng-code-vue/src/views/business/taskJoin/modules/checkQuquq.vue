<template>
  <a-modal
    ref="selectProblem"
    :title="`批阅 ${queueInfo.id}.${queueInfo.title}`"
    :width="1000"
    :visible="visible"
    :confirm-loading="submitLoading"
    @cancel="close"
    @ok="confirm"
  >

    <div class="page-header-content">
      <a-card :bordered="false" class="content">
        <a-row style="color: #000d17">
          <a-col :span="12">
            <p>题目：{{ queueInfo.title }}</p>
            <p>参与方式：{{ queueInfo.className ?? '个人参与' }}</p>
            <p>用户昵称：{{ queueInfo.nickname }}</p>
          </a-col>
          <a-col :span="12">
            <div>
              <div style="display: inline-block; margin:0 10px 14px 0">状态:</div>
              <dict-tag style="display: inline-block" :options="dict.type['queue_status']" :value="queueInfo.status"/>
            </div>
            <p>语言：{{ queueInfo.typeName }}</p>
            <p>提交时间：{{ queueInfo.createTime }}</p>
          </a-col>
        </a-row>
        <div style="padding: 0 10px">
          <Codemirror :read-only="true" ref="codeMirror"/>
        </div>
        <div style="margin-top: 18px">
          <div style="display: inline-block">得分：</div>
          <div style="display: inline-block">
            <a-input v-model="score" type="number" placeholder="请输入分数 0 - 100" allow-clear/>
          </div>
        </div>
      </a-card>
    </div>
    <template slot="footer">
      <a-button key="back" @click="close">
        关闭
      </a-button>
      <a-button key="submit" type="primary" @click="confirm">
        批阅
      </a-button>
    </template>
  </a-modal>
</template>

<script>

import { getQueue, checkQueue } from '@/api/business/queue'
import Codemirror from '@/components/Codemirror'

export default {
  name: 'CheckQuque',
  props: {
  },
  dicts: ['code_language', 'queue_status'],
  components: {
    Codemirror
  },
  data () {
    return {
      queueId: 0,
      queueInfo: {},
      score: 100,
      // 选中表数组
      // 当前控件配置:
      submitLoading: false,
      visible: false
    }
  },
  created () {
  },
  methods: {
    // 关闭模态框
    close () {
      this.visible = false
      this.selectedRowKeys = []
      this.selectedRows = []
    },
    // 打开(由外面的组件调用)
    handleAdd (id) {
      this.queueInfo = {}
      this.score = 100
      this.queueId = parseInt(id)
      this.getQueueInfo()
      this.visible = true
    },
    getQueueInfo () {
      getQueue(this.queueId).then(res => {
        this.queueInfo = res.data
        this.$refs.codeMirror.setCodeValue(res.data.code)
        setTimeout(() => {
          this.$refs.codeMirror.setCodeValue(res.data.code)
          this.$refs.codeMirror.language = res.data.typeName
        }, 1200)
      })
    },
    // 确认
    confirm () {
      if (this.score === '') {
        this.$message.error('请输入具体得分！')
        return false
      }
      const param = {
        id: this.queueId,
        score: this.score
      }
      console.log('submit', param)
      this.submitLoading = true
      checkQueue(param).then(res => {
        this.$message.success(res.msg)
        this.visible = false
        this.$emit('ok')
      }).finally(() => {
        this.submitLoading = false
      })
    }
  }
}
</script>

<style lang="less">
/* 编辑器高度 */
.CodeMirror {
  /* 动态高度 */
  height: 300px !important;
}
.CodeMirror-gutters {
  left: 0 !important;
}
</style>
