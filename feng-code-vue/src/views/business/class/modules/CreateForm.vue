<template>
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="班级名称" prop="name" >
        <a-input v-model="form.name" placeholder="请输入班级名称" />
      </a-form-model-item>

      <a-form-model-item label="所属学院" prop="collegeId">
        <a-select
          v-model="form.collegeId"
          placeholder="请选择学院"
        >
          <a-select-option v-for="(c, index) in collegeList" :key="index" :value="c.id">
            {{ c.name }}
          </a-select-option>
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
import { getClass, addClass, updateClass } from '@/api/business/class'
import { listCollegeAll } from '@/api/business/college'

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
      collegeList: [], // 学院下拉选项
      // 表单参数
      form: {
        id: null,

        name: null,

        collegeId: null,

        createBy: null,

        createTime: null,

        updateBy: null,

        updateTime: null

      },
      // 1增加,2修改
      formType: 1,
      open: false,
      rules: {
        name: [
          { required: true, message: '班级名称不能为空', trigger: 'blur' }
        ],
        collegeId: [
          { required: true, message: '请选择所属学院', trigger: 'change' }
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

        name: null,

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
      listCollegeAll().then(reponse => {
        this.collegeList = reponse.data
      })
    },
    /** 修改按钮操作 */
    handleUpdate (row, ids) {
      this.reset()
      this.formType = 2
      const id = row ? row.id : ids
      getClass(id).then(response => {
        this.form = response.data
        this.open = true
        this.formTitle = '修改'
      })
      listCollegeAll().then(reponse => {
        this.collegeList = reponse.data
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id !== undefined && this.form.id !== null) {
            updateClass(this.form).then(response => {
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
            addClass(this.form).then(response => {
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
