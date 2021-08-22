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
        }]

    return (
        <>
            <header className={styles.header}>
                <img src={Logo} style={{marginRight: 20}}/>
                {links.map((item, index) => (<HeaderLink key={index} title={item.title} link={item.link}/>))}
            </header>
            <main className={styles.main}>{children}</main>
        </>

    )
}

export default Layout