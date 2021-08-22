import React from "react";
import {AuthPage} from './pages/index'
import BloggerRouter from "./components/BloggerRouter";
import {ManagerRouter} from "./components";

class App extends React.Component {
    state = {
        isUserLogged: true,
        userRole: "manager"
    };

    setUserLogged = () => {
        this.setState({
            isUserLogged: true, userRole: "manager"
        })
    }

    componentDidMount() {
        //TODO: проверка авторизации
    }

    render() {
        if (!this.state.isUserLogged) {
            return (
                <AuthPage setUserLogged={this.setUserLogged}/>
            );
        }
        switch (this.state.userRole) {
            case "blogger":
                return <BloggerRouter/>
            case "manager":
                return <ManagerRouter/>
            default:
                return <BloggerRouter/>
        }
    }
}

export default App;
