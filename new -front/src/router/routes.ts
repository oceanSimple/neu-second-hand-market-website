const mainRoutes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/main',
    redirect: '/main/home',
    component: () => import('@/view/main/index.vue'),
    children: [
      {
        path: '/main/home',
        component: () => import('@/view/main/home/index.vue'),
        name: 'home',
      },
      {
        path: '/main/favorite',
        component: () => import('@/view/main/favorite/index.vue'),
        name: 'favorite',
      },
      {
        path: '/main/historyOrder',
        component: () => import('@/view/main/historyOrder/index.vue'),
        name: 'historyOrder',
      },
      {
        path: '/main/message',
        component: () => import('@/view/main/message/index.vue'),
        name: 'message',
      },
      {
        path: '/main/info',
        component: () => import('@/view/main/info/index.vue'),
        name: 'info',
      },
    ],
  },
]

const loginRoutes = [
  {
    path: '/login',
    component: () => import('@/view/login/index.vue'),
    name: 'login',
  },
  {
    path: '/register',
    component: () => import('@/view/register/index.vue'),
    name: 'register',
  },
  {
    path: '/findBackPsw',
    component: () => import('@/view/findBackPsw/index.vue'),
    name: 'findBackPsw',
  },
]

const homePageRoutes = [
  {
    path: '/homePage',
    component: () => import('@/view/homePage/index.vue'),
    name: 'homePage',
  },
]

const optionRoutes = [
  {
    path: '/favorite',
    component: () => import('@/view/favorite/index.vue'),
    name: 'favorite',
  },
  {
    path: '/historyOrder',
    component: () => import('@/view/historyOrder/index.vue'),
    name: 'historyOrder',
  },
  {
    path: '/info',
    component: () => import('@/view/info/index.vue'),
    name: 'info',
  },
  {
    path: '/message',
    component: () => import('@/view/message/index.vue'),
    name: 'message',
  },
]

export const route = Array.prototype.concat(
  mainRoutes,
  loginRoutes,
  homePageRoutes,
  optionRoutes,
)