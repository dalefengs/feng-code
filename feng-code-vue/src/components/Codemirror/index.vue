<template>
  <!-- Two-way Data-Binding -->
  <!-- <codemirror v-model="code" :options="cmOptions" /> -->
  <!-- Or manually control the data synchronization -->
  <div>
    <div class="plane">
      <a-row style="vertical-align: center">
        <a-col :span="12">
          <a-select :default-value="this.mode" style="width: 120px;" @change="languageChange">
            <a-select-option v-for="(item, key) in modes" :value="key" :key="key">
              {{ key }}
            </a-select-option>
          </a-select>
          <a-button style="margin-left: 15px" icon="reddit" @click="smartButtonClick">
            智能模式
          </a-button>
        </a-col>
        <a-col :span="12" style="text-align: right">
          <div>
            <a-tooltip placement="top" title="还原到默认的代码模版" @click="resetTemplte">
              <a-icon class="right-icon" type="undo" />
            </a-tooltip>
            <a-tooltip placement="top" title="使用帮助">
              <a-icon class="right-icon" type="question-circle" />
            </a-tooltip>
            <a-tooltip placement="top" title="全屏">
              <a-icon class="right-icon" type="fullscreen" @click="fullscreen"/>
            </a-tooltip>
          </div>
        </a-col>
      </a-row>
    </div>
    <codemirror
      id="codemirror_editor"
      ref="cmEditor"
      :value="code"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"
    />
    <a-divider style="margin: 0"/>
    <div id="exit-full" v-if="isFullscreen" @click="fullscreen">
      <a-button type="primary">
        退出全屏
      </a-button>
    </div>
  </div>
</template>
<script>
import { codemirror } from 'vue-codemirror'
// import base style
import 'codemirror/lib/codemirror.css'
// import theme style
import 'codemirror/theme/material-darker.css'
// import language js
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/sql/sql'
import 'codemirror/mode/python/python.js' // 导入python
import 'codemirror/mode/go/go'
import 'codemirror/mode/clike/clike'

// 支持括号自动匹配
import 'codemirror/addon/edit/matchbrackets'
import 'codemirror/addon/edit/closebrackets'
// 及时自动更新，配置里面也需要设置autoRefresh为true
import 'codemirror/addon/display/autorefresh'
// import 'codemirror/addon/comment/comment' // 行注释

// 代码折叠
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/addon/fold/foldcode'
import 'codemirror/addon/fold/foldgutter'
import 'codemirror/addon/fold/brace-fold'
import 'codemirror/addon/fold/comment-fold'

// 代码搜索
import 'codemirror/addon/dialog/dialog'
import 'codemirror/addon/dialog/dialog.css'
import 'codemirror/addon/search/search'
import 'codemirror/addon/search/searchcursor'
import 'codemirror/addon/search/matchesonscrollbar'
import 'codemirror/addon/search/matchesonscrollbar.css'
import 'codemirror/addon/scroll/annotatescrollbar'

// 代码全屏 F11
import 'codemirror/addon/display/fullscreen.css'
import 'codemirror/addon/display/fullscreen'

// 代码补全
import 'codemirror/addon/lint/lint'
import 'codemirror/addon/lint/lint.css'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/show-hint'
import 'codemirror/addon/hint/sql-hint'

import 'codemirror/addon/hint/anyword-hint'
import 'codemirror/addon/hint/javascript-hint'
import { getDicts } from '@/api/system/dict/data'

export default {
  name: 'CodemirrorComponent',
  components: { codemirror },
  props: {
    mode: {
      type: String,
      requires: true,
      default: 'Java'
    },
    supportMode: { // 支持的语言
      type: Array,
      default: () => ['Java', 'Python', 'Golang', 'Sql', 'Shell', 'C/C++']
    },
    codeTemplates: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    supportMode: {
      handler (newVal, oldVal) {
        this.changeModes()
      }
    }
  },
  data () {
    return {
      language: 'Java',
      languageDicts: {},
      isFullscreen: false,
      code: `


      `,
      cmOptions: {
        tabSize: 4,
        mode: 'text/x-java',
        // theme: 'material-darker',
        lineNumbers: true,
        smartIndent: true, // 是否智能缩进
        foldGutter: true,
        line: true,
        lint: true,
        gutters: [
          'CodeMirror-linenumbers',
          'CodeMirror-foldgutter',
          'CodeMirror-lint-markers' // CodeMirror-lint-markers是实现语法报错功能
        ],
        hintOptions: { // 自定义提示选项
          completeSingle: false// 当匹配只有一项的时候是否自动补全
        },
        extraKeys: { // 触发按键
          'Ctrl': 'autocomplete',
          'F11': function (cm) { // 代码全屏
            this.isFullscreen = !this.isFullscreen
            cm.setOption('fullScreen', !cm.getOption('fullScreen'))
          },
          'Esc': function (cm) {
            this.isFullscreen = !this.isFullscreen
            if (cm.getOption('fullScreen')) {
              console.log('esc 关闭全屏')
              cm.setOption('fullScreen', false)
            }
          }
        },
        matchBrackets: true, // 括号匹配
        autoCloseBrackets: true, // 括号自动关闭
        autorefresh: true
        // more CodeMirror options...
      },
      modes: {},
      modesList: {
        'Java': {
          'mode': 'text/x-java',
          'templete': `
class Solution {
    public String your-method-name(String param1, int param2) {

    }
}
          `
        },
        'Python': {
          'mode': 'python',
          'templete': `
class Solution:
    def your-method-name(self):

          `
        },
        'Golang': {
          'mode': 'text/x-go',
          'templete': `
func your-method-name(s string) int {

}

          `
        },
        'C/C++': {
          'mode': 'text/x-csrc',
          'templete': `
int your-method-name(char * s){

}

          `
        },
        'JavaScript': {
          'mode': 'text/javascript',
          'templete': `
var your-method-name = function(s) {

};

          `
        },
        'Sql': {
          'mode': 'text/x-sql',
          'templete': ''
        },
        'Shell': {
          'mode': 'text/x-sh',
          'templete': ''
        },
        'TestCase': {
          'mode': '',
          'templete': `

          `
        }
      }
    }
  },
  computed: {
    codemirror () {
      return this.$refs.cmEditor.codemirror
    }
  },
  created () {
    this.changeModes()
  },
  mounted () {
    // console.log('the current CodeMirror instance object:', this.codemirror)
    this.codemirror.on('change', (cm, changeObj) => {
      // 自动补全的时候，也会触发change事件，所有坐下判断，以免死循环，正则是为了不让空格，换行之类的也提示
      // 通过change对象你可以自定义一些规则去判断是否提示
      if (changeObj.origin === '+input' && /\w|\./g.test(changeObj.text[0])) {
        this.codemirror.showHint()
      }
    })
    // you can use this.codemirror to do something...
  },
  methods: {
    onCmReady (cm) {
      // console.log('the editor is readied!', cm)
    },
    onCmFocus (cm) {
      // console.log('the editor is focused!', cm)
    },
    onCmCodeChange (newCode) {
      this.code = newCode
    },
    // 返回内容
    getCodeValue () {
      return this.codemirror.doc.getValue()
    },
    // 切换语言
    languageChange (value) {
      this.language = value
      this.cmOptions.mode = this.modes[value].mode
      this.code = this.modes[value].templete
    },
    // 全屏
    fullscreen () {
      this.isFullscreen = !this.isFullscreen
      this.codemirror.setOption('fullScreen', !this.codemirror.getOption('fullScreen'))
    },
    smartButtonClick () {
      this.$message.warn('智能模式已开启，暂时不能关闭！')
    },
    resetTemplte () {
      this.cmOptions.mode = this.modes[this.language].mode
      this.code = this.modes[this.language].templete
      this.$message.success('代码已恢复到原始状态！')
    },
    async changeModes () {
      // 获取语言类型字典
      if (Object.keys(this.languageDicts).length === 0) {
        const { data } = await getDicts('code_language')
        this.languageDicts = data
      }
      // 设置支持语言
      if (this.supportMode.length === 0) {
        this.modes = this.modesList
      } else {
        this.modes = {}
        for (const key in this.modesList) {
          if (this.supportMode.includes(key)) {
            this.modes[key] = this.modesList[key]
          }
        }
      }
      // 设置代码模版
      if (this.codeTemplates.length > 0) {
        this.codeTemplates.forEach((item, index) => {
          this.languageDicts.forEach(d => {
            if (parseInt(d.dictValue) === index) {
              this.modes[d.dictLabel]['templete'] = item
            }
          })
        })
      }
      if (this.modes[this.mode]) {
        const mode = this.modes[this.mode]
        this.code = mode.templete
        this.cmOptions.mode = mode.mode
      }
    }
  }
}
</script>
<style>
#codemirror_editor {
  line-height: 1.5;
}

.plane{
  background-color: #f7f7f7;
  padding: 0 10px;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  height: 45px;
  line-height: 45px;
}

/* 全屏 */
.CodeMirror-fullscreen{
  z-index: 500;
}

.right-icon{
  margin-right: 15px;
  font-size: 18px;
}

#exit-full {
  z-index: 501;
  position: fixed;
  right: 20px;
  top: 20px;
}

</style>
