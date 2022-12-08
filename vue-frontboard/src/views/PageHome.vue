<!-- PageHome.vue -->
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/mini_backjun_introduce_img.jpg">
    <HelloWorld msg="Welcome to Mini Baekjoon Site"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'PageHome',
  components: {
    HelloWorld
  }
}
</script>