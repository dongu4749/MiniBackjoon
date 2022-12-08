<template>
    <div class="board-list">
        <table class="w3-table-all">
            <thead>
                <tr>
                    <th>문제 번호</th>
                    <th>아이디</th>
                    <th>코드</th>
                    <th>채점 결과</th>
                    <th>등록일시</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, idx) in list" :key="idx">
                    <td>{{ row.problem }}</td>
                    <td>{{ row.id }}</td>
                    <td>{{ row.code }}</td>
                    <td>{{ row.result }}</td>
                    <td>{{ row.created_at }}</td>
                </tr>
            </tbody>
        </table>
        <br />
        <div class="goback-buttons">
            <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnGoBack(this.idx)">문제로 돌아가기</button>
        </div>
        <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
            <span class="pg">
                <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-border">&lt;&lt;</a>
                <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page - 1}`)"
                    class="prev w3-button w3-border">&lt;</a>
                <template v-for=" (n, index) in paginavigation()">
                    <template v-if="paging.page == n">
                        <strong class="w3-button w3-border w3-green" :key="index">{{ n }}</strong>
                    </template>
                    <template v-else>
                        <a class="w3-button w3-border" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{ n
                        }}</a>
                    </template>
                </template>
                <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page"
                    @click="fnPage(`${paging.end_page + 1}`)" class="next w3-button w3-border">&gt;</a>
                <a href="javascript:;" @click="fnPage(`${paging.total_page_cnt}`)"
                    class="last w3-button w3-border">&gt;&gt;</a>
            </span>
        </div>
    </div>
</template>
<script>
export default {
    data() { //변수생성
        return {
            requestBody: this.$route.query,
            idx: this.$route.query.idx,
            // requestBody: {}, //리스트 페이지 데이터전송
            list: {}, //리스트 데이터

            paging: {
                block: 0,
                end_page: 0,
                next_block: 0,
                page: 0,
                page_size: 0,
                prev_block: 0,
                start_index: 0,
                start_page: 0,
                total_block_cnt: 0,
                total_list_cnt: 0,
                total_page_cnt: 0,
            }, //페이징 데이터
            page: this.$route.query.page ? this.$route.query.page : 1,
            size: this.$route.query.size ? this.$route.query.size : 10,
            search_key: this.$route.query.sk ? this.$route.query.sk : '',
            search_value: this.$route.query.sv ? this.$route.query.sv : '',
            // keyword: this.$route.query.keyword,
            paginavigation: function () { //페이징 처리 for문 커스텀
                let pageNumber = [] //;
                let start_page = this.paging.start_page;
                let end_page = this.paging.end_page;
                for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
                return pageNumber;
            }
        }
    },
    mounted() {
        this.fnGetList()
    },
    methods: {
        fnGoBack(idx) {
            delete this.requestBody.idx
            delete this.requestBody.sk
            delete this.requestBody.sv
            this.requestBody.idx = idx
            this.$router.push({
                path: './detail',
                query: this.requestBody
            })
        },
        fnGetList() {
            this.requestBody = {
                sk: 'problemid',
                sv: this.idx + localStorage.getItem('user_id'),
                page: this.page,
                size: this.size
            }
            this.$axios.get(this.$serverUrl + "/problem/check", {
                params: this.requestBody,
                headers: {}
            }).then((res) => {
                if (res.data.result_code === "OK") {
                    this.list = res.data.data
                    this.paging = res.data.pagination
                    this.no = this.paging.total_list_cnt - ((this.paging.page - 1) * this.paging.page_size)
                }
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
        },

    }
}
</script>
<style scoped>

</style>