import {NavLink} from "react-router-dom";
import React from "react";

const EventsListManager = ({events, createEventButtonHandler}) => {
    return (
        <div>
            <button className="primaryButton" style={{marginBottom: 20}} onClick={createEventButtonHandler}>Создать событие</button>
            {events.map(item => (
                <div style={{borderColor: "#0077b6", borderWidth: "2px", borderRadius: 5, padding: "10px", borderStyle: "solid", display: "flex", flexDirection: "row", marginBottom: 10}}>
                    <div style={{marginRight: 20}}>
                        <img src="https://dt.samregion.ru/wp-content/uploads/sites/40/2021/08/ubqpa_zrx7y.jpg" style={{width: 400, height: 260}}/>
                    </div>
                    <div style={{display: "flex", flexDirection: "column" }} key={item.ID}>
                        <NavLink style={{fontSize: "1.3rem", fontWeight: 600, marginBottom: 10, textDecoration: "none"}} to={`/event/${item.ID}`} exact>{item.title}</NavLink>
                        <span style={{fontSize: "1.1rem", marginBottom: 10}}><b>Описание: </b>{item.event_description}</span>
                        <span style={{fontSize: "1.2rem", fontWeight: 500, marginBottom: 10}}><b>Дата: </b>{new Date(item.event_date).toLocaleString()}</span>
                        <span style={{fontSize: "1.2rem", fontWeight: 500, marginBottom: 10}}><b>Участников: </b>{item.count1}</span>
                        {/*<span>{item.bloggers.length} участников</span>*/}
                        <NavLink style={{color: "#023e8a", fontSize: "1.2rem", fontWeight: 600, textDecoration: "none"}} to={`/event/${item.ID}`} exact>Подробнее</NavLink>
                    </div>
                </div>

            ))}
        </div>
    )
}

export default EventsListManager