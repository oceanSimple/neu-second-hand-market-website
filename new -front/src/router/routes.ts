const mainRoutes = [
  {
    path: '',
    component: () => import('@/view/main/index.vue'),
    name: 'main',
  },
]

const loginRoutes = [
  {
    path: '/login',
    component: () => import('@/view/login/index.vue'),
    name: 'login',
  },
]

export const route = Array.prototype.concat(mainRoutes, loginRoutes)