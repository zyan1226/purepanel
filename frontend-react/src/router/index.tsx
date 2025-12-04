import {createBrowserRouter} from "react-router";
import App from "../App.tsx";
import {LoginPage} from "../pages/login/LoginPage.tsx";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App/>,
    },
    {
        path: '/login',
        element: <LoginPage/>,
    },
]);

export default router;
