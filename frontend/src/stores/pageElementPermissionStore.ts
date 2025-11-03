import {ref} from 'vue'
import {defineStore} from 'pinia'
import type {SysPermission} from "@/types/SysPermission.ts";

export const usePageElementPermissionStore = defineStore('pageElementPermissionStore', () => {
    const pageElementPermissionList = ref<SysPermission[]>([])

    function update(newList: SysPermission[]) {
        pageElementPermissionList.value = newList
    }

    return {pageElementPermissionList, update}
})