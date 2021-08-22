import React, {Component} from 'react';
import {AddBloggerFrame, BloggersListFrame} from "../components";
import $api from "../http";

class BloggersList extends Component {
    state = {
        isLoading: false,
        bloggers: [],
        frame: <BloggersListFrame bloggers={[]} toAddBloggerFrame={this.toAddBloggerFrame} pageLoading={true}/>
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    toAddBloggerFrame = (bloggers) => {
        this.setState(prevState => ({
            ...prevState, frame: <AddBloggerFrame toBloggersList={this.toBloggersList}/>,
            [bloggers]: bloggers
        }))
    }

    toBloggersList = () => {
        this.setState(prevState => ({
            ...prevState,
            frame: <BloggersListFrame bloggers={this.state.bloggers} toAddBloggerFrame={this.toAddBloggerFrame} pageLoading={this.state.isLoading}/>
        }))
    }

    async loadBloggers() {
        const response = await $api.get('Blogers');
        return response.data;
    }

    async componentDidMount() {
        this.setLoading(true)
        const bloggers = await this.loadBloggers();
        console.log(bloggers)
        this.setState(prevState => ({
            ...prevState,
            bloggers,
            frame: <BloggersListFrame bloggers={bloggers} toAddBloggerFrame={this.toAddBloggerFrame} pageLoading={this.state.isLoading}/>
        }))
        this.setLoading(false)
    }

    render() {
        if (this.state.isLoading) {
            return (<div>Загрузка</div>)
        }
        return (
            <>
                <h3 className="pageTitle">Список блоггеров</h3>

                {this.state.frame}
            </>
        );
    }
}

export default BloggersList;