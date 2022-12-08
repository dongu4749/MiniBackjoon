import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import CommentList from '@/views/board/BoardDetail.vue'
import ProblemList from '@/views/problem/ProblemList.vue'
import ProblemCheck from '@/views/problem/ProblemCheck.vue'
import ProblemDetail from '@/views/problem/ProblemDetail.vue'
import ProblemWrite from '@/views/problem/ProblemWrite.vue'
import ProblemSubmit from '@/views/problem/ProblemSubmit.vue'
import Login from "@/views/common/Login"
import RegisterOne from "@/views/common/RegisterOne";
import store from "@/vuex/store";

const requireAuth = () => (from, to, next) => {
  const token = localStorage.getItem('user_token')
  if (token) {
    store.state.isLogin = true
    return next()
  } // isLogin === true면 페이지 이동
  next('/login') // isLogin === false면 다시 로그인 화면으로 이동
}

const requireAdminAuth = () => (from, to, next) => {
    const token = localStorage.getItem('user_token')
    const role = localStorage.getItem('user_role')
    if (token) {
        store.state.isLogin = true
        if(role === "ROLE_ADMIN"){
            return next()
        }

    } // isLogin === true면 페이지 이동
    alert('권한이 없습니다.')// isLogin === false면 다시 로그인 화면으로 이동
}

const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/common/login',
    name: 'Login',
    component: Login  //로그인 컴포넌트 추가
  },
  {
    path: '/common/registerOne',
    name: 'RegisterOne',
    component: RegisterOne // 회원가입 컴포넌트 추가
  },
  {
      path: '/problem/check',
      name: 'ProblemCheck',
      component: ProblemCheck,
      beforeEnter: requireAuth()
   },
  {
    path: '/board/list',
    name: 'BoardList',
    component: BoardList,
    beforeEnter: requireAuth()
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail,
    beforeEnter: requireAuth()
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite,
    beforeEnter: requireAuth()
  },
  {
    path: '/problem/list',
    name: 'ProblemList',
    component: ProblemList,
    beforeEnter: requireAuth()
  },
  {
    path: '/problem/detail',
    name: 'ProblemDetail',
    component: ProblemDetail,
    beforeEnter: requireAuth()
  },
  {
    path: '/problem/write',
    name: 'ProblemWrite',
    component: ProblemWrite,
    beforeEnter: requireAdminAuth()
  },
   {
      path: '/problem/submit',
      name: 'ProblemSubmit',
      component: ProblemSubmit,
      beforeEnter: requireAuth()
    },
     {
          path: '/comment/list',
          name: 'CommentList',
          component: CommentList,
          beforeEnter: requireAuth()
        }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router