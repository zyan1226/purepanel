import {RouterProvider} from "react-router";
import router from './router';
import {ConfigProvider, theme} from "antd";


const {useToken} = theme;

function App() {

    const {token} = useToken();

    return (
        <ConfigProvider
            theme={{
                token: {
                    colorPrimary: "#637dff",
                    colorInfo: "#637dff",
                },
                algorithm: theme.defaultAlgorithm
            }}>
            <div style={{
                backgroundColor: token.colorBgContainer,
                color: token.colorText
            }}>
                <RouterProvider router={router}/>
            </div>
        </ConfigProvider>
    )
}

export default App
