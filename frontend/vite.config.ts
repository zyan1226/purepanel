import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
// import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from '@tailwindcss/vite'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vite.dev/config/
export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd())

    return {
        plugins: [
            vue(),
            vueJsx(),
            // vueDevTools(),
            tailwindcss(),
        ],
        resolve: {
            alias: {
                '@': fileURLToPath(new URL('./src', import.meta.url))
            },
        },
        server: {
            proxy: {
                [env.VITE_API_BASE]: {
                    target: env.VITE_API_TARGET,
                    changeOrigin: true,
                }
            }
        }
    }
})
