### Reference

- https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/


### In Postman
- Make a POST request ```http://localhost:8080/graphql```
- Select GraphQL for type of Body
```
{
    bookById(id: "book-1") {
        id
        name
        pageCount
        author {
            lastName
            firstName
        }
    }
}
```
- Output:
```
{
    "data": {
        "bookById": {
            "id": "book-1",
            "name": "Harry Potter and the Philosopher's Stone",
            "pageCount": 223,
            "author": {
                "lastName": "Rowling",
                "firstName": "Joanne"
            }
        }
    }
}
```