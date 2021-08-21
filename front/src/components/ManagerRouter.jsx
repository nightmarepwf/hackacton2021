import {Redirect, Route, Switch} from "react-router-dom";
import {BloggersList, ContentPage, EventCard, EventsPageManager} from "../pages";

const ManagerRouter = () => {
  return (
      <Switch>
          <Route path="/" exact>
              <EventsPageManager/>
          </Route>
          <Route path="/event/:id" exact>
              <EventCard/>
          </Route>
          <Route path="/bloggers" exact>
              <BloggersList/>
          </Route>
          <Route path="/content" exact>
              <ContentPage/>
          </Route>
          <Redirect to="/"/>
      </Switch>
  )
}

export default ManagerRouter