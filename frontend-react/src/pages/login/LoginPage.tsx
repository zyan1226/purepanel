import {Button, Form, Input, Typography} from "antd";
import {LockOutlined, SafetyOutlined, UserOutlined} from "@ant-design/icons";
import Logo from "../../assets/logo.png";

export default function LoginPage() {
    return (
        <div className="min-h-screen flex items-center justify-center bg-[url(./assets/login-bg.png)]">
            <div className="flex flex-col gap-10">
                <div className="flex flex-col items-center gap-4">
                    <div className="flex flex-row gap-4">
                        <img alt="logo" className="w-10" src={Logo}/>
                        <p className="text-3xl font-bold">
                            PurePanel
                        </p>
                    </div>
                    <Typography.Text type="secondary">一个极简、纯粹、易上手的中后台项目启动脚手架 🪜</Typography.Text>
                </div>
                <Form>
                    <Form.Item name="username">
                        <Input prefix={<UserOutlined/>} placeholder="请输入用户名"/>
                    </Form.Item>
                    <Form.Item name="password">
                        <Input.Password prefix={<LockOutlined/>} placeholder="请输入密码"/>
                    </Form.Item>
                    <Form.Item name="captcha">
                        <div className="flex flex-row gap-2 items-center">
                            <Input prefix={<SafetyOutlined/>} placeholder="请输入验证码"/>
                            <img alt="captcha" className="h-9 w-30 rounded-md" src="https://picsum.photos/200/200"/>
                        </div>
                    </Form.Item>
                    <Form.Item>
                        <Button htmlType="submit" className="w-full" type="primary">登录</Button>
                    </Form.Item>
                </Form>
            </div>
            <div className="absolute bottom-10">
                <Typography.Text type="secondary">Copyright @ 2021-{new Date().getFullYear()} PurePanel. All Rights
                    Reserved</Typography.Text>
            </div>
        </div>
    );
}