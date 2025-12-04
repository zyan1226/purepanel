import 'tdesign-vue-next/es/style/index.css';
import './assets/main.css'

import {createApp} from 'vue'
import {createPinia} from 'pinia'
import {MotionPlugin} from '@vueuse/motion'

import App from './App.vue'
import router from './router'
import authDirective from "@/directives/authDirective.ts";

const app = createApp(App)
app.directive('auth', authDirective)
app.use(createPinia())
app.use(router)
app.use(MotionPlugin)
app.mount('#app')
