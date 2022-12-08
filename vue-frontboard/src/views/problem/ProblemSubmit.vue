<template>
  <div class="problem-submit">
    <textarea rows="20" cols="75" type="text" v-model="code"></textarea>
    <br />
    <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="handleSubmit">제출</button>
    <p>{{ status }}</p>
    <p>{{ jobId && `JobId: ${jobId}` }}</p>
    <p>{{ output }}</p>
    <div class="goback-buttons">
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnGoBack">문제로 돌아가기</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      title: '',
      author: '',
      contents: '',
      created_at: '',
      code: "",
      language: "cpp",
      output: "",
      status: "",
      jobId: "",
      jobDetails: null,
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios.get(this.$serverUrl + '/problem/' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          this.title = res.data.title
          this.author = res.data.author
          this.contents = res.data.contents
          this.created_at = res.data.created_at
        }).catch((err) => {
          console.log(err)
        })
      }
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnGoBack() {
      let apiUrl = this.$serverUrl + '/problem'
      this.form = {
        "idx": this.idx,
        "title": this.title,
        "contents": this.contents,
        "author": this.author
      }
      this.$axios.patch(apiUrl, this.form)
        .then((res) => {
          this.fnView(res.data.idx)
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
    },
    async handleSubmit() {
      this.payload = {
        language: this.language = "cpp",
        code: this.code
      }
      try {
        this.jobId = ""
        this.status = ""
        this.output = ""
        this.jobDetails = null
        const { data } = await axios.post("http://localhost:5000/run", this.payload)
        console.log(data)
        this.jobId = data.jobId
        let intervalId
        intervalId = setInterval(async () => {
          const { data: dataRes } = await axios.get("http://localhost:5000/status", { params: { id: data.jobId } })
          const { success, job, error } = dataRes
          console.log(dataRes)
          if (success) {
            const { status: jobStatus, output: jobOutput } = job
            this.status = jobStatus
            this.jobDetails = job
            if (jobStatus === "pending") return
            this.output = jobOutput
            clearInterval(intervalId)
          } else {
            this.status = "Error: Please retry!"
            console.error(error)
            clearInterval(intervalId)
            this.output = error
          }
          console.log(dataRes)
        }, 1000)
      } catch ({ response }) {
        if (response) {
          const errMsg = response.data.err.stderr
          this.output = errMsg
        } else {
          this.output = "Error connecting to server"
        }
      }
    }
  }
}
</script>

<style>
#SubmitForm {
  width: 100px;
  margin: auto;
}
</style>