<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="用户名" prop="userId" >
        <a-input v-model="form.userId" placeholder="请输入用户名" />
      </a-form-model-item>
      <a-form-model-item label="题目" prop="problemId" >
        <a-input v-model="form.problemId" placeholder="请输入题目" />
      </a-form-model-item>
      <a-form-model-item label="学习任务" prop="taskId" >
        <a-input v-model="form.taskId" placeholder="请输入学习任务" />
      </a-form-model-item>
      <a-form-model-item label="学生参与学习任务" prop="taskJoinId" >
        <a-input v-model="form.taskJoinId" placeholder="请输入学生参与学习任务" />
      </a-form-model-item>
      <a-form-model-item label="语言类型" prop="type" >
        <a-select placeholder="请选择语言类型" v-model="form.type">
          <a-select-option v-for="(d, index) in typeOptions" :key="index" :value="d.value" >{{ d.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="代码" prop="code" >
        <a-input v-model="form.code" placeholder="请输入内容" type="textarea" allow-clear />
      </a-form-model-item>
      <a-form-model-item label="状态" prop="status" >
        <a-select placeholder="请选择状态" v-model="form.status">
          <a-select-option v-for="(d, index) in statusOptions" :key="index" :value="d.value" >{{ d.label }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="成功信息" prop="successMsg" >
        <a-input v-model="form.successMsg" placeholder="请输入内容" type="textarea" allow-clear />
      </a-form-model-item>
      <a-form-model-item label="错误信息" prop="errorMsg" >
        <a-input v-model="form.errorMsg" placeholder="请输入内容" type="textarea" allow-clear />
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
import { getQueue, addQueue, updateQueue } from '@/api/business/queue'

export default {
  name: 'CreateForm',
  props: {
    typeOptions: {
      type: Array,
      required: true
    },
    statusOptions: {
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

        userId: null,

        problemId: null,

        taskId: null,

        taskJoinId: null,

        type: null,

        code: null,

        status: null,

        successMsg: null,

        errorMsg: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        userId: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],

        problemId: [
          { required: true, message: '题目不能为空', trigger: 'blur' }
        ],

        taskId: [
          { required: true, message: '学习任务不能为空', trigger: 'blur' }
        ],

        taskJoinId: [
          { required: true, message: '学生参与学习任务不能为空', trigger: 'blur' }
        ],

        type: [
          { required: true, message: '语言类型不能为空', trigger: 'change' }
        ],

        code: [
          { required: true, message: '代码不能为空', trigger: 'blur' }
        ],

        status: [
          { required: true, message: '状态不能为空', trigger: 'change' }
        ],

        successMsg: [
          { required: true, message: '成功信息不能为空', trigger: 'blur' }
        ],

        errorMsg: [
          { required: true, message: '错误信息不能为空', trigger: 'blur' }
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

        userId: null,

        problemId: null,

        taskId: null,

        taskJoinId: null,

        type: null,

        code: null,

        status: null,

        successMsg: null,

        errorMsg: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      }
    },
    /** 新增按钮操作 */
    handleAdd (row) {
      this.reset()
      this.formType = 1
      this.open = true
      this.formTitle = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate (row, ids) {
      this.reset()
      this.formType = 2
      const id = row ? row.id : ids
      getQueue(id).then(response => {
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
            updateQueue(this.form).then(response => {
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
            addQueue(this.form).then(response => {
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
