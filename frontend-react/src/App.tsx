import {RouterProvider} from "react-router";
import router from './router';
import {theme} from "antd";


const {useToken} = theme;

function App() {
    const {token} = useToken();

    return (
        <div style={{
            backgroundColor: token.colorBgContainer,
            color: token.colorText
        }}>
            <RouterProvider router={router}/>
        </div>
    )
}

export default App
