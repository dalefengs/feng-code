<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="标题" prop="title" >
        <a-input v-model="form.title" placeholder="请输入标题" />
      </a-form-model-item>
      <a-form-model-item label="作者id" prop="userId" >
        <a-input v-model="form.userId" placeholder="请输入作者id" />
      </a-form-model-item>
      <a-form-model-item label="问题id" prop="problemId" >
        <a-input v-model="form.problemId" placeholder="请输入问题id" />
      </a-form-model-item>
      <a-form-model-item label="内容" prop="content" >
        <editor ref="contentEditor" v-model="form.content" />
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
import { getSolve, addSolve, updateSolve } from '@/api/business/solve'
import Editor from '@/components/Editor'

export default {
  name: 'CreateForm',
  props: {
  },
  components: {
    Editor
  },
  data () {
    return {
      submitLoading: false,
      formTitle: '',
      contentEditor: '',
      // 表单参数
      form: {
        title: null,

        userId: null,

        problemId: null,

        content: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        userId: [
          { required: true, message: '作者id不能为空', trigger: 'blur' }
        ],

        problemId: [
          { required: true, message: '问题id不能为空', trigger: 'blur' }
        ],

        content: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
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
        title: null,

        userId: null,

        problemId: null,

        content: null

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
      getSolve(id).then(response => {
        this.form = response.data
        this.open = true
        this.formTitle = '修改'
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.form.content = this.contentEditor.getValue()
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id !== undefined && this.form.id !== null) {
            updateSolve(this.form).then(response => {
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
            addSolve(this.form).then(response => {
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
