import styles from './Layout.module.css'
import Logo from './Logo.png'
import {HeaderLink} from "./index";

const Layout = ({children}) => {
    const links = [
        {
            link: "/",
            title: "События"
        },
        {
            link: "/bloggers",
            title: "Блоггеры"
        },
        {
            link: "/content",
            title: "Контент"
        }]

    return (
        <>
            <header className={styles.header}>
                <img src={Logo} style={{marginRight: 20}}/>
                {links.map(item => (<HeaderLink title={item.title} link={item.link}/>))}
            </header>
            <main className={styles.main}>{children}</main>
        </>

    )
}

export default Layout