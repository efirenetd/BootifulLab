### Guide
* To test, run docker compose

    ```docker-compose up```

* Start the service by running ```Lab01RedisApp.java```

* Add new customers by sending a POST request to ```http://localhost:8080/api/v1/customers/customers endpoint```

    ```
    {"name" : "jong",
    "contactName":  "Nsdasda",
    "address" : "XYZ",
    "city" : "QC",
    "postalCode":  "1112",
    "country" : "PH"}
  ```
* Retrieve all the customers by sending a GET request

* Stop the service

    ```docker-compose down```

* Clean up local Volume

    ```docker volume prune```