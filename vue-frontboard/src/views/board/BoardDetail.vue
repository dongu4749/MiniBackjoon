<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate"
        >수정</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-red" v-on:click="fnDelete"
        >삭제</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
    </div>
    <div class="board-contents">
      <h3>{{ title }}</h3>
      <div>
        <strong class="w3-large">{{ author }}</strong>
        <br>
        <span>{{ created_at }}</span>
      </div>
    </div>
    <div class="board-contents">
      <span>{{ contents }}</span>
    </div>
    <table class="w3-table-all">
      <thead>
        <tr>
          <th>댓글</th>
          <th>작성자</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, idx) in list" :key="idx">
          <td>{{ row.comment }}</td>
          <td>{{ row.id }}</td>
          <button type="button" class="w3-button w3-round w3-red" v-on:click="deleteComment(row.idx)"
            v-show="(row.id === nowuser)">삭제</button>&nbsp;
        </tr>
      </tbody>
    </table>
    <div>
      <!-- 댓글입력창 -->
      <p class="comment-form-comment">
        <label for="comment">Comments</label>
        <textarea cols="50" rows="2" v-model="comment" placeholder="댓글을 남겨보세요!" required="required"></textarea>
      </p>
      <!-- 댓글작성 버튼 -->
      <p class="form-submit" style="text-align: right">
        <input type="submit" v-on:click="addComment" value="댓글등록" class="submit site-button" />
      </p>
    </div>

  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: '',
      author: '',
      contents: '',
      created_at: '',

      board: '',
      comment: '',
      id: '',
      list: {},
      request: {},


    }
  },
  mounted() {
    this.fnGetView()
    this.commentList()
  },
  methods: {
    fnGetView() {
      this.$axios.get(this.$serverUrl + '/board/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        this.title = res.data.title
        this.author = res.data.author
        this.contents = res.data.contents
        this.created_at = res.data.created_at
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnList() {
      delete this.request.sk
      delete this.request.sv
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnUpdate() {
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return
      this.deleteCommentAll()

      this.$axios.delete(this.$serverUrl + '/board/' + this.idx, {})
        .then(() => {
          this.$axios.delete(this.$serverUrl + '/board/' + this.idx, {})
          alert('삭제되었습니다.')
          this.fnList();
          console.log(this.list)
        }).catch((err) => {
          console.log(err);
        })
    },
    commentList() {
      this.request = { // 데이터 전송
        sk: 'board',
        sv: this.idx,
      }
      this.$axios.get(this.$serverUrl + "/comment/list", {
        params: this.request,
        headers: {}
      }).then((res) => {
        if (res.data.result_code === "OK") {
          this.list = res.data.data
        }
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    addComment() {
      this.form = {
        "board": this.idx,
        "id": localStorage.getItem("user_id"),
        "comment": this.comment,
      }
      this.$axios.post(this.$serverUrl + '/comment/' + this.idx, this.form)
        .then(() => {
          alert('댓글이 저장되었습니다.')
          this.commentList()
        })
    },
    deleteComment(commentidx) {
      if (!confirm("삭제하시겠습니까?")) return
      this.$axios.delete(this.$serverUrl + '/comment/' + commentidx, {})
        .then(() => {
          alert('삭제되었습니다.')
          this.commentList();
          console.log(this.list)
        }).catch((err) => {
          console.log(err);
        })
    },
    deleteCommentAll() {
      for (var value of this.list) {
        this.$axios.delete(this.$serverUrl + '/comment/' + value.idx, {})
        this.commentList()
      }
      this.commentList()
    }
  },
}
</script>
<style scoped>

</style>