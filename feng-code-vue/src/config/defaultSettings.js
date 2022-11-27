/**
 * 项目默认配置项
 * primaryColor - 默认主题色, 如果修改颜色不生效，请清理 localStorage
 * navTheme - sidebar theme ['dark', 'light'] 两种主题
 * colorWeak - 色盲模式
 * layout - 整体布局方式 ['sidemenu', 'topmenu'] 两种布局
 * fixedHeader - 固定 Header : boolean
 * fixSiderbar - 固定左侧菜单栏 ： boolean
 * contentWidth - 内容区布局： 流式 |  固定
 *
 * storageOptions: {} - Vue-ls 插件配置项 (localStorage/sessionStorage)
 *
 */

import { ROLES } from '@/store/mutation-types'
import storage from 'store'
import { isDesignatedRole } from '@/utils/util'
import { STUDENT_ROLE } from '@/store/role-constant'

// 管理后台界面布局
const sidemenuDark = {
  navTheme: 'dark', // theme for nav menu
  primaryColor: '#1890ff', // primary color of ant design
  layout: 'sidemenu', // nav menu position: `sidemenu` or `topmenu`
  contentWidth: 'Fluid', // layout of content: `Fluid` or `Fixed`, only works when layout is topmenu
  fixedHeader: true, // sticky header
  fixSiderbar: true, // sticky siderbar
  colorWeak: false,
  multiTab: true,
  tableSize: 'default',
  tableBordered: false,
  hideFooter: false,
  title: 'FengCode',
  production: process.env.NODE_ENV === 'production' && process.env.VUE_APP_PREVIEW !== 'true'
}

// 用户界面布局
const topmenu = {
  navTheme: 'light', // theme for nav menu
  primaryColor: '#1890ff', // primary color of ant design
  layout: 'topmenu', // nav menu position: sidemenu or topmenu
  contentWidth: 'Fluid', // layout of content: Fluid or Fixed, only works when layout is topmenu
  fixedHeader: false, // sticky header
  fixSiderbar: true, // sticky siderbar
  colorWeak: false,
  multiTab: false,
  tableSize: 'default',
  tableBordered: false,
  hideFooter: false,
  title: 'FengCode',
  production: process.env.NODE_ENV === 'production' && process.env.VUE_APP_PREVIEW !== 'true'

}

/**
 * 动态菜单
 * @returns {{multiTab: boolean, production: boolean, fixSiderbar: boolean, primaryColor: string, colorWeak: boolean, tableBordered: boolean, hideFooter: boolean, navTheme: string, title: string, layout: string, contentWidth: string, fixedHeader: boolean, tableSize: string}}
 */
export function dynamicMenu (roles) {
  if (!roles) {
    roles = storage.get(ROLES)
    console.log(`roles is false, get storage:`, roles)
  }
  // 如果是学生
  console.log('dynamicMenu Roles', roles)
  if (roles && isDesignatedRole(roles, STUDENT_ROLE)) {
    return topmenu
  } else {
    return sidemenuDark
  }
}

// export default {
//   ...dynamicMenu()
// }

// // 亮色配置
// export default {
//   navTheme: 'light', // theme for nav menu
//   primaryColor: '#1890FF', // primary color of ant design
//   layout: 'topmenu', // nav menu position: sidemenu or topmenu
//   contentWidth: 'Fixed', // layout of content: Fluid or Fixed, only works when layout is topmenu
//   fixedHeader: true, // sticky header
//   fixSiderbar: false, // sticky siderbar
//   colorWeak: false,
//   multiTab: false,
//   tableSize: 'middle',
//   tableBordered: false,
//   hideFooter: false,
//   title: 'FengCode',
//   production: process.env.NODE_ENV === 'production' && process.env.VUE_APP_PREVIEW !== 'true'
// }
