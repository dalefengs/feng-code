<template>
  <a-row class="body" ref="styleVar">
    <a-col :span="12" style="padding: 0 20px; overflow-y: auto; height: calc(100vh - 72px - 50px);">
      <a-tabs default-active-key="1">
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
          提交记录
        </a-tab-pane>
      </a-tabs>
    </a-col>
    <a-col :span="12" style="position: relative; height: calc(100vh - 118px)">
      <Codemirror :support-mode="problemInfo.languageDicts" :code-templates="problemInfo.codeTemplatesParse" ref="editCode"></Codemirror>
      <div class="controller" v-show="controllerIcon === 'up'">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="执行结果">
            Content of Tab Pane 1
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
          <a-button type="primary">运行</a-button>
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

export default {
  name: 'ResolveProblem',
  components: {
    Codemirror
  },
  data () {
    return {
      id: undefined,
      problemInfo: {},
      paramTypes: {},
      teseCase: [],
      controllerIcon: 'up'
    }
  },
  created () {
    if (!this.$route.params.id || this.$route.params.id === '0') {
      this.$message.error('非法访问')
      this.$router.replace({ path: '/problemSet' })
    }
    this.id = this.$route.params.id
    this.getProblemInfo()
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
      const code = this.$refs.editCode.getCodeValue()
      const mode = this.$refs.editCode.mode
      console.log('code submit mode === ', mode, code)
    },
    async getProblemInfo () {
      const { data, code } = await getProblem(this.id)
      console.log(data)
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
</style>
