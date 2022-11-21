<template>
  <div id="fromBody">
    <a-form-model
      ref="elForm"
      :model="formData"
      :rules="rules"
      :label-col="{ span: 3, offset: 0 }"
      :wrapper-col="{ span: 21, offset: 0 }"
      layout="horizontal">
      <a-form-model-item label="题目标题" prop="title">
        <a-input v-model="formData.title" placeholder="请输入标题" :style="{width: '100%'}" allow-clear></a-input>
      </a-form-model-item>
      <a-form-model-item label="题目说明" prop="description">
        <a-textarea
          v-model="formData.description"
          placeholder="请输入题目说明"
          :auto-size="{minRows: 4, maxRows: 4}"
          :style="{width: '100%'}"
          allow-clear />
      </a-form-model-item>
      <a-form-model-item label="题目提示" prop="hint">
        <a-textarea
          v-model="formData.hint"
          placeholder="请输入题目提示"
          :auto-size="{minRows: 4, maxRows: 4}"
          :style="{width: '100%'}"
          allow-clear />
      </a-form-model-item>
      <a-form-model-item label="难度等级" prop="level">
        <a-radio-group v-model="formData.level">
          <a-radio-button
            v-for="(item, index) in levelOptions"
            :key="index"
            :value="item.value"
            :disabled="item.disabled">{{ item.label }}</a-radio-button>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="排序" prop="sort">
        <a-input v-model="formData.sort" placeholder="请输入排序" allow-clear></a-input>
      </a-form-model-item>
      <a-form-model-item label="批阅方式" prop="isAuto">
        <a-radio-group v-model="formData.isAuto">
          <a-radio-button
            v-for="(item, index) in isAutoOptions"
            :key="index"
            :value="item.value"
            :disabled="item.disabled">{{ item.label }}
          </a-radio-button>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="标签" prop="tagId">
        <a-select v-model="formData.tagId" placeholder="请选择标签" allow-clear :style="{width: '100%'}">
          <a-select-option
            v-for="(item, index) in tagIdOptions"
            :key="index"
            :value="item.value"
            :disabled="item.disabled">{{ item.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="分类" prop="categoryId">
        <a-select v-model="formData.categoryId" placeholder="请选择分类" allow-clear :style="{width: '100%'}">
          <a-select-option
            v-for="(item, index) in categoryIdOptions"
            :key="index"
            :value="item.value"
            :disabled="item.disabled">{{ item.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="支持语言" prop="language">
        <a-checkbox-group v-model="formData.language">
          <a-checkbox
            v-for="(item, index) in languageOptions"
            :key="index"
            :value="item.value"
            :disabled="item.disabled">{{ item.label }}</a-checkbox>
        </a-checkbox-group>
      </a-form-model-item>
      <template v-for="(item, key) in formData.language" v-if="!languageWrite.includes(item)">
        <a-form-model-item :label="languageList[item] + ' 代码模板'" prop="templete" :key="item" >
          <!--     代码编辑器      -->
          <CodemirrorDemo :mode="languageList[item]" :ref="'edit_' + item"></CodemirrorDemo>
        </a-form-model-item>
        <a-form-model-item :label="languageList[item] + ' 函数名'" prop="" :key="key + 100">
          <a-input v-model="formData.methodNames[item]" placeholder="请输入代码模版中要执行的函数名" :style="{width: '100%'}" allow-clear>
          </a-input>
        </a-form-model-item>
        <!--    Python不显示参数类型选择框和按钮    -->
        <template v-for="(count, index) in languageParamTypeCount[item]" v-if="!['1', '5'].includes(item)">
          <a-form-model-item :label="languageList[item] + '第'+ count +'个参数类型'" prop="" :key="index + item">
            <a-select v-model="formData.languageParamType[item][count - 1]" placeholder="请选择参数类型" allow-clear :style="{width: '100%'}">
              <a-select-option
                v-for="(v, i) in languageTypeOptions[item]"
                :key="i"
                :value="v.value"
                :disabled="v.disabled">{{ v.label }}</a-select-option>
            </a-select>
          </a-form-model-item>
        </template>
        <a-form-model-item label="参数操作" :key="key + 300" v-if="item !== '1'">
          <a-button type="primary" icon="plus" @click="languageParamTypeCountChange(item, 1)">
            添加
          </a-button>
          <a-button type="primary" icon="plus" @click="languageParamTypeCountChange(item, 2)" style="margin-left: 20px">
            删除
          </a-button>
        </a-form-model-item>
      </template>
      <a-form-model-item label="测试用例" prop="testCase">
        <!--     代码编辑器      -->
        <CodemirrorDemo mode="TestCase" :ref="'testCase'"></CodemirrorDemo>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 21, offset: 3 }">
        <a-space>
          <a-button type="primary" @click="submitForm">提交</a-button>
          <a-button @click="resetForm">重置</a-button>
          <a-button @click="$router.go(-1)">返回</a-button>
        </a-space>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>
<script>

import { listAllProblemCategory } from '@/api/business/problemCategory'
import { listAllTags } from '@/api/business/tags'
import { getDicts } from '@/api/system/dict/data'
import CodemirrorDemo from '@/components/Codemirror'
export default {
  name: 'CreateProblem',
  components: {
    CodemirrorDemo
  },
  props: [],
  data () {
    return {
      formData: {
        title: '',
        description: '',
        hint: '', // 提示
        categoryId: undefined,
        tagId: undefined,
        level: '0',
        sort: 50,
        isAuto: '0',
        language: [], // 支持的语言
        testCase: undefined,
        languageTemplate: [], // 语言模版
        methodNames: [], // 语言模版方法名称
        languageParamType: [[], [], [], [], [], []] // 语言模版参数类型列表
      },
      rules: {
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        description: [{
          required: true,
          message: '请输入题目说明',
          trigger: 'blur'
        }],
        hint: [{
          required: true,
          message: '请输入题目提示',
          trigger: 'blur'
        }],
        categoryId: [{
          required: true,
          message: '请选择分类',
          trigger: 'change'
        }],
        tagId: [{
          required: true,
          message: '请选择标签',
          trigger: 'change'
        }],
        level: [{
          required: true,
          message: '难度等级不能为空',
          trigger: 'change'
        }],
        sort: [{
          required: true,
          message: '请输入排序',
          trigger: 'blur'
        }],
        isAuto: [{
          required: true,
          message: '批阅方式不能为空',
          trigger: 'change'
        }],
        language: [{
          required: true,
          type: 'array',
          message: '请至少选择一个支持语言',
          trigger: 'change'
        }],
        methodNames: [{
          required: true,
          message: '请输入代码模版中要执行的函数名',
          trigger: 'blur'
        }],
        testCase: [{
          required: true,
          message: '请输入测试用例',
          trigger: 'blur'
        }]
      },
      categoryIdOptions: [],
      tagIdOptions: [],
      levelOptions: [],
      isAutoOptions: [], // 是否自动批阅
      languageOptions: [], // 语言选项
      languageTypeOptions: [], // 每个语言的参数类型
      languageList: {},
      languageWrite: ['4', '6'], // 4sql 5shell 不生成函数名
      languageParamTypeCount: {} // 每个语言类型的参数个数
    }
  },
  computed: {},
  watch: {
    'formData.languageParamType': {
      handler (newVal, oldVal) {
        console.log('languageParamType', newVal)
      },
      deep: true
    },
    'formData.language': {
      handler (newVal, oldVal) {
        // 求差集 得到取消选择的语言
        const diffLanguage = newVal.concat(oldVal).filter(v => !newVal.includes(v))
        console.log('diffLanguage', diffLanguage)
        if (diffLanguage.length !== 0) {
          diffLanguage.forEach(lkey => {
            console.log('delete data key', lkey)
            // 删除对应的数据
            // this.formData.languageParamType[lkey] = []
            const t = this.formData.languageParamType[lkey] = []
            this.formData.languageParamType = Object.assign({}, this.formData.languageParamType, t)
            console.log('Object.assign({}, this.formData.languageParamType[lkey], []', Object.assign({}, this.formData.languageParamType, t))
            console.log('this.formData.languageParamType[lkey]', lkey, this.formData.languageParamType[lkey])
            this.formData.languageTemplate.splice(lkey, 1)
            this.formData.methodNames.splice(lkey, 1)
            this.languageParamTypeCount[lkey] = 0
          })
        }
      },
      deep: true
    },
    'formData.categoryId': {
      handler (newVal, oldVal) {
        console.log(newVal)
        this.languageOptions.forEach((item, index) => {
          this.formData.language = []
          // 分类选数据库时启用sql
          if (newVal === 2) {
            item.disabled = item.value !== '4'
            this.formData.language.push('4')
          } else if (newVal === 3) { // 分类选数据库Shell时启用shell
            item.disabled = item.value !== '6'
            this.formData.language.push('6')
          } else {
            item.disabled = item.value === '6' || item.value === '4'
          }
        })
      },
      deep: true
    }
  },
  created () {
    this.getProblemCategoryList()
    this.getProblemTagsList()
    this.getDictsList()
    this.getLanguageTypeDicts()
  },
  mounted () {},
  methods: {
    submitForm () {
      this.formData.language.forEach(item => {
        // sql 和 shell
        if (['4', '6'].includes(item)) {
          return false
        }
        const ref = 'edit_' + item
        const code = this.$refs[ref][0].getCodeValue()
        this.formData.languageTemplate[item] = code
      })
      // 获取测试用例
      this.formData.testCase = this.$refs.testCase.getCodeValue()
      // 表单验证并提交
      this.$refs['elForm'].validate(valid => {
        console.log('form', this.formData)
        console.log('form-json', JSON.stringify(this.formData))
        console.log('valid', valid)
        if (!valid) {
          return false
        }
      })
    },
    resetForm () {
      this.$refs['elForm'].resetFields()
    },
    /**
     * 添加删除参数
     * @param languageType
     * @param type 1增 2减
     */
    languageParamTypeCountChange (languageType, type) {
      const count = this.languageParamTypeCount
      if (type === 1) {
        count[languageType]++
      } else if (type === 2) {
        count[languageType]--
        // 删除数组最后一个元素
        this.formData.languageParamType[languageType].pop()
      }
      this.languageParamTypeCount = Object.assign({}, this.languageParamTypeCount, count)
    },
    /**
     * 获取分类列表
     */
    getProblemCategoryList () {
      listAllProblemCategory().then(res => {
        if (res.code !== 200) {
          this.$message.error('获取题目分类列表失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.title,
            'value': item.id
          }
          this.categoryIdOptions.push(data)
        })
      })
    },
    getProblemTagsList () {
      listAllTags().then(res => {
        if (res.code !== 200) {
          this.$message.error('获取题目标签列表失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.title,
            'value': item.id
          }
          this.tagIdOptions.push(data)
        })
      })
    },
    getDictsList () {
      // 获取难度
      getDicts('difficulty_level').then(res => {
        if (res.code !== 200) {
          this.$message.error('获取题难度等级失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.dictLabel,
            'value': item.dictValue
          }
          this.levelOptions.push(data)
        })
      })
      // 获取自动批阅字典
      getDicts('is_auto').then(res => {
        if (res.code !== 200) {
          this.$message.error('获取自动批阅字典失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.dictLabel,
            'value': item.dictValue
          }
          this.isAutoOptions.push(data)
        })
      })
      // 获取语言类型字典
      getDicts('code_language').then(res => {
        if (res.code !== 200) {
          this.$message.error('获取语言类型失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.dictLabel,
            'value': item.dictValue,
            'disabled': true
          }
          this.languageList[item.dictValue] = item.dictLabel
          this.languageParamTypeCount[item.dictValue] = 0
          this.languageOptions.push(data)
        })
      })
    },
    // 获取语言类型字典
    async getLanguageTypeDicts () {
      const languageDictType = {
        '0': 'java_type',
        '2': 'go_type',
        '3': 'c_type'
      }
      const lanuageTypeObject = {}
      for (const key in languageDictType) {
        const languageList = []
        // 获取语言类型字典
        const res = await getDicts(languageDictType[key])
        if (res.code !== 200) {
          this.$message.error('获取语言类型失败')
          return false
        }
        res.data.forEach(item => {
          const data = {
            'label': item.dictLabel,
            'value': item.dictValue
          }
          languageList.push(data)
        })
        lanuageTypeObject[key] = languageList
        this.languageTypeOptions = lanuageTypeObject
      }
    }
  }
}

</script>
<style>

#fromBody{
  background-color: white;
  padding: 40px 80px 40px 0;
}
/* 编辑器高度 */
.CodeMirror {
  height: auto !important;
}
</style>
