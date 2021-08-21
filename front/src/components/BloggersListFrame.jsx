import React from "react";

const BloggersListFrame = ({bloggers, toAddBloggerFrame, toBlogerList}) => {
    return (
        <>
            <button onClick={toAddBloggerFrame}>Добавить в реестр вручную</button>
            {bloggers.map(item => {
                return (<div key={item.ID} style={{display: "flex", flexDirection: "column", borderStyle: "solid"}}>
                    <span>Фамилия: {item.u_soname}</span>
                    <span>Имя: {item.u_name}</span>
                    <span>Instagram: @{item.instagram}</span>
                    <span>Email: {item.email}</span>
                </div>)
            })}
        </>
    )
}

export default BloggersListFrame