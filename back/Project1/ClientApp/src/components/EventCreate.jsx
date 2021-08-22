import React, {Component} from 'react'

class EventCreate extends Component {
    state = {
        title: "",
        desc: "",
        eventDate: "",
        tagName: "",
        mentionName: "",
        tags: [],
        mentions: []
    }

    inputHandler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    addTag = () => {
        const tags = [...this.state.tags]
        tags.push({id: Date.now(), tagName: this.state.tagName})
        this.setState(prevState => ({
            ...prevState,
            tags,
            tagName: ""
        }))
    }

    addMention = () => {
        const mentions = [...this.state.mentions]
        mentions.push({id: Date.now(), mentionName: this.state.mentionName})
        this.setState(prevState => ({
            ...prevState,
            mentions,
            mentionName: ""
        }))
    }

    deleteTag = (id) => {
        this.setState(prevState => ({
            ...prevState,
            tags: prevState.tags.filter(item => {
                if (item.id !== id) {
                    return item
                }
            })
        }))
    }

    deleteMention = (id) => {
        this.setState(prevState => ({
            ...prevState,
            mentions: prevState.mentions.filter(item => {
                if (item.id !== id) {
                    return item
                }
            })
        }))
    }

    render() {
        return (
            <div>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Название</label>
                    <input name="title"
                           type="text"
                           value={this.state.title}
                           style={{
                               padding: "5px 10px",
                               color: "#023e8a",
                               fontSize: "1.1rem",
                               borderColor: "#0077b6",
                               borderRadius: 5
                           }}
                           onChange={this.inputHandler}/>
                </p>
                <p style={{display: "flex", flexDirection: "column"}}>
                    <label style={{color: "#0077b6", fontWeight: 600}}>Описание</label>
                    <textarea name="desc"
                              value={this.state.desc}
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
                    <label style={{color: "#0077b6", fontWeight: 600}}>Дата</label>
                    <input type="date"
                           name="eventDate"
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
                <div>
                    <div>
                        <span style={{color: "#0077b6", fontWeight: 600}}>Теги</span>
                        <div><input name="tagName" value={this.state.tagName}
                                    onChange={this.inputHandler} style={{
                            padding: "5px 10px",
                            color: "#023e8a",
                            fontSize: "1.1rem",
                            borderColor: "#0077b6",
                            borderRadius: 5,
                            marginRight: 15
                        }}/>
                            <button className="primaryButton" onClick={this.addTag}>Добавить тег</button>
                        </div>
                        <div style={{display: "flex", flexDirection: "row", marginTop: 10}}>
                            {this.state.tags.map(tag => (<div className="tag" key={tag.id}><span>{tag.tagName}</span>
                                <button onClick={() => this.deleteTag(tag.id)}>x</button>
                            </div>))}
                        </div>
                    </div>
                    <div>
                        <span style={{color: "#0077b6", fontWeight: 600}}>Упоминания</span>
                        <div><input name="mentionName" value={this.state.mentionName}
                                    onChange={this.inputHandler} style={{
                            padding: "5px 10px",
                            color: "#023e8a",
                            fontSize: "1.1rem",
                            borderColor: "#0077b6",
                            borderRadius: 5,
                            marginRight: 15
                        }}/>
                            <button className="primaryButton" onClick={this.addMention}>Добавить упоминание</button>
                        </div>
                        <div style={{display: "flex", flexDirection: "row", marginTop: 10}}>
                            {this.state.mentions.map(mention => (<div className="tag" key={mention.id}><span>{mention.mentionName}</span>
                                <button onClick={() => this.deleteMention(mention.id)}>x</button>
                            </div>))}
                        </div>
                    </div>
                </div>
                <div style={{display: "flex", flexDirection: "row", marginTop: 20}}>
                    <button className="primaryButton" style={{marginRight: 10}} onClick={this.props.backButtonHandler}>Назад к списку</button>
                    <button className="primaryButton" onClick={() => {this.props.toBloggersSelect(this.state)}}>Далее</button>
                </div>
            </div>
        );
    }
}

export default EventCreate;