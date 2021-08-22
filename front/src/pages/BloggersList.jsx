import React, {Component} from 'react';
import {AddBloggerFrame, BloggersListFrame, Spinner} from "../components";
import $api from "../http";

class BloggersList extends Component {
    state = {
        isLoading: false,
        bloggers: [],
        frame: <BloggersListFrame bloggers={[]} toAddBloggerFrame={this.toAddBloggerFrame} pageLoading={true}
                                  setStars={this.setStars}/>
    }

    setLoading(bool) {
        this.setState(prevState => ({
            ...prevState, isLoading: bool
        }))
    }

    toAddBloggerFrame = (bloggers) => {
        this.setState(prevState => ({
            ...prevState, frame: <AddBloggerFrame toBloggersList={this.toBloggersList} backToBloggerList={this.backToBloggersList}/>,
            [bloggers]: bloggers
        }))
    }

    backToBloggersList = () => {
        this.setState(prevState => ({
            ...prevState,
            bloggers: prevState.bloggers,
            frame: <BloggersListFrame bloggers={prevState.bloggers} toAddBloggerFrame={this.toAddBloggerFrame}
                                      pageLoading={this.state.isLoading} setStars={this.setStars}/>
        }))
    }

    toBloggersList = (newBloggers) => {
        const bloggers = [];
        newBloggers.data.old_blogers.map(item => bloggers.push({
            ID: item.ID,
            u_name: item.u_name,
            u_soname: item.u_soname,
            email: item.email,
            instagram: item.instagram,
            rating: item.rating
        }))
        this.setState(prevState => ({
            ...prevState,
            bloggers,
            frame: <BloggersListFrame bloggers={bloggers} toAddBloggerFrame={this.toAddBloggerFrame}
                                      pageLoading={this.state.isLoading} setStars={this.setStars}/>
        }))
    }

    setStars = async (id, rating) => {
        const res = await $api.post('Rating', JSON.stringify({
            id,
            rating
        }), {headers: {"Content-Type": "text/plain"}})
        const blogger = res.data[0]
        let newBloggers = [...this.state.bloggers];
        newBloggers = newBloggers.map(item => {
            if (item.ID === id) {
                return blogger
            }
            return item
        })
        this.setState(prevState => ({
                ...prevState, bloggers: newBloggers,
                frame: <BloggersListFrame bloggers={newBloggers} toAddBloggerFrame={this.toAddBloggerFrame}
                                          pageLoading={this.state.isLoading} setStars={this.setStars}/>
            })
        )
    }

    async loadBloggers() {
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
        return bloggers
    }

    async componentDidMount() {
        this.setLoading(true)
        const bloggers = await this.loadBloggers();
        this.setState(prevState => ({
            ...prevState,
            bloggers,
            frame: <BloggersListFrame bloggers={bloggers} toAddBloggerFrame={this.toAddBloggerFrame}
                                      pageLoading={this.state.isLoading} setStars={this.setStars}/>
        }))
        this.setLoading(false)
    }

    render() {
        if (this.state.isLoading) {
            return <Spinner/>
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