<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <span
          v-if="item.listClass === 'default' || item.listClass == ''"
          :key="item.value"
          :index="index"
          :class="item.raw.cssClass"
        >
          {{ item.label }}
        </span>
        <a-badge
          v-else
          :key="item.value"
          :index="index"
          :status="item.listClass === 'danger' ? 'error' : (item.listClass === 'success' ? 'success' : (item.listClass === 'warning' ? 'warning' : 'processing'))"
          :class="item.raw.cssClass"
          :text="item.label"
        >
        </a-badge>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: 'DictTag',
  props: {
    options: {
      type: Array,
      default: null
    },
    value: {
      type: [Number, String, Array],
      default: null
    }
  },
  computed: {
    values () {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)]
      } else {
        return []
      }
    }
  }
}
</script>
