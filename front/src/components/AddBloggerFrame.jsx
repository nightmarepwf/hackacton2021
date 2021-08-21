import React, {Component} from 'react';
import $api from "../http";

class AddBloggerFrame extends Component {
    state = {
        isLoading: false,
        firstName: "",
        lastName: "",
        loginInstagram: "",
        email: "",
    }

    inputHandler = (e) => {
        this.setState(prevState => ({
            ...prevState,
            [e.target.name]: e.target.value
        }))
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    createHandler = async () => {
        this.setLoading(true);
        const bloggers = await $api.post("Blogers", JSON.stringify({
            u_name: this.state.firstName,
            u_soname: this.state.lastName,
            email: this.state.email,
            instagram: this.state.loginInstagram
        }), {headers: {"Content-Type": "text/plain"}});
        this.setLoading(false)
        this.props.toBloggersList(bloggers)
    }

    render() {
        return (
            <div>
                <p><label>Имя <input name="firstName" value={this.state.firstName} onChange={this.inputHandler}/></label></p>
                <p><label>Фамилия <input name="lastName" value={this.state.lastName} onChange={this.inputHandler}/></label></p>
                <p><label>Логин instagram <input name="loginInstagram" value={this.state.loginInstagram} onChange={this.inputHandler}/></label></p>
                <p><label>Email <input name="email" value={this.state.email} onChange={this.inputHandler}/></label></p>
                <button onClick={this.props.toBloggersList}>Вернуться к списку</button>
                <button onClick={this.createHandler}>Добавить в реестр</button>
            </div>
        );
    }
}

export default AddBloggerFrame;