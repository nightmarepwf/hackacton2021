import React, {Component} from 'react';

class AddBloggerFrame extends Component {
    state = {
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

    createHandler = () => {
        console.log({
            u_name: this.state.firstName,
            u_soname: this.state.lastName,
            email: this.state.email,
            instagram: this.state.loginInstagram
        })
    }

    render() {
        return (
            <div>
                <p><label>Имя <input name="firstName" value={this.state.firstName} onChange={this.inputHandler}/></label></p>
                <p><label>Фамилия <input name="lastName" value={this.state.lastName} onChange={this.inputHandler}/></label></p>
                <p><label>Логин instagram <input name="loginInstagram" value={this.state.loginInstagram} onChange={this.inputHandler}/></label></p>
                <p><label>Email <input name="email" value={this.state.email} onChange={this.inputHandler}/></label></p>
                <button>Вернуться к списку</button>
                <button onClick={this.createHandler}>Добавить в реестр</button>
            </div>
        );
    }
}

export default AddBloggerFrame;