<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="学习任务id" prop="taskId" >
        <a-input v-model="form.taskId" placeholder="请输入学习任务id" />
      </a-form-model-item>
      <a-form-model-item label="题目id" prop="problemId" >
        <a-input v-model="form.problemId" placeholder="请输入题目id" />
      </a-form-model-item>
      <a-form-model-item label="排序" prop="soft" >
        <a-input v-model="form.soft" placeholder="请输入排序" />
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
import { getTaskProblem, addTaskProblem, updateTaskProblem } from '@/api/business/taskProblem'

export default {
  name: 'CreateForm',
  props: {
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

        taskId: null,

        problemId: null,

        soft: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        taskId: [
          { required: true, message: '学习任务id不能为空', trigger: 'blur' }
        ],

        problemId: [
          { required: true, message: '题目id不能为空', trigger: 'blur' }
        ],

        soft: [
          { required: true, message: '排序不能为空', trigger: 'blur' }
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
        taskId: null,

        taskId: null,

        taskId: null,

        soft: null,

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
      getTaskProblem(id).then(response => {
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
            updateTaskProblem(this.form).then(response => {
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
            addTaskProblem(this.form).then(response => {
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
