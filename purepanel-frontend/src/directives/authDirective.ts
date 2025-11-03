import type {Directive} from 'vue'
import {usePageElementPermissionStore} from "@/stores/pageElementPermissionStore.ts";

const authDirective: Directive = {
    mounted(el, binding) {
        check(el, binding)
    },
    updated(el, binding) {
        check(el, binding)
    }
}

function check(el: HTMLElement, binding: any) {
    const pageElementPermissionStore = usePageElementPermissionStore()

    if (!pageElementPermissionStore || !binding.value) return

    const code = binding.value
    const hasPermission = pageElementPermissionStore.pageElementPermissionList.some(item => item.authCode === code)

    if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
    }
}

export default authDirective
