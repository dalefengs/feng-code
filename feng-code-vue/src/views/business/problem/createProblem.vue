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
          <a-textarea
            v-model="formData.templete"
            placeholder="请输入代码模版"
            :auto-size="{minRows: 4, maxRows: 4}"
            :style="{width: '100%'}"
            allow-clear />
        </a-form-model-item>
        <a-form-model-item :label="languageList[item] + ' 函数名'" prop="" :key="key + 100">
          <a-input v-model="formData.methodNames[item]" placeholder="请输入代码模版中要执行的函数名" :style="{width: '100%'}" allow-clear>
          </a-input>
        </a-form-model-item>
        <!--    Python不显示参数类型选择框和按钮    -->
        <template v-for="(count, index) in languageParamTypeCount[item]" v-if="item !== '1'">
          <a-form-model-item :label="languageList[item] + '第'+ count +'个参数类型'" prop="" :key="index + item">
            <a-select v-model="formData.languageParamType[item][count]" placeholder="请选择参数类型" allow-clear :style="{width: '100%'}">
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
        <a-textarea
          v-model="formData.testCase"
          placeholder="请输入测试用例"
          :auto-size="{minRows: 4, maxRows: 4}"
          :style="{width: '100%'}"
          allow-clear />
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
export default {
  name: 'CreateProblem',
  components: {},
  props: [],
  data () {
    return {
      formData: {
        title: undefined,
        description: undefined,
        hint: undefined,
        categoryId: 1,
        tagId: undefined,
        level: '0',
        sort: 50,
        isAuto: '0',
        language: ['0', '2'],
        templete: undefined,
        testCase: undefined,
        methodNames: [],
        languageParamType: {
          0: [],
          1: [],
          2: [],
          3: [],
          4: [],
          5: [],
          6: []
        }
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
        templete: [{
          required: true,
          message: '请输入多行文本',
          trigger: 'blur'
        }],
        methods: [{
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
      isAutoOptions: [],
      languageOptions: [],
      languageTypeOptions: [],
      languageList: {},
      languageWrite: ['4', '6'], // 4sql 5shell 不生成函数名
      languageParamTypeCount: {}
    }
  },
  computed: {},
  watch: {
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
      this.$refs['elForm'].validate(valid => {
        console.log('valid', valid)
        console.log('form', this.formData)
        console.log(this.languageList)
        if (!valid) {
          return false
        }
      })
    },
    resetForm () {
      this.$refs['elForm'].resetFields()
    },
    languageParamTypeCountChange (languageType, type) {
      const t = this.languageParamTypeCount
      if (type === 1) {
        t[languageType]++
      } else if (type === 2) {
        t[languageType]--
      }
      this.languageParamTypeCount = Object.assign({}, this.languageParamTypeCount, t)
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
          this.languageParamTypeCount[item.dictValue] = 2
          this.languageOptions.push(data)
        })
      })
    },
    // 获取语言类型字典
    async getLanguageTypeDicts () {
      const languageDictType = {
        '0': 'java_type',
        '2': 'go_type',
        '4': 'c_type'
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

</style>
