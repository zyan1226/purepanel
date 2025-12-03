import '@/app/[lang]/global.css';
import {RootProvider} from 'fumadocs-ui/provider';
import {Inter} from 'next/font/google';
import {ReactNode} from "react";
import {defineI18nUI} from "fumadocs-ui/i18n";
import {i18n} from "@/lib/i18n";

const inter = Inter({
  subsets: ['latin'],
});

const { provider } = defineI18nUI(i18n, {
    translations: {
        cn: {
            displayName: '简体中文',
            search: '搜索文档',
        },
    },
});

export default async function Layout({ children,  params }: {  params: Promise<{ lang: string }>;children: ReactNode}) {
  const lang = (await params).lang;
  return (
    <html lang="zh-cn" className={inter.className} suppressHydrationWarning>
      <body className="flex flex-col min-h-screen">
        <RootProvider i18n={provider(lang)}>{children}</RootProvider>
      </body>
    </html>
  );
}
