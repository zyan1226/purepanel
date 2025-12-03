import type {BaseLayoutProps} from 'fumadocs-ui/layouts/shared';
import {i18n} from "@/lib/i18n";
import logo from "@/app/assets/logo.png";

/**
 * Shared layout configurations
 *
 * you can customise layouts individually from:
 * Home Layout: app/(home)/layout.tsx
 * Docs Layout: app/docs/layout.tsx
 */
export function baseOptions(locale: string): BaseLayoutProps {
    return {
        i18n,
        githubUrl: 'https://github.com/zyan1226/purepanel',
        nav: {
            title: (
                <>
                    <img src={logo.src} alt="logo" className="h-6"/>
                    PurePanel
                </>
            ),
        },
        // see https://fumadocs.dev/docs/ui/navigation/links
        links: [],
    };
}
