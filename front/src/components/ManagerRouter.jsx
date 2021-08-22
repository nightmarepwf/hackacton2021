import {Redirect, Route, Switch} from "react-router-dom";
import {BloggersList, ConfirmPage, ContentPage, EventCard, EventsPageManager} from "../pages";
import Layout from "./Layout";

const ManagerRouter = (props) => {
    return (
        <Switch>
            <Route path="/confirm/:code" exact
                   render={matchProps => (
                       <ConfirmPage
                           {...matchProps}
                           {...props}
                       />
                   )}/>
            <Route path="/" exact>
                <Layout><EventsPageManager/></Layout>
            </Route>
            <Route path="/event/:id" exact
                   render={matchProps => (
                       <Layout>
                           <EventCard
                               {...matchProps}
                               {...props}
                           /></Layout>
                   )}/>
            <Route path="/bloggers" exact>
                <Layout><BloggersList/></Layout>
            </Route>
            <Route path="/content" exact>
                <Layout><ContentPage/></Layout>
            </Route>
            <Redirect to="/"/>
        </Switch>
    )
}

export default ManagerRouter