import React, {Component} from 'react';

class AuthPage extends Component {
    state = {
        login: "",
        password: "",
        isLoading: false
    }

    inputHandler = (e) => {
        this.setState(state => ({
            ...state,
            [e.target.name]: e.target.value
        }))
    }

    setFormLoading = (bool) => {
        this.setState({
            isLoading: bool
        })
    }

    loginButtonHandler = (e) => {
        e.preventDefault()
        this.setFormLoading(true)
        const req = {
            login: this.state.login, password: this.state.password
        }
        console.log(req)
        this.setFormLoading(false)
        this.props.setUserLogged()
    }

    render() {
        return (
            <div>
                <p>
                    <label>
                        Логин
                        <input type="text" name="login" value={this.state.login} onChange={this.inputHandler} disabled={this.state.isLoading}/>
                    </label>
                </p>
                <p>
                    <label>
                        Пароль
                        <input type="password" name="password" value={this.state.password} onChange={this.inputHandler} disabled={this.state.isLoading}/>
                    </label>
                </p>
                <button type="button" onClick={(e) => this.loginButtonHandler(e)} disabled={this.state.isLoading}>Войти</button>
            </div>
        );
    }
}

export default AuthPage;