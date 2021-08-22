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
                <h4 className="pageSubTitle">Добавление в реестр</h4>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Имя</label>
                    <input type="text"
                           name="firstName"
                           value={this.state.firstName}
                           onChange={this.inputHandler}
                           style={{
                               padding: "5px 10px",
                               color: "#023e8a",
                               fontSize: "1.1rem",
                               borderColor: "#0077b6",
                               borderRadius: 5
                           }}
                    />
                </p>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Фамилия</label>
                    <input type="text"
                           name="lastName"
                           value={this.state.lastName}
                           onChange={this.inputHandler}
                           style={{
                               padding: "5px 10px",
                               color: "#023e8a",
                               fontSize: "1.1rem",
                               borderColor: "#0077b6",
                               borderRadius: 5
                           }}
                    />
                </p>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Логин instagram</label>
                    <input type="text"
                           name="loginInstagram"
                           value={this.state.loginInstagram}
                           onChange={this.inputHandler}
                           style={{
                               padding: "5px 10px",
                               color: "#023e8a",
                               fontSize: "1.1rem",
                               borderColor: "#0077b6",
                               borderRadius: 5
                           }}
                    />
                </p>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Email</label>
                    <input type="text"
                           name="email"
                           value={this.state.email}
                           onChange={this.inputHandler}
                           style={{
                               padding: "5px 10px",
                               color: "#023e8a",
                               fontSize: "1.1rem",
                               borderColor: "#0077b6",
                               borderRadius: 5
                           }}
                    />
                </p>
                <button className="primaryButton" style={{marginRight: 10}} onClick={this.props.backToBloggerList}>Вернуться к списку</button>
                <button className="primaryButton" onClick={this.createHandler}>Добавить в реестр</button>
            </div>
        );
    }
}

export default AddBloggerFrame;