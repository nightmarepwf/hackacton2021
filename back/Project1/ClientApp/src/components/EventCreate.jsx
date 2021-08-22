import React, {Component} from 'react'

class EventCreate extends Component {
    state = {
        title: "",
        desc: "",
        eventDate: "",
        tagName: "",
        tags: []
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

    deleteTag = (id) => {
        this.setState(prevState => ({
            ...prevState,
            tags: prevState.tags.filter(item => {
                if(item.id !== id) {
                    return item
                }
            })
        }))
    }

    render() {
        return (
            <div>
                <p><label>Название <input name="title" type="text" value={this.state.title}
                                          onChange={this.inputHandler}/></label></p>
                <p><label>Описание <input name="desc" value={this.state.desc} onChange={this.inputHandler}/></label></p>
                <p><label>Дата <input type="date" name="eventDate" onChange={this.inputHandler} /></label></p>
                <div>
                    <span>Теги</span>
                    <div><label>Добавить тег <input name="tagName" value={this.state.tagName}
                                                    onChange={this.inputHandler}/></label>
                        <button onClick={this.addTag}>Добавить тег</button>
                    </div>
                    {this.state.tags.map(tag => (<div key={tag.id}>{tag.tagName}
                        <button onClick={() => this.deleteTag(tag.id)}>x</button>
                    </div>))}
                </div>
                <div>
                    <button onClick={this.props.backButtonHandler}>Назад к списку</button>
                    <button onClick={() => this.props.toBloggersSelect(this.state)}>Далее</button>
                </div>
            </div>
        );
    }
}

export default EventCreate;