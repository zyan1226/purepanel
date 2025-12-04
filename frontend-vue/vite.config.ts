import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from '@tailwindcss/vite'
import vueJsx from '@vitejs/plugin-vue-jsx'
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import {TDesignResolver} from '@tdesign-vue-next/auto-import-resolver';

// https://vite.dev/config/
export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd())

    return {
        plugins: [
            vue(),
            vueJsx(),
            vueDevTools(),
            tailwindcss(),
            AutoImport({
                resolvers: [TDesignResolver({
                    library: 'vue-next'
                })],
            }),
            Components({
                resolvers: [TDesignResolver({
                    library: 'vue-next'
                })],
            }),
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
