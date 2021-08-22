import {Redirect, Route, Switch} from "react-router-dom";
import {EventCard, EventsPageBlogger, ProfilePage} from "../pages";

const BloggerRouter = () => {
    return (
        <Switch>
            <Route path="/" exact>
                <EventsPageBlogger/>
            </Route>
            <Route path="/event/:id" exact>
                <EventCard/>
            </Route>
            <Route path="/profile" exact>
                <ProfilePage/>
            </Route>
            <Redirect to="/"/>
        </Switch>
    )
}

export default BloggerRouter