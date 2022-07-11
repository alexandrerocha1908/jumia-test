# jumia-test
Jumia test

This is my test to continue the process for a developer job in Jumia.

First, I would like to apologize because I didn't dockerized my solution. I was getting an error in docker compose, and
was running out of time. So I decided to submit the way that it is.

My solution was to do two microservices, one to import the batches that the ERP is sending and based in this batch the
microservice will update the database (if the stock is different) via a scheduler task. The second microservice works like the backend of the ecommerce
frontend. For example, a customer want to list products, so this microservice will call the first microservice to get the
list of products.

I developed in Spring (Java 11), the database is mysql 8. The conversation between the services is via Rest.
I presumed that the list of products won't change in a day, so I cached the list and the search call.

Instructions to run it:
To run the project we will need to install the maven, the java and the mysql.
In the root project folder, run the command mvn clean install (to build it).
Then in the ecommerce-catalog folder run mvn spring-boot:run (the service will run in 8081 port)
Then in the ecommerce-checkout folder run mvn spring-boot:run (the service will run in 8080 port)
With the ecommerce catalog running we can, at any time, paste a csv file with the products in the folder
/ecommerce-catalog/target/classes/toImport , doing this the scheduler will see that we have more products to update in the db.

Thanks for the opportunity.