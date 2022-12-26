<template>
  <a-card class="body">
    <a-button-group>
      <a-button :type="type === -1 ? 'primary' : ''">全部</a-button>
      <a-button :type="type === 0 ? 'primary' : ''">待完成</a-button>
      <a-button :type="type === 1 ? 'primary' : ''">已完成</a-button>
      <a-button :type="type === 2 ? 'primary' : ''">已过期</a-button>
    </a-button-group>
  </a-card>
</template>

<script>
export default {
  name: 'MyTask',
  data () {
    return {
      type: -1
    }
  }
}
</script>

<style scoped lang="less">
.body {
  padding: 0 10px;
  margin: 0 auto;
  width: 950px;
  min-width: 950px;
  height: 100%;
}

</style>
