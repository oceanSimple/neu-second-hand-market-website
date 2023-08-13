interface menuItem {
  route: string
  name: string
  icon: string
}

export const menuItems: menuItem[] = [
  {
    route: '/main/home',
    name: '首页',
    icon: 'House',
  },
  {
    route: '/main/favorite',
    name: '收藏栏',
    icon: 'Star',
  },
  {
    route: '/main/historyOrder',
    name: '历史订单',
    icon: 'Tickets',
  },
  {
    route: '/main/message',
    name: '消息',
    icon: 'ChatLineSquare',
  },
  {
    route: '/main/info',
    name: '个人信息',
    icon: 'User',
  },
]