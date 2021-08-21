import styles from './HeaderLink.module.css'
import {NavLink} from "react-router-dom";

const HeaderLink = ({title, link}) => {
    return <p><NavLink activeClassName={styles.activeLink} className={styles.link} to={link} exact>{title}</NavLink></p>
}

export default HeaderLink