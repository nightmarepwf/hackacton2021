import React from "react";
import StarRatings from "react-star-ratings";

const BloggersListFrame = ({bloggers, toAddBloggerFrame, toBlogerList, setStars}) => {
    return (
        <>
            <button className="primaryButton" style={{marginBottom: 20}} onClick={toAddBloggerFrame}>Добавить в реестр
                вручную
            </button>
            {bloggers.map(item => {
                return (<div key={item.ID} style={{
                    display: "flex",
                    flexDirection: "row",
                    borderStyle: "solid",
                    borderRadius: 5,
                    borderWidth: 2,
                    marginBottom: 10,
                    padding: 15
                }}>
                    <div className="avatar" style={{
                        display: "flex",
                        flexDirection: "row",
                        justifyContent: "center",
                        alignItems: "center",
                        width: 140,
                        height: 140,
                        borderRadius: 140,
                        background: "#0077b6",
                        marginRight: 30,
                        color: "white",
                        fontSize: 36
                    }}>{item.u_name[0]}</div>
                    <div style={{display: "flex", flexDirection: "column"}}>
                        <span><b>Фамилия: </b>{item.u_soname}</span>
                        <span><b>Имя: </b>{item.u_name}</span>
                        <span><b>Instagram: </b>@{item.instagram}</span>
                        <span><b>Email: </b>{item.email}</span>
                        <p style={{fontWeight: 600, color: "#0077b6"}}>Оцените работу блоггера:</p>
                        <div>
                            <StarRatings
                                rating={item.rating || 0}
                                starRatedColor="blue"
                                changeRating={(newRating) => {setStars(item.ID, newRating)}}
                                numberOfStars={10}
                                starDimension="20px"
                                starSpacing="2px"
                            /></div>
                    </div>
                </div>)
            })}
        </>
    )
}

export default BloggersListFrame