import React, {Component} from 'react';
import $api from "../http";
import Spinner from "./Spinner";

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
        const bloggers = [];
        response.data.old_blogers.map(item => bloggers.push({
            ID: item.ID,
            u_name: item.u_name,
            u_soname: item.u_soname,
            email: item.email,
            instagram: item.instagram,
            rating: item.rating
        }))
        // response.data.new_blogers.users.map(item => bloggers.push({
        //     ID: Math.random() * 10,
        //     full_name:item.user.full_name,
        //     email: null,
        //     instagram: item.user.username,
        //     rating: null
        // }));
        console.log(bloggers)
        this.setState(prevState => ({
            ...prevState, bloggers
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
            return <Spinner/>
        }
        return (
            <div>
                <h4 className="pageSubTitle">Выберите блоггеров, которых хотите пригласить</h4>
                <table>
                    <thead>
                    <tr>
                        <th>Пригласить</th>
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
                            <td>{blogger.u_name && blogger.u_soname ? `${blogger.u_name} ${blogger.u_soname}` : blogger.full_name}</td>
                            <td>{blogger.instagram}</td>
                            <td>{blogger.rating ? blogger.rating : "Новый блоггер"}</td>
                            <td>{blogger.email ? blogger.email : "Нет данных"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <button className="primaryButton" style={{marginTop: 20}} onClick={() => {
                    this.props.setEventCreated(this.state.bloggers.filter(item => item.checked))
                }}>Создать событие
                </button>
            </div>
        );
    }
}

export default BloggersForEventsSelect;