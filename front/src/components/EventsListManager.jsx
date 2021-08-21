import {NavLink} from "react-router-dom";

const EventsListManager = ({events}) => {
    return (
        <div>
            {events.map(item => (
                <div style={{display: "flex", flexDirection: "column", borderStyle: "solid", marginBottom: 10, borderColor: "#0077b6", borderWidth: "2px", borderRadius: 5, padding: "10px"}} key={item.id}>
                    <span style={{fontSize: "1.3rem", fontWeight: 500, marginBottom: 10}}>{item.title}</span>
                    <span style={{fontSize: "1.1rem", marginBottom: 10}}><b>Описание: </b>{item.event_description}</span>
                    <span style={{fontSize: "1.2rem", fontWeight: 500, marginBottom: 10}}><b>Дата: </b>{new Date(item.event_date).toLocaleString()}</span>
                    {/*<span>{item.bloggers.length} участников</span>*/}
                    <NavLink style={{color: "#023e8a", fontSize: "1.2rem", fontWeight: 600}} to={`/event/${item.id}`} exact>Подробнее</NavLink>
                </div>
            ))}
        </div>
    )
}

export default EventsListManager