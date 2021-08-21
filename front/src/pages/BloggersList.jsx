import React, {Component} from 'react';
import {AddBloggerFrame, BloggersListFrame} from "../components";
import $api from "../http";

class BloggersList extends Component {
    state = {
        isLoading: false,
        bloggers: [],
        frame: <BloggersListFrame bloggers={[]} />
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    toAddBloggerFrame = () => {
        this.setState(prevState => ({
            ...prevState, frame: <AddBloggerFrame />
        }))
    }

    toBloggersList = () => {
        this.setState(prevState => ({
            ...prevState, frame: <BloggersListFrame bloggers={[]} />
        }))
    }

    async loadBloggers() {
        const response = await $api.get('Blogers');
        return response.data;
    }

    async componentDidMount() {
        const bloggers = await this.loadBloggers();
        console.log(bloggers)
        this.setState(prevState => ({
            ...prevState, bloggers: []
        }))
    }

    render() {
        return (
            <>
                <div>
                    Список блоггеров
                </div>
                <hr />
                <button onClick={this.toAddBloggerFrame}>Добавить в реестр вручную</button>
                {this.state.frame}
            </>
        );
    }
}

export default BloggersList;