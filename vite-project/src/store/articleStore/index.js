import { defineStore } from 'pinia'

const state = () => {
  return {
    // 视频列表
    articlePage: {
      total: 0
    },
    searchValue: '',
  }
}

const actions = {
 
}

const getters = {
  
}

export const useArticleStore = defineStore('articleStore', {
  // other options...
  state,
  actions,
  getters
})