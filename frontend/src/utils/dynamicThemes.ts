/**
 * 动态切换 TDesign 主题色
 * @param colorName 主题色文件名（不带 .css 后缀）
 */
export function setThemeColor(colorName: string): void {
    const id = "tdesign-theme-link";
    let link = document.getElementById(id) as HTMLLinkElement | null;

    if (!link) {
        link = document.createElement("link");
        link.id = id;
        link.rel = "stylesheet";
        document.head.appendChild(link);
    }
    //对应 public/themes/td-theme-xxx.css
    link.href = `/themes/td-theme-${colorName}.css`;
}
