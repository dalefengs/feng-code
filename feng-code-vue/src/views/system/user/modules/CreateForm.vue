<template>
  <!-- 增加修改 -->
  <a-drawer width="35%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>{{ formTitle }}</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="角色" prop="roleIds">
        <a-select
          mode="multiple"
          v-model="form.roleIds"
          placeholder="请选择"
        >
          <a-select-option v-for="(d, index) in roleOptions" :key="index" :value="d.roleId">
            {{ d.roleName }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="用户昵称" prop="nickName">
        <a-input v-model="form.nickName" placeholder="请输入" :maxLength="30" />
      </a-form-model-item>
      <a-form-model-item label="手机号" prop="phonenumber">
        <a-input v-model="form.phonenumber" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="邮箱" prop="email">
        <a-input v-model="form.email" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="用户名" prop="userName">
        <a-input v-model="form.userName" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="学号" prop="studentNumber" v-if="isStudent">
        <a-input v-model="form.studentNumber" type="number" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="密码" prop="password" v-if="form.userId == undefined">
        <a-input-password v-model="form.password" placeholder="请输入" :maxLength="20" />
      </a-form-model-item>

      <a-form-model-item label="学院" prop="collegeId">
        <a-select
          v-model="form.collegeId"
          placeholder="请选择学院"
          @change="collegeSelectChange"
        >
          <a-select-option v-for="(d, index) in collegeOptions" :key="index" :value="d.id">
            {{ d.name }}
          </a-select-option>
        </a-select>
      </a-form-model-item>

      <a-form-model-item label="班级" prop="classId" v-if="isStudent">
        <a-select
          v-model="form.classId"
          placeholder="请选择班级"
        >
          <a-select-option v-for="(d, index) in classOptions" :key="index" :value="d.id">
            {{ d.name }}
          </a-select-option>
        </a-select>
      </a-form-model-item>

      <a-form-model-item label="性别" prop="sex">
        <a-radio-group v-model="form.sex" button-style="solid">
          <a-radio-button v-for="(d, index) in sexOptions" :key="index" :value="d.value">{{ d.label }}</a-radio-button>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="状态" prop="status">
        <a-radio-group v-model="form.status" button-style="solid">
          <a-radio-button v-for="(d, index) in statusOptions" :key="index" :value="d.value">{{ d.label }}</a-radio-button>
        </a-radio-group>
      </a-form-model-item>

      <a-form-model-item label="备注" prop="remark">
        <a-input v-model="form.remark" placeholder="请输入" type="textarea" allow-clear />
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

import { getUser, addUser, updateUser } from '@/api/system/user'
import { listCollegeAll } from '@/api/business/college'
import { listClassByCollegeId } from '@/api/business/class'
import { isDesignatedRole } from '@/utils/util'

export default {
  name: 'CreateForm',
  props: {
    statusOptions: {
      type: Array,
      required: true
    },
    sexOptions: {
      type: Array,
      required: true
    }
  },
  components: {
  },
  data () {
    return {
      submitLoading: false,
      replaceFields: { children: 'children', title: 'label', key: 'id', value: 'id' },
      collegeOptions: [],
      classOptions: [],
      // 角色选项
      roleOptions: [],
      isStudent: false,
      // 默认密码
      initPassword: undefined,
      formTitle: '',
      // 表单参数
      form: {
        userId: undefined,
        collegeId: undefined,
        classId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        studentNumber: undefined,
        email: undefined,
        sex: '3',
        status: '0',
        remark: undefined,
        roleIds: []
      },
      open: false,
      rules: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        studentNumber: [
          { required: true, message: '请输入学号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        collegeId: [
          { required: true, message: '请选择所属学院', trigger: 'change' }
        ],
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ],
        email: [
          { required: false, message: '邮箱不能为空', trigger: 'blur' },
          {
            type: 'email',
            message: '请正确填写邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phonenumber: [
          { required: false, message: '手机号不能为空', trigger: 'blur' },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请正确填写手机号',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  filters: {
  },
  created () {
    this.getConfigKey('sys.user.initPassword').then(response => {
      this.initPassword = response.msg
    })
  },
  computed: {
  },
  watch: {
    /* 监听选择角色，如果是学生显示班级选择框 */
    'form.roleIds': {
      handler (newVal, oldVal) {
        /* 学生才选择班级 101是学生 */
        this.isStudent = isDesignatedRole(this.form.roleIds, 101)
      },
      deep: true
    }
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
      this.form = {
        userId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        studentNumber: undefined,
        collegeId: undefined,
        classId: undefined,
        email: undefined,
        sex: '3',
        status: '0',
        remark: undefined,
        roleIds: []
      }
      this.classOptions = []
      this.collegeOptions = []
      this.isStudent = false
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset()
      this.$emit('select-tree')
      getUser().then(response => {
        this.roleOptions = response.roles
        this.open = true
        this.formTitle = '新增用户'
        this.form.password = this.initPassword
      })
      listCollegeAll().then(response => {
        this.collegeOptions = response.data
      })
    },
    /** 修改按钮操作 */
    async handleUpdate (row, ids) {
      this.reset()
      this.$emit('select-tree')
      const userId = row ? row.userId : ids
      const userInfo = await getUser(userId)
      this.form = userInfo.data
      this.roleOptions = userInfo.roles
      this.form.postIds = userInfo.postIds
      this.form.roleIds = userInfo.roleIds
      this.open = true
      this.formTitle = '修改用户'
      this.form.password = ''
      console.log('this.form.roles', this.form.roles)

      const collegeRes = await listCollegeAll()
      this.collegeOptions = collegeRes.data
      /* 学生才选择班级 */
      if (isDesignatedRole(this.form.roles, 'student')) {
        this.isStudent = true
        this.setClassOptions(this.form.collegeId)
      }
    },
    /* 学院下拉框事件 */
    collegeSelectChange (collegeId, e) {
      /* 学生才选择班级 */
      if (this.isStudent === true) {
        this.form.classId = undefined
        this.setClassOptions(collegeId)
      }
    },
    setClassOptions (collegeId) {
      listClassByCollegeId({ collegeId: collegeId })
        .then(response => {
          this.classOptions = response.data
        })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.userId !== undefined) {
            updateUser(this.form).then(response => {
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
            addUser(this.form).then(response => {
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
