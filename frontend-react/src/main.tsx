import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from "./App.tsx";
import {ConfigProvider, theme} from "antd";


createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <ConfigProvider
            theme={{
                token: {
                    colorPrimary: "#637dff",
                    colorInfo: "#637dff",
                },
                algorithm: theme.darkAlgorithm
            }}>
            <App/>
        </ConfigProvider>
    </StrictMode>,
)
