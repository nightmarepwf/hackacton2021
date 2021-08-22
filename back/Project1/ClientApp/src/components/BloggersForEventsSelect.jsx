import React, {Component} from 'react';
import $api from "../http";

class BloggersForEventsSelect extends Component {
    state = {
        isLoading: false,
        bloggers: []
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    async componentDidMount() {
        this.setLoading(true);
        const response = await $api.get('Blogers');
        this.setState(prevState => ({
            ...prevState, bloggers: response.data
        }))
        this.setLoading(false)
    }

    checkboxHandler = (e) => {
        let newBloggersArray = [...this.state.bloggers];
        newBloggersArray = newBloggersArray.map(item => {
            if (String(item.ID) === String(e.target.name)) {
                item.checked = e.target.checked
                return item
            }
            return item
        })
        this.setState(prevState => ({
            ...prevState, bloggers: newBloggersArray
        }))
    }

    render() {
        if (this.state.isLoading) {
            return (<div>Список блоггеров загружается</div>)
        }
        return (
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>Отметка о участии</th>
                        <th>Имя</th>
                        <th>Логин instagram</th>
                        <th>Рейтинг</th>
                        <th>Email</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.bloggers.map(blogger => (
                        <tr key={blogger.ID}>
                            <td><input type="checkbox" name={blogger.ID} value={blogger.checked}
                                       onChange={(e) => this.checkboxHandler(e)}/></td>
                            <td>{blogger.u_name}</td>
                            <td>{blogger.instagram}</td>
                            <td>{blogger.rating}</td>
                            <td>{blogger.email}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <button onClick={() => {
                    this.props.setEventCreated(this.state.bloggers.filter(item => item.checked))
                }}>Создать событие</button>
            </div>
        );
    }
}

export default BloggersForEventsSelect;