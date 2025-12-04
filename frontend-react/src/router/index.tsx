import {createBrowserRouter} from "react-router";
import LoginPage from "../pages/login/LoginPage.tsx";
import LayoutPage from "../pages/layout/LayoutPage.tsx";

const router = createBrowserRouter([
    {
        path: '/',
        element: <LayoutPage/>,
    },
    {
        path: '/login',
        element: <LoginPage/>,
    },
]);

export default router;
