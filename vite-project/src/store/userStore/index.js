import { defineStore } from 'pinia'

const state = () => {
  return {
    // 用户信息
    userData: {}
  }
}

const actions = {
 
}

const getters = {
  
}

export const useUserStore = defineStore('userStore', {
  // other options...
  state,
  actions,
  getters
})