<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="题目标题" prop="title" >
        <a-input v-model="form.title" placeholder="请输入题目标题" />
      </a-form-model-item>
      <a-form-model-item label="难度等级" prop="level" >
        <a-select placeholder="请选择难度等级" v-model="form.level">
          <a-select-option v-for="(d, index) in levelOptions" :key="index" :value="d.value" >{{ d.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="题目说明" prop="description" >
        <a-input v-model="form.description" placeholder="请输入内容" type="textarea" allow-clear />
      </a-form-model-item>
      <a-form-model-item label="提示" prop="hint" >
        <a-input v-model="form.hint" placeholder="请输入提示" />
      </a-form-model-item>
      <a-form-model-item label="分类id" prop="categoryId" >
      </a-form-model-item>
      <a-form-model-item label="标签id" prop="tagId" >
        <a-input v-model="form.tagId" placeholder="请输入标签id" />
      </a-form-model-item>
      <a-form-model-item label="排序" prop="sort" >
        <a-input v-model="form.sort" placeholder="请输入排序" type="number" />
      </a-form-model-item>
      <a-form-model-item label="测试用例文件地址" prop="testcastUrl" >
        <a-input v-model="form.testcastUrl" placeholder="请输入测试用例文件地址" />
      </a-form-model-item>
      <a-form-model-item label="方法名" prop="methodName" >
        <a-input v-model="form.methodName" placeholder="请输入方法名" />
      </a-form-model-item>
      <a-form-model-item label="参数名，多个参数使用;号分隔(类型:名称;类型:名称) 默认使用Java类型" prop="paramNames" >
        <a-input v-model="form.paramNames" placeholder="请输入参数名，多个参数使用;号分隔(类型:名称;类型:名称) 默认使用Java类型" />
      </a-form-model-item>
      <a-form-model-item label="0自动批阅(执行测试用例) 1教师批阅" prop="isAuto" >
        <a-select placeholder="请选择0自动批阅(执行测试用例) 1教师批阅" v-model="form.isAuto">
          <a-select-option v-for="(d, index) in isAutoOptions" :key="index" :value="d.value" >{{ d.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <div class="bottom-control">
        <a-space>
          <a-button type="primary" :loading="submitLoading" @click="submitForm">
            保存
          </a-button>
          <a-button type="dashed" @click="cancel">
            取消
          </a-button>
        </a-space>
      </div>
    </a-form-model>
  </a-drawer>
</template>

<script>
import { getProblem, addProblem, updateProblem } from '@/api/business/problem'

export default {
  name: 'CreateForm',
  props: {
    levelOptions: {
      type: Array,
      required: true
    },
    isAutoOptions: {
      type: Array,
      required: true
    }
  },
  components: {
  },
  data () {
    return {
      submitLoading: false,
      formTitle: '',
      // 表单参数
      form: {
        id: null,

        title: null,

        userId: null,

        level: null,

        description: null,

        categoryId: null,

        tagId: null,

        hint: null,

        sort: 50,

        testcastUrl: null,

        methodName: null,

        paramNames: null,

        isAuto: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        title: [
          { required: true, message: '题目标题不能为空', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '难度等级不能为空', trigger: 'change' }
        ],

        categoryId: [
          { required: true, message: '分类id不能为空', trigger: 'change' }
        ]

      }
    }
  },
  filters: {
  },
  created () {
  },
  computed: {
  },
  watch: {
  },
  mounted () {
  },
  methods: {
    onClose () {
      this.open = false
    },
    // 取消按钮
    cancel () {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset () {
      this.formType = 1
      this.form = {
        id: null,

        title: null,

        userId: null,

        level: null,

        description: null,

        categoryId: null,

        tagId: null,

        hint: null,

        sort: 50,

        testcastUrl: null,

        methodName: null,

        paramNames: null,

        isAuto: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      }
    },
    /** 新增按钮操作 */
    handleAdd (row) {
      // this.reset()
      // this.formType = 1
      // this.open = true
      // this.formTitle = '添加'
      this.$router.push({ path: '/study/business/problem/createProblem', query: { } })
    },
    /** 修改按钮操作 */
    handleUpdate (row, ids) {
      this.reset()
      this.formType = 2
      const id = row ? row.id : ids
      getProblem(id).then(response => {
        this.form = response.data
        this.open = true
        this.formTitle = '修改'
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id !== undefined && this.form.id !== null) {
            updateProblem(this.form).then(response => {
              this.$message.success(
                '修改成功',
                3
              )
              this.open = false
              this.$emit('ok')
            }).finally(() => {
              this.submitLoading = false
            })
          } else {
            addProblem(this.form).then(response => {
              this.$message.success(
                '新增成功',
                3
              )
              this.open = false
              this.$emit('ok')
            }).finally(() => {
              this.submitLoading = false
            })
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>
