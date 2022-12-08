<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      전북대학교 소프트웨어공학과 조동우, 임승순, 신채윤, 백유승.<br>
      Go to the
      <a href="https://www.acmicpc.net/" target="_blank" rel="noopener">Baekjoon</a>.
    </p>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
