import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// Ant Design 按需引入
import Components from 'unplugin-vue-components/vite';
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers';

import path from 'path';


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    /* ... */
    Components({
      resolvers: [
        AntDesignVueResolver({
          importStyle: 'less',
          // 自动引入 ant-design/icons-vue中的图标，需要安装@ant-design/icons-vue
          resolveIcons: true,
        })
      ],
    }),
    
  ],

  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
      "comps": path.resolve(__dirname, "src/components"),
    },
  },

  // 开启less支持 自定义主题
  css: {
    preprocessorOptions: {
      less: {
        modifyVars: {
          'primary-color': '#336699',
          'link-color': '#336699',
          'border-radius-base': '2px',
        },
        javascriptEnabled: true
      }
    }
  },

  server: {
    proxy: {
      // 接口地址代理
      '/demo': {
        target: 'http://localhost:8088', // 接口的域名
        // secure: false, // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        rewrite: path => path.replace(/^\/demo/, '')
      },
    }
  },
})
