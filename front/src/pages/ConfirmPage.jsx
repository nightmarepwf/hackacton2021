import React, {Component} from 'react';
import $api from "../http";
import Logo from "../components/Logo.png";
import {Spinner} from "../components";

class ConfirmPage extends Component {
    state = {
        isLoading: true,
        status: null,
        eventInfo: null
    }

    async componentDidMount() {
        const res = await $api.post('/Confirm', JSON.stringify({
            guid: this.props.match.params.code
        }), {headers: {"Content-Type": "text/plain"}})
        this.setState(prevState => ({
            ...prevState, eventInfo: res.data
        }))
        this.setLoading(false)
    }

    setLoading = (bool) => {
        this.setState(prevState => ({
            ...prevState, isLoading: false
        }))
    }

    sendResponse = async (bool) => {
        const res = await $api.post('SetConfirm', JSON.stringify({
            guid: this.props.match.params.code,
            status: bool
        }), {headers: {"Content-Type": "text/plain"}})
        this.setState(prevState => ({
            ...prevState, status: bool === 1 ? "accepted" : "declined"
        }))
    }

    render() {
        if (this.state.isLoading) {
            return <Spinner />
        }
        if (!this.state.status) {
            return (<div style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                marginTop: 110
            }}>
                <img src={Logo} style={{marginBottom: 20}}/>
                <p style={{fontSize: "1.2rem"}}>Вы были приглашены принять участие в
                    мероприятии: <b>{this.state.eventInfo.event_info[0].title}</b></p>
                <p style={{fontSize: "1.1rem"}}><b>Описание: </b>{this.state.eventInfo.event_info[0].event_description}
                </p>
                <p><b>Дата
                    проведения: </b>{new Date(this.state.eventInfo.event_info[0].event_date).toLocaleDateString()}</p>
                <button className="primaryButton" style={{marginBottom: 20}}
                        onClick={() => this.sendResponse(1)}>Принять приглашение
                </button>
                <p style={{fontSize: "1.1rem", marginTop: 10, color: "#023e8a"}}
                   onClick={() => this.sendResponse(2)}>Отказаться</p>
            </div>)
        }
        if (this.state.status === "accepted") {
            return <div style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                marginTop: 110
            }}>
                <img src={Logo} style={{marginBottom: 20}}/>
                <p style={{fontSize: "1.2rem"}}>Вы приняли приглашение на участие в
                    мероприятии: <b>{this.state.eventInfo.event_info[0].title}</b></p>
            </div>
        }
        return <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            marginTop: 110
        }}>
            <img src={Logo} style={{marginBottom: 20}}/>
            <p style={{fontSize: "1.2rem"}}>Вы отклонили приглашение на участие в
                мероприятии: <b>{this.state.eventInfo.event_info[0].title}</b></p>
        </div>
    }
}

export default ConfirmPage;