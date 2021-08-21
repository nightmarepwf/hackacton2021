import React, {Component} from 'react';
import {BloggersForEventsSelect, EventCreate, EventsListManager} from "../components";
import $api from "../http";

class EventsPageManager extends Component {

    state = {
        isLoading: false,
        events: [],
        frame: null,
        eventInfo: null,
        bloggersList: []
    }

    createEventButtonHandler = () => {
        this.setState(prevState => ({
            ...prevState, frame: <EventCreate title={this.state.title}
                                              desc={this.state.desc}
                                              eventDate={this.state.eventDate}
                                              inputHandler={this.inputHandler}
                                              backButtonHandler={this.toEventList}
                                              toBloggersSelect={this.toBloggersList}/>
        }))
    }

    inputHandler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    toEventList = () => {
        this.setState(prevState => ({
            ...prevState, frame: <EventsListManager events={this.events}/>
        }))
    }

    toBloggersList = (eventInfo) => {
        this.setState(prevState => ({
            ...prevState,
            eventInfo,
            frame: <BloggersForEventsSelect setEventCreated={this.setEventCreated}/>
        }))
    }

    setEventCreated = async (bloggers) => {
        this.setState(prevState => ({
            ...prevState, frame: <EventsListManager events={this.state.events}/>,
            bloggersList: bloggers
        }))
        this.setLoading(true);
        const event = await $api.post("Events", JSON.stringify({
            title: this.state.eventInfo.title,
            event_description: this.state.eventInfo.desc,
            event_date: this.state.eventInfo.eventDate,
            blogers: bloggers,
            tags: this.state.eventInfo.tags
        }), {headers: {"Content-Type": "text/plain"}});
        this.setLoading(false)
        console.log(event.data)
        this.setState(prevState => ({
            ...prevState, events: event.data, frame: <EventsListManager events={event.data}/>
        }))
        return event.data
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    sendEvent = async () => {
        this.setLoading(true);
        const event = await $api.post("Events", JSON.stringify({
            title: this.state.eventInfo.title,
            event_description: this.state.eventInfo.desc,
            event_date: this.state.eventInfo.eventDate,
            blogers: this.state.bloggersList,
            tags: this.state.eventInfo.tags
        }), {headers: {"Content-Type": "text/plain"}});
        this.setLoading(false)
        return event.data
    }

    getEvents = async () => {
        this.setLoading(true);
        const event = await $api.get("Events");
        this.setLoading(false)
        return event.data
    }

    async componentDidMount() {
        this.setLoading(true)
        const events = await this.getEvents()
        this.setState(prevState => ({
            ...prevState,
            events,
            frame: <EventsListManager events={events}/>
        }))
        this.setLoading(false)
    }

    render() {
        if (this.state.isLoading) {
            return <div>Загрузка</div>
        }
        return (
            <div>
                <h3 className="pageTitle">Предстоящие события</h3>
                <button className="primaryButton" style={{marginBottom: 20}} onClick={this.createEventButtonHandler}>Создать событие</button>
                {this.state.frame}
            </div>
        );
    }
}

export default EventsPageManager;