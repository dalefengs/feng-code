<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="班级id" prop="classId" >
        <a-input v-model="form.classId" placeholder="请输入班级id" />
      </a-form-model-item>
      <a-form-model-item label="用户id" prop="userId" >
        <a-input v-model="form.userId" placeholder="请输入用户id" />
      </a-form-model-item>
      <a-form-model-item label="学习任务" prop="taskId" >
        <a-input v-model="form.taskId" placeholder="请输入学习任务" />
      </a-form-model-item>
      <a-form-model-item label="状态" prop="status" >
      </a-form-model-item>
      <a-form-model-item label="正确题目数量" prop="correctCount" >
        <a-input v-model="form.correctCount" placeholder="请输入正确题目数量" />
      </a-form-model-item>
      <a-form-model-item label="分数" prop="score" >
        <a-input v-model="form.score" placeholder="请输入分数" />
      </a-form-model-item>
      <a-form-model-item label="结束时间" prop="endTime" >
        <a-date-picker style="width: 100%" v-model="form.endTime" format="YYYY-MM-DD HH:mm:ss" allow-clear/>
      </a-form-model-item>
      <a-form-model-item label="答题时间" prop="submitTime" >
        <a-date-picker style="width: 100%" v-model="form.submitTime" format="YYYY-MM-DD HH:mm:ss" allow-clear/>
      </a-form-model-item>
      <a-form-model-item label="批阅时间" prop="checkTime" >
        <a-date-picker style="width: 100%" v-model="form.checkTime" format="YYYY-MM-DD HH:mm:ss" allow-clear/>
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
import { getTaskJoin, addTaskJoin, updateTaskJoin } from '@/api/business/taskJoin'

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

        classId: null,

        userId: null,

        taskId: null,

        status: 0,

        correctCount: null,

        score: null,

        endTime: null,

        submitTime: null,

        checkTime: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        classId: [
          { required: true, message: '班级id不能为空', trigger: 'blur' }
        ],

        userId: [
          { required: true, message: '用户id不能为空', trigger: 'blur' }
        ],

        taskId: [
          { required: true, message: '学习任务不能为空', trigger: 'blur' }
        ],

        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],

        correctCount: [
          { required: true, message: '正确题目数量不能为空', trigger: 'blur' }
        ],

        score: [
          { required: true, message: '分数不能为空', trigger: 'blur' }
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

        classId: null,

        userId: null,

        taskId: null,

        status: 0,

        correctCount: null,

        score: null,

        endTime: null,

        submitTime: null,

        checkTime: null,

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
      getTaskJoin(id).then(response => {
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
            updateTaskJoin(this.form).then(response => {
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
            addTaskJoin(this.form).then(response => {
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
