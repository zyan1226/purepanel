import Link from 'next/link';
import {buttonVariants} from "@/components/ui/button";
import {cn} from "@/lib/cn";

export default function HomePage() {
    return (
        <main className="flex flex-1 flex-col justify-center items-center gap-10">
            <h1 className="text-8xl font-bold leading-tight">
                极致轻量的
                <br/>
                中后台项目快速启动脚手架</h1>
            <p className="text-3xl text-fd-muted-foreground">易读、易开发、易维护，优雅高效。</p>
            <div className="flex flex-row gap-4">
                <button className={cn(buttonVariants({
                    color: 'primary'
                }), 'rounded-full px-4 text-md')}>
                    <Link href='/docs/overview'>
                        快速上手
                    </Link>
                </button>
                <button className={cn(buttonVariants({
                    color: 'outline'
                }), 'rounded-full px-4 text-md')}>
                    <a href='https://github.com/zyan1226/purepanel' target="_blank" rel="noopener noreferrer">
                        GitHub
                    </a>
                </button>
            </div>
        </main>
    );
}
