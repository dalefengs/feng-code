<template>
  <a-row class="body" ref="styleVar">
    <a-col :span="12" style="padding: 0 20px; overflow-y: auto; height: calc(100vh - 72px - 50px);">
      <a-tabs default-active-key="1" :active-key="activeKey" @tabClick="tabClick">
        <a-tab-pane key="1" tab="题目描述">
          <h2>{{ problemInfo.title }}</h2>
          <a-tag color="cyan" v-if="problemInfo.level === 0">简单</a-tag>
          <a-tag color="orange" v-if="problemInfo.level === 1">中等</a-tag>
          <a-tag color="red" v-if="problemInfo.level === 2">困难</a-tag>

          <a-tag color="blue">
            <a-icon type="like" />
            {{ problemInfo.likeCount }}
          </a-tag>
          <b> 作者：{{ problemInfo.nickName }}</b>
          <div class="description">
            {{ problemInfo.description }}
          </div>
          <!--     示例     -->
          <div>
            <template v-for="i in (teseCase.length > 3 ? 3 : teseCase.length)">
              <div :key="i">
                <h3>示例 {{ i }}</h3>
                <div class="sample">
                  <b>输入：</b> {{ teseCase[i - 1][0] }}
                  <br>
                  <b>输出：</b> {{ teseCase[i - 1][1] }}
                </div>
              </div>
            </template>
          </div>
          <div>
            <h3>提示：</h3>
            {{ problemInfo.hint }}
          </div>
          <div style="margin: 35px 0">
            <span>提交次数：{{ problemInfo.successCount }}</span>
            <span>通过次数：{{ problemInfo.successCount }}</span>
            <span>通过率：{{ problemInfo.successCount }}</span>
            <a-divider type="vertical" />
          </div>
        </a-tab-pane>
        <a-tab-pane key="2" tab="评论(112)">
          评论
        </a-tab-pane>
        <a-tab-pane key="3" tab="题解(10)">
          题解
        </a-tab-pane>
        <a-tab-pane key="4" tab="提交记录">
          <!-- 数据展示 -->
          <a-table
            :loading="loading"
            rowKey="id"
            :columns="columns"
            :data-source="submitList"
            :pagination="false"
          >
            <span slot="type" slot-scope="text, record">
              <dict-tag :options="dict.type['code_language']" :value="record.type" />
            </span>
            <span slot="status" slot-scope="text, record">
              <dict-tag :options="dict.type['queue_status']" :value="record.status" />
            </span>
            <span slot="operation" slot-scope="text, record">
              <a @click="$refs.createForm.handleUpdate(record, undefined)" v-hasPermi="['business:queue:edit']">
                <a-icon type="search"/> 查看
              </a>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </a-col>
    <a-col :span="12" style="position: relative; height: calc(100vh - 118px)">
      <Codemirror
        :support-mode="problemInfo.languageDicts"
        :code-templates="problemInfo.codeTemplatesParse"
        ref="editCode"
      ></Codemirror>
      <div class="controller" v-show="controllerIcon === 'up'">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="执行结果">
            <div v-if="excuteStatus === 0">请先提交您的代码。</div>
            <div v-if="excuteStatus === 1"><a-icon type="loading" style="margin-right: 8px" />代码正在执行中，请等待...</div>
            <div v-if="excuteStatus > 1">
              <div v-if="excuteStatus === 2">
                <a-alert message="恭喜您，程序通过啦！" type="success" show-icon />
                <div class="excuteTime">
                  <div>用例数量：{{ this.excuteResult.allCount ?? 0 }} 个</div>
                  <div>通过数量：{{ this.excuteResult.successCount ?? 0 }} 个</div>
                  <div>通过率：{{ ((this.excuteResult.successCount / this.excuteResult.allCount) * 100).toFixed(2) }}%</div>
                </div>
                <div class="excuteTime">
                  <div>执行时间：{{ this.excuteResult.excuteTime ?? 0 }} ms</div>
                  <div>执行占用：{{ this.excuteResult.memory ?? 0 }} MB</div>
                </div>
              </div>
              <div v-else>
                <a-alert message="很遗憾，您的程序发生了错误，请再接再励！" type="error" show-icon />
                <div class="excuteTime">
                  <div>用例数量：{{ this.excuteResult.allCount ?? 0 }} 个</div>
                  <div>通过数量：{{ this.excuteResult.successCount ?? 0 }} 个</div>
                  <div>通过率：{{ ((this.excuteResult.successCount / this.excuteResult.allCount) * 100).toFixed(2) }}%</div>
                </div>
                <div class="excuteTime">
                  <div>执行时间：{{ this.excuteResult.excuteTime ?? 0 }} ms</div>
                  <div>执行占用：{{ this.excuteResult.memory ?? 0 }} MB</div>
                </div>
                <div class="sampleError">
                  <div v-if="!errorMsg">
                    <b>用例输入：{{ this.errorTestCase.testCase ?? '' }}</b>
                    <br>
                    <b>程序输出：{{ this.errorTestCase.executeResult ?? '' }}</b>
                    <br>
                    <b>正确结果：{{ this.errorTestCase.correctResult ?? '' }}</b>
                  </div>
                  <div v-else>Error：{{ errorMsg }}</div>
                </div>
              </div>
            </div>
          </a-tab-pane>
        </a-tabs>
      </div>
      <div class="submit-plane">
        <div>
          <a-button @click="controllerClick()">控制台
            <a-icon :type="controllerIcon" style="font-size: 13px" />
          </a-button>
        </div>
        <div class="right">
          <a-button @click="submit()" style="background-color: #2db55d; color: white">提交</a-button>
        </div>
      </div>
    </a-col>
  </a-row>
</template>

<script>
import { getProblem } from '@/api/business/problem'
import commonEmit from '@/utils/commonEmit'
import Codemirror from '@/components/Codemirror'
import { addQueue, getQueue, submitListQueue } from '@/api/business/queue'
import { tableMixin } from '@/store/table-mixin'

export default {
  name: 'ResolveProblem',
  components: {
    Codemirror
  },
  mixins: [tableMixin],
  dicts: ['code_language', 'queue_status'],
  data () {
    return {
      id: undefined,
      activeKey: '1',
      problemInfo: {
        codeTemplatesParse: []
      },
      paramTypes: {},
      teseCase: [],
      controllerIcon: 'up',
      excuteStatus: 0,
      excuteResult: {},
      errorTestCase: {},
      errorMsg: '',
      submitList: [], // 提交列表
      submitParam: {
        type: -1
      },
      loading: false,
      columns: [
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' },
          ellipsis: true,
          align: 'center'
        },
        {
          title: '语言类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' },
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
        }]
    }
  },
  created () {
    if (!this.$route.params.id || this.$route.params.id === '0') {
      this.$message.error('非法访问')
      this.$router.replace({ path: '/problemSet' })
    }
    this.id = this.$route.params.id
    this.getProblemInfo()
    this.getSubmitList()
  },
  mounted () {
    // 隐藏底栏
    setTimeout(() => {
      commonEmit.$emit('onEmithIdeFooter', false)
    }, 500)
    this.changeEditorHeight('calc(65vh - 180px)')
  },
  methods: {
    submit () {
      this.excuteStatus = 0
      this.excuteResult = {}
      this.errorMsg = ''
      this.errorTestCase = {}
      const data = {}
      data.code = this.$refs.editCode.getCodeValue()
      data.type = this.$refs.editCode.languageKey
      data.problemId = this.id
      console.log('submit data', data)
      addQueue(data).then(res => {
        if (res.code !== 200) {
          this.$message.error(res.msg)
        } else {
          this.$message.success('提交成功！')
          this.getSubmitList()
          this.activeKey = '4'
          this.controllerIcon = 'up'
          this.excuteStatus = 1 // 执行中
          let isComplete = false
          setInterval(() => {
            if (isComplete) {
              return false
            }
            let isRefresh = true
            getQueue(res.data).then(res => {
              const status = res.data.status
              if (status === 1 && isRefresh) {
                this.getSubmitList()
                isRefresh = false
              }
              // 判断执行完成
              if (status > 1) {
                isComplete = true
                this.excuteStatus = status
                if (res.data.successMsg) {
                  this.excuteResult = JSON.parse(res.data.successMsg)
                  console.log('excuteResult:', this.excuteResult)
                  if (this.excuteResult.errorTestCase) {
                    this.errorTestCase = this.excuteResult.errorTestCase
                  }
                  if (this.excuteResult.errorMsg) {
                    this.errorMsg = this.excuteResult.errorMsg
                  }
                } else {
                  this.errorMsg = res.data.errorMsg
                }
                this.getSubmitList()
              }
            })
          }, 800)
        }
      })
    },
    getSubmitList () {
      this.submitParam.problemId = this.id
      submitListQueue(this.submitParam).then(res => {
        this.submitList = res.data
      })
    },
    async getProblemInfo () {
      const { data, code } = await getProblem(this.id)
      if (code !== 200 || !data) {
        this.$message.error('数据不存，非法访问')
        await this.$router.replace({ path: '/problemSet' })
      }
      this.problemInfo = data
      this.teseCase = this.parseTestCase(data.testCase)
      this.parseParamType(data.paramTypes)
    },
    controllerClick () {
      // this.$refs.styleVar.style.setProperty('--edit-height', '300px')
      let heightStyle = 'calc(65vh - 180px)'
      if (this.controllerIcon === 'up') {
        this.controllerIcon = 'down'
        heightStyle = 'calc(100vh - 218px)'
      } else {
        this.controllerIcon = 'up'
      }
      this.changeEditorHeight(heightStyle)
    },
    changeEditorHeight (heightStyle) {
      this.$refs.styleVar.$el.setAttribute('style', '--edit-height: ' + heightStyle)
    },
    // 解析JSON测试用例
    parseTestCase (jsonData) {
      const testCaseData = JSON.parse(jsonData)
      if (!testCaseData) {
        return false
      }
      const line = testCaseData.split('\n')
      const testCase = []
      line.forEach(line => {
        // 排除空串
        line = line.trim()
        if (!line) {
          return false
        }
        const single = line.split('=')
        testCase.push(single)
      })
      return testCase
    },
    // 解析 JSON 参数列表
    parseParamType (jsonData) {
      const paramData = JSON.parse(jsonData)
      if (!paramData) {
        return false
      }
    },
    tabClick (index) {
      this.activeKey = index
    }
  }
}
</script>

<style lang="less">
/* 编辑器高度 */
.body {
  height: calc(100vh - 72px - 44px);
  min-width: 1280px;
  background-color: white;
  padding: 0 3px;
  margin: 0;
  //overflow-y: auto;
}

/* 编辑器高度 */
.CodeMirror {
  /* 动态高度 */
  height: var(--edit-height);
  //height: calc(50vh - 120px) !important;
}

.ant-layout-footer {
  padding: 0 !important;
  margin: 0 !important;
}

//.ant-card-body {
//  padding: 3px;
//}

.controller {
  position: absolute;
  bottom: 7px;
  height: 35vh;
  width: 100.4%;
  background-color: white;
  box-shadow: 0 0 15px #f3f3f3;
  padding: 5px 10px;
  overflow: auto;
}

.submit-plane {
  background-color: white;
  position: absolute;
  width: 100.4%;
  height: 50px;
  bottom: 0px;
  padding: 10px 10px 0 10px;
  box-shadow: 0 0 25px #f3f3f3;

  div {
    display: inline-block;

    button {
      border-radius: 8px;
      padding: 0 10px;
    }
  }

  .right {
    position: absolute;
    right: 10px;

    button {
      margin-left: 18px;
    }
  }
}

.description {
  margin-top: 15px;
  margin-bottom: 35px;
  font-size: 16px;
}

.sample {
  background-color: #f7f7f8;
  padding: 15px 15px;
  margin: 15px 0 30px 0;
  border-radius: 10px;
  width: 100%;
  font-size: 16px;
}

.sampleError {
  background-color: #f7f7f8;
  padding: 10px 10px;
  margin: 10px 0 45px 0;
  border-radius: 10px;
  width: 100%;
  font-size: 15px;
  white-space: pre-wrap;
}

.excuteTime {
  div {
    display: inline-block;
    margin: 10px 40px 0 10px;
    font-size: 15px;
  }
}
</style>
