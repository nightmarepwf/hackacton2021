import React, {Component} from 'react';

class BloggersForEventsSelect extends Component {
    state = {
        isLoading: false,
        bloggers: []
    }

    bloggers = Array(45).fill({
        id: Date.now(),
        name: "Очередной инстаблоггер",
        login: "@testUser",
        rating: 7.31,
        email: "test@mail.ru",
        checked: false
    }).map(item => {
        return {...item, id: Date.now() + Math.floor(Math.random() * (10000))}
    })

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    componentDidMount() {
        this.setLoading(true);
        this.setState(prevState => ({
            ...prevState, bloggers: this.bloggers
        }))
        this.setLoading(false)
    }

    checkboxHandler = (e) => {
        let newBloggersArray = [...this.state.bloggers];
        newBloggersArray = newBloggersArray.map(item => {
            if (String(item.id) === String(e.target.name)) {
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
                        <tr key={blogger.id}>
                            <td><input type="checkbox" name={blogger.id} value={blogger.checked}
                                       onChange={(e) => this.checkboxHandler(e)}/></td>
                            <td>{blogger.name}</td>
                            <td>{blogger.login}</td>
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