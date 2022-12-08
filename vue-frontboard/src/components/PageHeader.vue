<template>
  <header>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/problem/list">문제</router-link> |
      <router-link to="/board/list">게시판</router-link> |
      <router-link to="/common/login" v-if="!this.$store.state.isLogin">로그인</router-link>
      <a v-if="this.$store.state.isLogin" @click="fnLogout">로그아웃</a>
    </div>
  </header>
  <hr/>
</template>

<script>
export default {
 methods: {
    fnLogout() {
      localStorage.removeItem("user_token")
      localStorage.removeItem("user_role")
      localStorage.removeItem("user_id")
      location.reload()
    }
  }
}
</script>

<style scoped>

</style>