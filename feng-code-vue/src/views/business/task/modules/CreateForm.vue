<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="学习任务名称" prop="title" >
        <a-input v-model="form.title" placeholder="请输入学习任务名称" />
      </a-form-model-item>
      <a-form-model-item label="子标题" prop="subTitle" >
        <a-input v-model="form.subTitle" placeholder="请输入子标题" />
      </a-form-model-item>
      <a-form-model-item label="任务图片" prop="imgUrl" >
        <file-upload v-model="form.imgUrl" type="image"></file-upload>
      </a-form-model-item>
      <a-form-model-item label="简介说明" prop="hint">
        <a-textarea
          v-model="form.taskExplain"
          placeholder="请输入任务简介说明"
          :auto-size="{minRows: 4, maxRows: 4}"
          :style="{width: '100%'}"
          allow-clear />
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
import { getTask, addTask, updateTask } from '@/api/business/task'

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

        teacherId: null,

        title: null,

        subTitle: null,

        imgUrl: null,

        taskExplain: null,

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
          { required: true, message: '学习任务名称不能为空', trigger: 'blur' }
        ],

        subTitle: [
          { required: true, message: '子标题不能为空', trigger: 'blur' }
        ],

        imgUrl: [
          { required: true, message: '任务图片不能为空', trigger: 'blur' }
        ],

        taskExplain: [
          { required: true, message: '任务简介说明不能为空', trigger: 'blur' }
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
        problemId: null,

        teacherId: null,

        title: null,

        subTitle: null,

        imgUrl: null,

        taskExplain: null,

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
      getTask(id).then(response => {
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
            updateTask(this.form).then(response => {
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
            addTask(this.form).then(response => {
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
