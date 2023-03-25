// vite.config.js
import { defineConfig } from "file:///E:/bishe/Code/vite-project/node_modules/vite/dist/node/index.js";
import vue from "file:///E:/bishe/Code/vite-project/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import Components from "file:///E:/bishe/Code/vite-project/node_modules/unplugin-vue-components/dist/vite.mjs";
import { AntDesignVueResolver } from "file:///E:/bishe/Code/vite-project/node_modules/unplugin-vue-components/dist/resolvers.mjs";
import path from "path";
var __vite_injected_original_dirname = "E:\\bishe\\Code\\vite-project";
var vite_config_default = defineConfig({
  plugins: [
    vue(),
    /* ... */
    Components({
      resolvers: [
        AntDesignVueResolver({
          importStyle: "less",
          // 自动引入 ant-design/icons-vue中的图标，需要安装@ant-design/icons-vue
          resolveIcons: true
        })
      ]
    })
  ],
  resolve: {
    alias: {
      "@": path.resolve(__vite_injected_original_dirname, "src"),
      "comps": path.resolve(__vite_injected_original_dirname, "src/components")
    }
  },
  // 开启less支持 自定义主题
  css: {
    preprocessorOptions: {
      less: {
        modifyVars: {
          "primary-color": "#336699",
          "link-color": "#336699",
          "border-radius-base": "2px"
        },
        javascriptEnabled: true
      }
    }
  },
  server: {
    proxy: {
      // 接口地址代理
      "/demo": {
        target: "http://localhost:8088",
        // 接口的域名
        // secure: false, // 如果是https接口，需要配置这个参数
        changeOrigin: true,
        // 如果接口跨域，需要进行这个参数配置
        rewrite: (path2) => path2.replace(/^\/demo/, "")
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJFOlxcXFxiaXNoZVxcXFxDb2RlXFxcXHZpdGUtcHJvamVjdFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcYmlzaGVcXFxcQ29kZVxcXFx2aXRlLXByb2plY3RcXFxcdml0ZS5jb25maWcuanNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L2Jpc2hlL0NvZGUvdml0ZS1wcm9qZWN0L3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcbmltcG9ydCB2dWUgZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlJ1xuLy8gQW50IERlc2lnbiBcdTYzMDlcdTk3MDBcdTVGMTVcdTUxNjVcbmltcG9ydCBDb21wb25lbnRzIGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3ZpdGUnO1xuaW1wb3J0IHsgQW50RGVzaWduVnVlUmVzb2x2ZXIgfSBmcm9tICd1bnBsdWdpbi12dWUtY29tcG9uZW50cy9yZXNvbHZlcnMnO1xuXG5pbXBvcnQgcGF0aCBmcm9tICdwYXRoJztcblxuXG4vLyBodHRwczovL3ZpdGVqcy5kZXYvY29uZmlnL1xuZXhwb3J0IGRlZmF1bHQgZGVmaW5lQ29uZmlnKHtcbiAgcGx1Z2luczogW1xuICAgIHZ1ZSgpLFxuICAgIC8qIC4uLiAqL1xuICAgIENvbXBvbmVudHMoe1xuICAgICAgcmVzb2x2ZXJzOiBbXG4gICAgICAgIEFudERlc2lnblZ1ZVJlc29sdmVyKHtcbiAgICAgICAgICBpbXBvcnRTdHlsZTogJ2xlc3MnLFxuICAgICAgICAgIC8vIFx1ODFFQVx1NTJBOFx1NUYxNVx1NTE2NSBhbnQtZGVzaWduL2ljb25zLXZ1ZVx1NEUyRFx1NzY4NFx1NTZGRVx1NjgwN1x1RkYwQ1x1OTcwMFx1ODk4MVx1NUI4OVx1ODhDNUBhbnQtZGVzaWduL2ljb25zLXZ1ZVxuICAgICAgICAgIHJlc29sdmVJY29uczogdHJ1ZSxcbiAgICAgICAgfSlcbiAgICAgIF0sXG4gICAgfSksXG4gICAgXG4gIF0sXG5cbiAgcmVzb2x2ZToge1xuICAgIGFsaWFzOiB7XG4gICAgICBcIkBcIjogcGF0aC5yZXNvbHZlKF9fZGlybmFtZSwgXCJzcmNcIiksXG4gICAgICBcImNvbXBzXCI6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsIFwic3JjL2NvbXBvbmVudHNcIiksXG4gICAgfSxcbiAgfSxcblxuICAvLyBcdTVGMDBcdTU0MkZsZXNzXHU2NTJGXHU2MzAxIFx1ODFFQVx1NUI5QVx1NEU0OVx1NEUzQlx1OTg5OFxuICBjc3M6IHtcbiAgICBwcmVwcm9jZXNzb3JPcHRpb25zOiB7XG4gICAgICBsZXNzOiB7XG4gICAgICAgIG1vZGlmeVZhcnM6IHtcbiAgICAgICAgICAncHJpbWFyeS1jb2xvcic6ICcjMzM2Njk5JyxcbiAgICAgICAgICAnbGluay1jb2xvcic6ICcjMzM2Njk5JyxcbiAgICAgICAgICAnYm9yZGVyLXJhZGl1cy1iYXNlJzogJzJweCcsXG4gICAgICAgIH0sXG4gICAgICAgIGphdmFzY3JpcHRFbmFibGVkOiB0cnVlXG4gICAgICB9XG4gICAgfVxuICB9LFxuXG4gIHNlcnZlcjoge1xuICAgIHByb3h5OiB7XG4gICAgICAvLyBcdTYzQTVcdTUzRTNcdTU3MzBcdTU3NDBcdTRFRTNcdTc0MDZcbiAgICAgICcvZGVtbyc6IHtcbiAgICAgICAgdGFyZ2V0OiAnaHR0cDovL2xvY2FsaG9zdDo4MDg4JywgLy8gXHU2M0E1XHU1M0UzXHU3Njg0XHU1N0RGXHU1NDBEXG4gICAgICAgIC8vIHNlY3VyZTogZmFsc2UsIC8vIFx1NTk4Mlx1Njc5Q1x1NjYyRmh0dHBzXHU2M0E1XHU1M0UzXHVGRjBDXHU5NzAwXHU4OTgxXHU5MTREXHU3RjZFXHU4RkQ5XHU0RTJBXHU1M0MyXHU2NTcwXG4gICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSwgLy8gXHU1OTgyXHU2NzlDXHU2M0E1XHU1M0UzXHU4REU4XHU1N0RGXHVGRjBDXHU5NzAwXHU4OTgxXHU4RkRCXHU4ODRDXHU4RkQ5XHU0RTJBXHU1M0MyXHU2NTcwXHU5MTREXHU3RjZFXG4gICAgICAgIHJld3JpdGU6IHBhdGggPT4gcGF0aC5yZXBsYWNlKC9eXFwvZGVtby8sICcnKVxuICAgICAgfSxcbiAgICB9XG4gIH0sXG59KVxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUF3USxTQUFTLG9CQUFvQjtBQUNyUyxPQUFPLFNBQVM7QUFFaEIsT0FBTyxnQkFBZ0I7QUFDdkIsU0FBUyw0QkFBNEI7QUFFckMsT0FBTyxVQUFVO0FBTmpCLElBQU0sbUNBQW1DO0FBVXpDLElBQU8sc0JBQVEsYUFBYTtBQUFBLEVBQzFCLFNBQVM7QUFBQSxJQUNQLElBQUk7QUFBQTtBQUFBLElBRUosV0FBVztBQUFBLE1BQ1QsV0FBVztBQUFBLFFBQ1QscUJBQXFCO0FBQUEsVUFDbkIsYUFBYTtBQUFBO0FBQUEsVUFFYixjQUFjO0FBQUEsUUFDaEIsQ0FBQztBQUFBLE1BQ0g7QUFBQSxJQUNGLENBQUM7QUFBQSxFQUVIO0FBQUEsRUFFQSxTQUFTO0FBQUEsSUFDUCxPQUFPO0FBQUEsTUFDTCxLQUFLLEtBQUssUUFBUSxrQ0FBVyxLQUFLO0FBQUEsTUFDbEMsU0FBUyxLQUFLLFFBQVEsa0NBQVcsZ0JBQWdCO0FBQUEsSUFDbkQ7QUFBQSxFQUNGO0FBQUE7QUFBQSxFQUdBLEtBQUs7QUFBQSxJQUNILHFCQUFxQjtBQUFBLE1BQ25CLE1BQU07QUFBQSxRQUNKLFlBQVk7QUFBQSxVQUNWLGlCQUFpQjtBQUFBLFVBQ2pCLGNBQWM7QUFBQSxVQUNkLHNCQUFzQjtBQUFBLFFBQ3hCO0FBQUEsUUFDQSxtQkFBbUI7QUFBQSxNQUNyQjtBQUFBLElBQ0Y7QUFBQSxFQUNGO0FBQUEsRUFFQSxRQUFRO0FBQUEsSUFDTixPQUFPO0FBQUE7QUFBQSxNQUVMLFNBQVM7QUFBQSxRQUNQLFFBQVE7QUFBQTtBQUFBO0FBQUEsUUFFUixjQUFjO0FBQUE7QUFBQSxRQUNkLFNBQVMsQ0FBQUEsVUFBUUEsTUFBSyxRQUFRLFdBQVcsRUFBRTtBQUFBLE1BQzdDO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFDRixDQUFDOyIsCiAgIm5hbWVzIjogWyJwYXRoIl0KfQo=
