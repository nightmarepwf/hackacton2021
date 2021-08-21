import {NavLink} from "react-router-dom";

const EventsListManager = ({events}) => {
    return (
        <div>
            {events.map(item => (
                <div style={{display: "flex", flexDirection: "column", borderStyle: "solid", marginBottom: 20}} key={item.id}>
                    <span>{item.title}</span>
                    <span>{item.event_description}</span>
                    <span>{new Date(item.event_date).toLocaleString()}</span>
                    {/*<span>{item.bloggers.length} участников</span>*/}
                    <NavLink to={`/event/${item.id}`} exact>Подробнее</NavLink>
                </div>
            ))}
        </div>
    )
}

export default EventsListManager