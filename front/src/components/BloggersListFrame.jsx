import React from "react";

const BloggersListFrame = ({bloggers}) => {
    return (
        bloggers.map(item => {
                return (
                    <pre>{JSON.stringify(item.snippet)}</pre>
                )
            }
        )
    )
}

export default BloggersListFrame