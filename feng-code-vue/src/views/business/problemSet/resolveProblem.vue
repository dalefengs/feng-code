<template>
  <a-row class="body-code" ref="styleVar">
    <a-col :span="12" style="padding: 0 20px; overflow-y: auto; height: calc(100vh - 72px - 50px);">
      <a-tabs default-active-key="1" :active-key="activeKey" @tabClick="tabClick">
        <a-tab-pane key="1" tab="题目描述">
          <h2>{{ problemInfo.title }}</h2>
          <a-tag color="cyan" v-if="problemInfo.level === 0" class="give-tag">简单</a-tag>
          <a-tag color="orange" v-if="problemInfo.level === 1" class="give-tag">中等</a-tag>
          <a-tag color="red" v-if="problemInfo.level === 2" class="give-tag">困难</a-tag>

          <a-tag color="blue" @click="give(null)" class="give-tag">
            <a-icon type="like" :theme="problemInfo.isGive === 1 ? 'filled' : 'outlined'" />
            {{ problemInfo.giveCount }}
          </a-tag>
          <b> 作者：{{ problemInfo.nickName }}</b>
          <div class="description">
            {{ problemInfo.description }}
          </div>
          <!--     示例     -->
          <div>
            <template v-for="i in (teseCase.length > 3 ? 3 : teseCase.length)">
              <div :key="i">
                <h3> 示例 {{ i }}：</h3>
                <div class="sample">
                  <b>输入：</b> {{ teseCase[i - 1][0] }}
                  <br>
                  <b>输出：</b> {{ teseCase[i - 1][1] }}
                </div>
              </div>
            </template>
          </div>
          <div style="margin-bottom: 60px">
            <h3>提示：</h3>
            {{ problemInfo.hint }}
          </div>
          <div class="submit-rete">
            <span> 提交次数：{{ problemInfo.submitCount }} 次</span>
            <a-divider type="vertical" />
            <span> 通过次数：{{ problemInfo.successCount }} 次</span>
            <a-divider type="vertical" />
            <span> 通过率：{{ problemInfo.submitCount ? ((problemInfo.successCount / problemInfo.submitCount) * 100 ).toFixed(2) : '0.00' }} %</span>
          </div>
        </a-tab-pane>
        <a-tab-pane key="2" :tab="'讨论(' + (problemInfo.commentCount ?? 0) + ')'">
          <a-comment style="">
            <a-avatar
              slot="avatar"
              :src="avatar"
              v-if="avatar !== ''"
            />
            <a-avatar v-else icon="user" slot="avatar" />
            <div slot="content">
              <a-form-item>
                <a-textarea :rows="4" :value="commentContent" placeholder="请输出您要发表的内容" @change="handleCommentContent"/>
              </a-form-item>
              <a-form-item>
                <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmitComment(undefined, undefined, undefined)">
                  提交
                </a-button>
              </a-form-item>
            </div>
          </a-comment>
          <!-- 列表 -->
          <a-comment v-for="(item, index) in commentList" :key="index">
            <div slot="actions" key="comment-nested-reply-to">
              <div>
                <span style="color: #1890ff; cursor: pointer" @click="handelReplyShow(item.id)">回复</span>
              </div>
              <div style="margin-top: 10px; display: none;" :ref="'reply_'+item.id">
                <div style="display: inline-block">
                  <a-avatar
                    slot="avatar"
                    :src="avatar"
                    v-if="avatar !== ''"
                  />
                  <a-avatar v-else icon="user" slot="avatar" />
                </div>
                <a-form-item class="comment-input">
                  <a-input :ref="'reply_input_'+item.id" placeholder="请输入评论" allow-clear />
                  <div class="comment-button">
                    <a-button @click="handleReplyCancel(item.id)" style="margin-right: 16px">取消</a-button>
                    <a-button type="primary" @click="handleSubmitComment(item.id, item.id, 0)">提交</a-button>
                  </div>
                </a-form-item>
              </div>
            </div>
            <a slot="author" style="color: #2d2d2d; font-size: 15px">{{ item.nickName }}</a>
            <a-avatar
              slot="avatar"
              v-if="item.avatar !== '' "
              :src="item.avatar"
              :alt="item.nickName"
            />
            <a-avatar v-else icon="user" slot="avatar" :alt="item.nickName" />
            <p slot="content">
              {{ item.content }}
            </p>
            <a-comment v-if="item.children" v-for="(child, i) in item.children" :key="i">
              <div slot="actions" key="comment-nested-reply-to">
                <div>
                  <span style="color: #1890ff; cursor: pointer" @click="handelReplyShow(child.id)">回复</span>
                </div>
                <div style="margin-top: 10px; display: none;" :ref="'reply_'+child.id">
                  <div style="display: inline-block">
                    <a-avatar
                      slot="avatar"
                      :src="avatar"
                      v-if="avatar !== ''"
                    />
                    <a-avatar v-else icon="user" slot="avatar" />
                  </div>
                  <a-form-item class="comment-input">
                    <a-input :placeholder="`回复 ${child.nickName} 的评论`" :ref="'reply_input_'+child.id" allow-clear />
                    <div class="comment-button">
                      <a-button @click="handleReplyCancel(child.id)" style="margin-right: 16px">取消</a-button>
                      <a-button type="primary" @click="handleSubmitComment(child.id, item.id, child.userId)">提交</a-button>
                    </div>
                  </a-form-item>
                </div>
              </div>
              <a slot="author" style="font-size: 15px">
                {{ child.nickName }}
                <span v-if="child.replyUserId > 0">
                  <a-icon type="caret-right" /> {{ child.replyNickName }}
                </span>
              </a>
              <a-avatar
                slot="avatar"
                v-if="child.avatar !== '' "
                :src="child.avatar"
                :alt="child.nickName"
              />
              <a-avatar v-else icon="user" slot="avatar" :alt="child.nickName" />
              <p slot="content">
                {{ child.content }}
              </p>
            </a-comment>
          </a-comment>
          <!-- 分页 -->
          <a-pagination
            class="ant-table-pagination"
            show-size-changer
            show-quick-jumper
            :current="commentQueryParam.pageNum"
            :total="commentTotal"
            :page-size="commentQueryParam.pageSize"
            :showTotal="total => `共 ${total} 条`"
            @showSizeChange="onCommentShowSizeChange"
            @change="changeCommentSize"
          />
        </a-tab-pane>
        <!-- <a-tab-pane key="3" tab="题解(10)">
          题解
        </a-tab-pane>-->
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
              <a @click="handleViewQuque(record.id)" v-hasPermi="['business:queue:query']">
                <a-icon type="search"/> 查看
              </a>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </a-col>
    <a-col :span="12" class="col-right" v-show="!showQuqueInfo" >
      <Codemirror
        :support-mode="problemInfo.languageDicts"
        :code-templates="problemInfo.codeTemplatesParse"
        ref="editCode"
      ></Codemirror>
      <div class="controller" v-show="controllerIcon === 'up'">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="执行结果">
            <div v-if="taskJoinInfo.status === 2" style="color: crimson">当前学习任务已于 {{ taskJoinInfo.endTime }} 过期, 不允许提交！</div>
            <div v-else-if="excuteStatus === 0">请先提交您的代码。</div>
            <div v-else-if="excuteStatus === 1"><a-icon type="loading" style="margin-right: 8px" />代码正在执行中，请等待...</div>
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
              <div v-else-if="excuteStatus === 5">
                <a-alert message="恭喜你提交成功，请耐心等待教师批阅！" type="info" show-icon />
                <div class="excuteTime">
                  <div>执行时间：{{ this.excuteResult.excuteTime ?? 0 }} ms</div>
                  <div>执行占用：{{ this.excuteResult.memory ?? 0 }} MB</div>
                </div>
                <div class="sampleError">
                  <div v-if="msg">
                    <b>程序输出：<br>{{ this.msg ?? '' }}</b>
                  </div>
                  <div v-else>Error：{{ errorMsg }}</div>
                </div>
              </div>
              <div v-else>
                <a-alert message="很遗憾，您的程序发生了错误，请再接再励！" type="error" show-icon />
                <div class="excuteTime">
                  <div>用例数量：{{ this.excuteResult.allCount ?? 0 }} 个</div>
                  <div>通过数量：{{ this.excuteResult.successCount ?? 0 }} 个</div>
                  <div>通过率：{{ this.excuteResult.allCount > 0 ? ((this.excuteResult.successCount / this.excuteResult.allCount) * 100).toFixed(2) : '0.00' }}%</div>
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
        <div class="right" >
          <a-button @click="$notification.error({ message: '当前学习任务已过期，不允许提交' })" v-if="taskJoinInfo.status === 2" >提交</a-button>
          <a-button @click="submit()" v-else style="background-color: #2db55d; color: white">提交</a-button>
        </div>
      </div>
    </a-col>
    <a-col :span="12" class="col-right" v-show="showQuqueInfo">
      <div class="queue-nav">
        <a-button @click="showQuqueInfo = false">返回</a-button>
      </div>
      <!-- 个人信息 -->
      <div style="margin-top: 20px;padding:0 20px">
        <span>
          <a-avatar
            size="large"
            :src="queueInfo.avatar"
            v-if="queueInfo.avatar !== ''"
          />
          <a-avatar v-else size="large" icon="user" slot="avatar" />
        </span>
        <span style="margin-left: 14px; font-size: 20px; vertical-align: middle">{{ queueInfo.nickname }}</span>
        <span style="float: right; font-size: 16px; vertical-align: middle">{{ queueInfo.createTime }}</span>
      </div>
      <br>
      <div style="height: 40px">
        <div v-show="Object.keys(queueInfo).length > 0">
          <a-alert message="恭喜您，程序通过啦！" type="success" show-icon v-if="queueInfo.status === 2"/>
          <a-alert message="恭喜你提交成功，请耐心等待教师批阅！" type="info" show-icon v-else-if="queueInfo.status === 5" />
          <a-alert message="很遗憾，您的程序发生了错误，请再接再励！" type="error" show-icon v-else />
        </div>
      </div>

      <Codemirror
        ref="queueShowCode"
        :read-only="true"
      ></Codemirror>
      <br>
      <div class="excuteTime">
        <div>用例数量：{{ this.excuteResult.allCount ?? 0 }} 个</div>
        <div>通过数量：{{ this.excuteResult.successCount ?? 0 }} 个</div>
        <div>通过率：{{ this.excuteResult.allCount > 0 ? ((this.excuteResult.successCount / this.excuteResult.allCount) * 100).toFixed(2) : '0.00' }}%</div>
      </div>
      <div class="excuteTime">
        <div>执行时间：{{ this.excuteResult.excuteTime ?? 0 }} ms</div>
        <div>执行占用：{{ this.excuteResult.memory ?? 0 }} MB</div>
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
import { addGive } from '@/api/business/give'
import { addComment, listComment } from '@/api/business/comment'
import storage from 'store'
import { USER_AVATAR } from '@/store/mutation-types'
import { getTaskJoin } from '@/api/business/taskJoin'

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
      avatar: '',
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
      msg: '',
      queueInfo: {}, // 查看提交记录信息详情
      showQuqueInfo: false,
      taskId: 0,
      taskJoinId: 0,
      taskJoinInfo: {},
      everydayId: 0,
      submitList: [], // 提交列表
      commentList: [], // 评论列表
      commentTotal: 0,
      commentContent: '',
      submitting: false,
      commentQueryParam: {
        pageSize: 20,
        pageNum: 1
      },
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
    this.avatar = storage.get(USER_AVATAR)
    if (this.$route.query.taskId) {
      this.taskId = this.$route.query.taskId
    }
    if (this.$route.query.taskJoinId) {
      this.taskJoinId = this.$route.query.taskJoinId
      getTaskJoin(this.taskJoinId).then(res => { this.taskJoinInfo = res.data })
    }
    if (this.$route.query.everydayId) {
      this.everydayId = this.$route.query.everydayId
    }
    this.id = this.$route.params.id
    this.getProblemInfo()
    this.getCommentList()
  },
  mounted () {
    // 隐藏底栏
    setTimeout(() => {
      commonEmit.$emit('onEmithIdeFooter', false)
    }, 500)
    this.changeEditorHeight('calc(65vh - 180px)')
  },
  methods: {
    /**
     * 查看提交记录详情
     * @param id
     */
    handleViewQuque (id) {
      this.queueInfo = {}
      this.showQuqueInfo = true
      getQueue(id).then(res => {
        this.queueInfo = res.data
        this.$refs.queueShowCode.setCodeValue(res.data.code)
        this.$refs.queueShowCode.language = res.data.typeName
        if (res.data.successMsg) {
          this.excuteResult = JSON.parse(res.data.successMsg)
          console.log('excuteResult:', this.excuteResult)
          if (this.excuteResult.errorTestCase) {
            this.errorTestCase = this.excuteResult.errorTestCase
          }
          if (this.excuteResult.msg) {
            this.msg = this.excuteResult.msg
          }
          if (this.excuteResult.errorMsg) {
            this.errorMsg = this.excuteResult.errorMsg
          }
        } else {
          this.errorMsg = res.data.errorMsg
        }
      }).catch((err) => {
        console.error(err)
        this.showQuqueInfo = false
      })
    },
    handleReplyCancel (id) {
      this.$refs['reply_' + id][0].style.display = 'none'
    },
    handelReplyShow (id) {
      this.$refs['reply_' + id][0].style.display = 'inline-block'
    },
    handleSubmitComment (inputId, pid, replyUserId) {
      if (!this.commentContent && !inputId) {
        this.$notification.error({ message: '请输入你的讨论内容！' })
        return false
      }
      const param = {
        problemId: this.id,
        content: this.commentContent
      }
      if (inputId) {
        param.pid = pid
        const content = this.$refs['reply_input_' + inputId][0].stateValue
        if (!content) {
          this.$notification.error({ message: '请输入你的回复内容！' })
          this.submitList = false
          return false
        }
        param.content = content
      } else {
        this.submitting = true
      }
      if (replyUserId) {
        param.replyUserId = replyUserId
      }
      addComment(param).then(res => {
        this.$notification.success({ message: '您的讨论提交成功！' })
        this.getCommentList()
        this.problemInfo.commentCount++
      }).finally(() => {
        this.submitting = false
        this.commentContent = ''
        if (inputId) {
          this.handleReplyCancel(inputId)
          this.$refs['reply_input_' + inputId][0].setValue('')
        }
      })
    },
    getCommentList () {
      this.commentQueryParam.problemId = this.id
      listComment(this.commentQueryParam).then(res => {
        this.commentList = res.rows
        this.commentTotal = res.total
      })
    },
    give (commentId) {
      const param = {
        problemId: this.id,
        commentId: commentId ?? 0
      }
      addGive(param).then(res => {
        this.$notification.success({
          message: '点赞成功！'
        })
        this.problemInfo.giveCount = this.problemInfo.giveCount + 1
        this.problemInfo.isGive = 1
      })
    },
    submit () {
      this.excuteStatus = 0
      this.excuteResult = {}
      this.errorMsg = ''
      this.msg = ''
      this.errorTestCase = {}
      const data = {}
      data.code = this.$refs.editCode.getCodeValue()
      data.type = this.$refs.editCode.languageKey
      data.problemId = this.id
      data.taskId = this.$route.query.taskId
      data.everydayId = this.$route.query.everydayId

      console.log('submit data', data)
      addQueue(data).then(res => {
        if (res.code !== 200) {
          this.$message.error(res.msg)
        } else {
          this.$notification.success({
            message: '提交成功，请耐心等待！'
          })
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
                  if (this.excuteResult.msg) {
                    this.msg = this.excuteResult.msg
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
      this.submitParam.taskId = this.taskId
      this.submitParam.everydayId = this.everydayId
      this.submitParam.isAuto = this.problemInfo.isAuto
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
    handleCommentContent (e) {
      this.commentContent = e.target.value
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
    onCommentShowSizeChange (current, pageSize) {
      this.commentQueryParam.pageSize = pageSize
      this.getCommentList()
    },
    changeCommentSize (current, pageSize) {
      this.commentQueryParam.pageNum = current
      this.commentQueryParam.pageSize = pageSize
      this.getCommentList()
    },
    tabClick (index) {
      this.activeKey = index
      if (index === '2') {
        this.getCommentList()
      }
      if (index === '4') {
        this.getSubmitList()
      }
    }
  }
}
</script>

<style lang="less">
/* 编辑器高度 */
.body-code {
  height: calc(100vh - 72px - 44px);
  min-width: 1280px;
  background-color: white;
  padding: 0 3px;
  margin: 0;
  //overflow-y: auto;
}

.queue-nav {
  height: 44px;
  line-height: 44px;
  width: 100%;
  border-bottom: #e8e8e8 1px solid;
  padding-left: 10px;
}

.col-right {
  position: relative;
  height: calc(100vh - 118px);
}

.comment-input {
  display: inline-block;
  margin-left: 12px;
  width: 420px;
  input {
    height: 36px;
    border-radius: 20px;
    vertical-align: bottom;
  }
}

.comment-button {
  float: right;
  margin-right: 6px;
}

.submit-rete {
  margin: 30px 0 30px -22px;
  padding: 0 20px;
  background-color: #ffffff;
  height: 43px;
  line-height: 43px;
  position: fixed;
  bottom: -1px;
  box-shadow: 0 0 25px #f3f3f3;
  width: 48.5%;
  min-width: calc(1280px / 2);
}

.give-tag {
  font-size: 15px;
  padding: 2px 10px;
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
  padding: 10px 15px;
  margin: 15px 0 20px 0;
  border-radius: 10px;
  width: 100%;
  font-size: 15px;
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
