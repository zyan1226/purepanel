import {Button, Form, Input} from "antd";
import {LockOutlined, UserOutlined} from "@ant-design/icons";

export default function LoginPage() {
    return (
        <div className="min-h-screen flex flex-col items-center bg-[url(./assets/login-bg.png)] p-20 gap-10">
            <p className="text-5xl font-bold">PurePanel</p>
            <div className="w-80">
                <Form>
                    <Form.Item name="username">
                        <Input prefix={<UserOutlined/>} placeholder="请输入用户名"/>
                    </Form.Item>
                    <Form.Item name="password">
                        <Input.Password prefix={<LockOutlined/>} placeholder="请输入密码"/>
                    </Form.Item>
                    <Form.Item name="password">
                        <Button className="w-full" type="primary">登录</Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );
}