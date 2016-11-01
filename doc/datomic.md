# Introduction to Datomic

![alt text](http://www.datomic.com/uploads/3/5/9/7/3597326/_119999_orig.jpg "Datomic")

Datomic helps a lot as intermediary cache between Application and persistent storage, having 5 unique benefits for cloud:

1. Scalability: it is out of the box, just add a new application to existing transactor and it will share the same data as existing one.

2. Support different database systems as persistent storage. Before Take5People was built on SQL Server database, which is still considered as a good business intelligence solution, such as performing analysis and reporting, but has two major disadvantages: license costs and resource hungry. What Datomic can do with 4Gb of memory, SQL Server will require at least 32GB for the same performance (new memory optimized tables since 2016)

3. Historical Data (immutable data): Before Take5People for every entity created main table and historical data two tables, which connected with onInsert triggers: when data to be inserted in one of the tables, it will be automatically inserted in another. This leads to various infinite loops during integration or system operation. Datomic data immutable by definition, which drastically improves debugging and developing time. Developers can look at any change made to the data and quickly spot corresponding code error.

4. Lucene search: the best full text search out of the box inside Datomic included.

5. Clojure friendly: Take5People cloud is built on Clojure stack and EDN language is natural language for Clojure and Datomic
