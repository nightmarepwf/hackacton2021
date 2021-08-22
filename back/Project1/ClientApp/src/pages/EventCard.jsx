import React, {Component} from 'react';
import $api from "../http";
import {Spinner} from "../components";

class EventCard extends Component {
    state = {
        isLoading: true,
        eventInfo: null
    }

    async componentDidMount() {
        const res = await $api.post(`Event`, JSON.stringify({id: this.props.match.params.id}), {headers: {"Content-Type": "text/plain"}});
        this.setState(prevState => ({
            ...prevState, isLoading: false, eventInfo: res.data
        }))
        console.log(this.state)
    }

    render() {
        if (this.state.isLoading) {
            return <Spinner />
        }
        return (
            <div>
                <h3 className="pageTitle">Карточка события</h3>
                <h4 className="pageSubTitle">{this.state.eventInfo.event_info[0].title}</h4>
                <img src="https://dt.samregion.ru/wp-content/uploads/sites/40/2021/08/ubqpa_zrx7y.jpg"
                     style={{maxWidth: "100%"}}/>
                <div>
                    <p><b>Описание: </b>{this.state.eventInfo.event_info[0].event_description}</p>
                    <p><b>Дата: </b>{new Date(this.state.eventInfo.event_info[0].event_date).toLocaleDateString()}</p>
                    <p><b>Теги: </b>{this.state.eventInfo.tags.map(item => item.tag).join(' ') || "Нет тегов"}</p>
                    <p>
                        <b>Упоминания: </b>{this.state.eventInfo.mentions.map(item => item.tag).join(' ') || "Нет упоминаний"}
                    </p>
                    <h4 className="pageSubTitle">Список приглашенных блоггеров :</h4>
                    <div>
                        {this.state.eventInfo.blogers.map(blogger => {
                            return <div style={{display: "flex", flexDirection: "row", alignItems:"flex-start", padding: 15, border: "1px solid #0077b6", borderRadius: 5, marginBottom: 20}}>
                                <div className="avatar" style={{
                                    display: "flex",
                                    flexDirection: "row",
                                    justifyContent: "center",
                                    alignItems: "center",
                                    width: 80,
                                    height: 80,
                                    borderRadius: 60,
                                    background: "#0077b6",
                                    marginRight: 30,
                                    color: "white",
                                    fontSize: 36
                                }}>{blogger.u_name[0]}</div>
                                <div>
                                    <p className="womt"><b>Имя: </b>{`${blogger.u_name} ${blogger.u_soname}`}</p>
                                    <p className="womt"><b>Instagram: </b>@{blogger.instagram}</p>
                                    <p className="womt"><b>Email: </b>{blogger.email}</p>
                                    <p className="womt"><b>Рейтинг: </b>{blogger.rating}/10</p>
                                    <p className="womt"><b>Статус: </b>{!blogger.invite_accept ? "Ожидание ответа" : blogger.invite_accept === 1 ? "Принято" : "Отклонено"}</p>
                                </div>

                            </div>
                        })}
                    </div>
                </div>
            </div>
        );
    }
}

export default EventCard;