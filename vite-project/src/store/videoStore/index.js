import { defineStore } from 'pinia'

const state = () => {
  return {
    // 视频列表
    videoPage: {
      total: 0
    },
    searchValue: '',
  }
}

const actions = {
 
}

const getters = {
  
}

export const useVideoStore = defineStore('videoStore', {
  // other options...
  state,
  actions,
  getters
})