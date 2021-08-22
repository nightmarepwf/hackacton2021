import React, {Component} from 'react';
import $api from "../http";
import Spinner from "./Spinner";
import Modal from 'react-modal';

class BloggersForEventsSelect extends Component {
    state = {
        isLoading: false,
        bloggers: [],
        show: false,
        modalLoading: true,
        modalInfo: null
    }

    setShow = async (bool, id) => {
        this.setState(prevState => ({
            ...prevState, show: bool
        }))
        if(id) {
            const res = await $api.post('/Instagram', JSON.stringify({user: id}), {headers: {"Content-Type": "text/plain"}});
            console.log(res)
            const modalInfo = {
                FullName: res.Value.FullName,
                Username: res.Value.Username,
                FollowerCount: res.Value.FollowerCount,
                FollowingCount: res.Value.FollowingCount
            }
            this.setState(prevState => ({
                ...prevState, modalInfo, modalLoading: false
            }))
        }
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
                            <td><span onClick={() => this.setShow(true, blogger.instagram)}>{blogger.instagram}</span></td>
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
                <Modal
                    isOpen={this.state.show}
                    onRequestClose={() => this.setShow(false)}
                    contentLabel="Example Modal"
                >
                    <p className="pageSubTitle">Информация о пользователе</p>
                    {this.state.modalLoading ? <p>Загрузка информации</p> : <div>
                        <p>{this.state.modalInfo.FullName}@{this.state.modalInfo.Username}</p>
                    </div>}
                </Modal>
            </div>
        );
    }
}

export default BloggersForEventsSelect;