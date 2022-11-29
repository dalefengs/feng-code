<template>
  <a-card class="body">
    <a-row>
      <a-col :span="12">left</a-col>
      <a-col :span="12" style="position: relative">
        <Codemirror> </Codemirror>
        <div class="controller" v-show="controllerIcon === 'up'">
          <a-tabs default-active-key="1">
            <a-tab-pane key="1" tab="执行结果">
              Content of Tab Pane 1
            </a-tab-pane>
          </a-tabs>
        </div>
        <div class="submit-plane">
          <div>
            <a-button @click="controllerClick()">控制台
              <a-icon :type="controllerIcon" style="font-size: 13px" />
            </a-button>
          </div>
          <div class="right">
            <a-button type="primary">运行</a-button>
            <a-button style="background-color: #2db55d; color: white">提交</a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import { getProblem } from '@/api/business/problem'
import commonEmit from '@/utils/commonEmit'
import Codemirror from '@/components/Codemirror'

export default {
  name: 'ResolveProblem',
  components: {
    Codemirror
  },
  data () {
    return {
      id: undefined,
      problemInfo: undefined,
      controllerIcon: 'up'
    }
  },
  created () {
    console.log('id', this.$route.params)
    if (!this.$route.params.id || this.$route.params.id === '0') {
      this.$message.error('非法访问')
      this.$router.replace({ path: '/problemSet' })
    }
    this.id = this.$route.params.id
    this.getProblemInfo()
  },
  mounted () {
    // 隐藏底栏
    setTimeout(() => {
      commonEmit.$emit('onEmithIdeFooter', false)
    }, 500)
  },
  methods: {
    async getProblemInfo () {
      const info = await getProblem(this.id)
      console.log(info)
      if (info.code !== 200) {
        this.$message.error('数据不存，非法访问')
        await this.$router.replace({ path: '/problemSet' })
      }
    },
    controllerClick () {
      if (this.controllerIcon === 'up') {
        this.controllerIcon = 'down'
      } else {
        this.controllerIcon = 'up'
      }
    }
  }
}
</script>

<style lang="less">
/* 编辑器高度 */
.body{
  height: calc(100vh - 72px - 44px);
}

/* 编辑器高度 */
.CodeMirror {
  height: calc(100vh - 218px) !important;
}

.ant-layout-footer {
  padding: 0 !important;
  margin: 0 !important;
}
.ant-card-body {
  padding: 3px;
}
.controller {
  position: absolute;
  bottom: 0;
  height: 350px;
  width: 100.4%;
  background-color: white;
  box-shadow: 0 0 15px #f3f3f3;
  padding: 5px 10px;
}

.submit-plane {
  background-color: white;
  position: relative;
  width: 100.4%;
  //bottom: 10px;
  height: 50px;
  padding: 10px 10px 0 10px;
  box-shadow: 0 0 25px #f3f3f3;

  div {
    display: inline-block;

    button {
      border-radius: 8px;
      padding: 0 10px;
    }
  }

  .right {
    position: absolute;
    right: 10px;

    button {
      margin-left: 18px;
    }
  }
}
</style>
