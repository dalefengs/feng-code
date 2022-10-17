import storage from 'store'
import { login, getInfo, logout } from '@/api/login'
import {
  ACCESS_TOKEN, HIDE_FOOTER,
  ROLES,
   TABLE_BORDERED, TABLE_SIZE, TOGGLE_COLOR,
  TOGGLE_CONTENT_WIDTH,
  TOGGLE_FIXED_HEADER,
  TOGGLE_FIXED_SIDEBAR,
  TOGGLE_LAYOUT,
   TOGGLE_MULTI_TAB,
  TOGGLE_NAV_THEME, TOGGLE_WEAK
} from '@/store/mutation-types'
import { dynamicMenu } from '@/config/defaultSettings'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(res => {
          // 角色信息
          const roles = []
          res.roles.forEach(item => {
            roles.push({
              roleId: item.roleId,
              roleKey: item.roleKey,
              roleName: item.roleName
            })
          })
          const defaultSettings = dynamicMenu(roles)

          storage.set(ROLES, roles) // 角色信息
          storage.set(TOGGLE_NAV_THEME, defaultSettings.navTheme)
          storage.set(TOGGLE_LAYOUT, defaultSettings.layout)
          storage.set(TOGGLE_FIXED_HEADER, defaultSettings.fixedHeader)
          storage.set(TOGGLE_FIXED_SIDEBAR, defaultSettings.fixSiderbar)
          storage.set(TOGGLE_CONTENT_WIDTH, defaultSettings.contentWidth)
          storage.set(TOGGLE_COLOR, defaultSettings.primaryColor)
          storage.set(TOGGLE_WEAK, defaultSettings.colorWeak)
          storage.set(TOGGLE_MULTI_TAB, defaultSettings.multiTab)
          storage.set(TABLE_SIZE, defaultSettings.tableSize)
          storage.set(TABLE_BORDERED, defaultSettings.tableBordered)
          storage.set(HIDE_FOOTER, defaultSettings.hideFooter)

          // 设置s state
          this.state.app.layout = defaultSettings.layout
          this.state.app.layout = defaultSettings.layout
          this.state.app.theme = defaultSettings.navTheme
          this.state.app.contentWidth = defaultSettings.contentWidth
          this.state.app.fixedHeader = defaultSettings.fixedHeader
          this.state.app.fixSiderbar = defaultSettings.fixSiderbar
          this.state.app.multiTab = defaultSettings.multiTab
          this.state.app.tableSize = defaultSettings.tableSize
          this.state.app.colorWeak = defaultSettings.colorWeak
          this.state.app.tableBordered = defaultSettings.tableBordered
          this.state.app.tableBordered = defaultSettings.tableBordered
          this.state.app.hideFooter = defaultSettings.hideFooter

          storage.set(ACCESS_TOKEN, res.token, 7 * 24 * 60 * 60 * 1000)
          commit('SET_TOKEN', res.token)
          resolve()
        })
        .catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(res => {
          const user = res.user
          const avatar = user.avatar === '' ? require('@/assets/images/profile.jpg') : user.avatar
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.nickName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(error => {
          reject(error)
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          storage.remove(ROLES)
          storage.remove(ACCESS_TOKEN)
        })
      })
    }

  }
}

export default user
