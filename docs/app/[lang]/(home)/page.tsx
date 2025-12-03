import Link from 'next/link';
import {WordRotate} from "@/components/ui/word-rotate";
import {RainbowButton} from "@/components/ui/rainbow-button";
import {SparklesText} from "@/components/ui/sparkles-text";

export default function HomePage() {
    return (
        <main className="flex flex-1 flex-col justify-center items-center gap-10">
            <div className="text-5xl font-bold">
                <WordRotate words={["极致轻量", "开箱即用", "易开发", "优雅高效"]}/>
                的中后台项目快速启动脚手架
            </div>
            <SparklesText className="text-base">
                <p>一个基于 Spring Boot 3 和 Vue3 的极致轻量的中后台项目快速启动脚手架。</p>
            </SparklesText>
            <div className="flex flex-row gap-4">
                <RainbowButton size="lg" className="rounded-full px-6 text-md">
                    <Link href='/docs/overview'>
                        快速上手
                    </Link>
                </RainbowButton>
                <RainbowButton size="lg" variant="outline" className="rounded-full px-6 text-md">
                    <a href='https://github.com/zyan1226/purepanel' target="_blank" rel="noopener noreferrer">
                        GitHub
                    </a>
                </RainbowButton>
            </div>
        </main>
    );
}
