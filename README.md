# SimpleTraining1Web

## Required
1) Need install MySql database. schema name="test", username="root", password="123abcABC"
2) Need install Redis.
3) Need install ElasticSearch (version >=6.x, 6.4.2 is recommended)
4) This program need 7001,8080,8081,8082,8083,3306,6379,9200,9300 ports
5) JDK8
6) Gradle (version 4.7 is recommended)

## DB installation
### Windows
        In windows, we suggest use free installation version Mysql. 
        1) download the both files in \\172.17.3.100\Training\Microservice\Phase1-JavaBase\Examing and unzip in your local
        2) edit POC.txt in MySQLData. change "basedir" & "datadir" base on your path
        3) add "${basePath}\mysql-5.7.20-winx64\bin" into your environment variable. variable name is "path".
        4) Use MySQLData/startMySQL.bat to start mysql
        
        The "test" schema default password is "123abcABC" 
        
### Linux
        On Linux, we suggest use docker to install mysql
        1) Install docker-ce
        2) Execute command:
            docker pull mysql
            docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123abcABC -d mysql
        3) If you want to restore test data from "backup.sql" file, do the following:
        Download "backup.sql" from \\172.17.3.100\upload\TerrenceWei/
        Execute command below:
            docker exec -it mysql bash
            mysql -uroot -p123abcABC
            create database test;
            exit;
            exit;
            cat backup.sql | docker exec -i mysql /usr/bin/mysql -uroot -p123abcABC test
## Redis installation
### Windows
        1) Install docker for windows
        2) Execute command below in PowerShell
### Linux
        1) Install docker-ce
        2) Execute command below in ternimal
### Command
        docker pull redis
        docker run --name redis -p 6379:6379 -d redis
## ElasticSearch installation
### Windows
        1) Install docker for windows
        2) Execute command below in PowerShell
### Linux
        1) Install docker-ce
        2) Execute command below in ternimal
### Command
        docker pull docker.elastic.co/elasticsearch/elasticsearch:6.4.2
        docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.4.2
## How to running
        First of all, need add `mylocal` with your PC ip address to your host files
        e.g. mylocal 172.17.118.200
        NOTICE: this should be your PC ip, not 127.0.0.1
        Then there were two ways to running:
            1) All in one via docker-compose
            2) One by one via gradle
### Running services module all in one via docker-compose
        Install docker-compose
        On Windows, TBD
        On Linux, cd to projec root directory
            change MYLOCAL_IP in startAll.sh
            execute command below:
                sh startAll.sh
        Wait until all the servies is up
        To stop all the services, cd to projec root directory, execute command below:
            sh stopAll.sh
### Running services module one by one
#### Run discovery service module
        cd to /discovery-service
        On Windows, open PowerShell and execute command:
            gradle clean build bootRun
        On Linux, run the shell script:
            sh start.sh
#### Run catalog service module
        cd to /catalog-service
        On Windows, open PowerShell and execute command:
            gradle clean build bootRun
        On Linux, run the shell script:
            sh start.sh
#### Run price service module
        cd to /pricing-service
        On Windows, open PowerShell and execute command:
            gradle clean build bootRun
        On Linux, run the shell script:
            sh start.sh
#### Run inventory service module
        cd to /inventory-service
        On Windows, open PowerShell and execute command:
            gradle clean build bootRun
        On Linux, run the shell script:
            sh start.sh
#### Run this web module
        cd to /web-storefront
        On Windows, open PowerShell and execute command:
            gradle clean build bootRun
        On Linux, run the shell script:
            sh start.sh
### Inital temporary data
#### Inital MySQL data

        If we use above free installation version Mysql. we do not need initial data.
        if we use ourself's mysql:
            1) Start all services
            2) Open browswer and enter the url to init data one by one:
                http://127.0.0.1:8083/api/category/initData
                http://127.0.0.1:8083/api/product/initData
                http://127.0.0.1:8081/api/price/initData
                http://127.0.0.1:8082/api/inventory/initData
#### Inital ElasticSearch indexing data
        1) Start catalog-service module
        2) Open browswer and enter the url to do indexing:
            2.1) Baseline indexing for all data:
                http://127.0.0.1:8083/api/product/elasticsearch/baselineIndexing
            2.2) Partial indexing:
                http://127.0.0.1:8083/api/product/elasticsearch/partialIndexing/<offset>/<size>
            'offset' and 'size' are mapping to SQL 'limit :offset, :size' to query data from DB.
            e.g. Indexing first 1000 record:
                 http://127.0.0.1:8083/api/product/elasticsearch/partialIndexing/0/1000
                 Indexing 1001~5000 record:
                 http://127.0.0.1:8083/api/product/elasticsearch/partialIndexing/1000/4000
        Indexing status will output in service log like:
                    2018-10-18 14:14:09.058  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex indexed indexBatchSize{} record
                    2018-10-18 14:14:09.183  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex [27.61]% precent finished, remaining [1589053] record
                    2018-10-18 14:14:09.384  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex indexed indexBatchSize{} record
                    2018-10-18 14:14:09.516  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex [27.65]% precent finished, remaining [1588053] record
                    2018-10-18 14:14:09.691  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex indexed indexBatchSize{} record
                    2018-10-18 14:14:09.825  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex [27.70]% precent finished, remaining [1587053] record
                    2018-10-18 14:14:10.008  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex indexed indexBatchSize{} record
                    2018-10-18 14:14:10.132  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex [27.74]% precent finished, remaining [1586053] record
                    2018-10-18 14:14:10.328  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex indexed indexBatchSize{} record
                    2018-10-18 14:14:10.857  INFO 32534 --- [nio-8083-exec-1] c.a.m.t.demo1.service.ESIndexService     : bulkIndex [27.79]% precent finished, remainin
        Wait until 100% percent
        My testing result on my local PC, indexing about 3.1 million records will cost about 8 minutes.(batch size is 10000)
        
### Access
        http://localhost:8080/login

 


Training report
=====================================================
        Input all what you changed content blow.

## Mandatory items

### JDK8 Lambda Expression
        
        Update/Create methods:
            /catalog-service/
                com.aaxis.microservice.training.demo1.service.ProductService.findProductsInPLP
                com.aaxis.microservice.training.demo1.service.ProductService.findProductsInPLP_legacy
                com.aaxis.microservice.training.demo1.service.ProductService.queryProductByRawSQL
                com.aaxis.microservice.training.demo1.service.ProductService.searchProducts
                com.aaxis.microservice.training.demo1.service.ProductService.addPriceAndInventory
            /web-storefront/
                com.aaxis.microservice.training.demo1.controller.RestUserController.doRegist
        
        
### Spring Validation

        Add annotation to /web-storefront/com.aaxis.microservice.training.demo1.domain.User:
            @NotBlank
            @Email(message = "User name should be email address")
            @Length(min = 7, max = 12, message = "Password should be 7 to 12 characters")
            @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\S]+$", message = "Password should be a mix of lower and uppercase characters as well as numbers")
            
        Invoke validation during login/regist:
            /web-storefront/
                com.aaxis.microservice.training.demo1.controller.UserController.doRegist
            Using javax.validation.Validator.validate() to do validation and get error message Set
            Using Spring RedirectAttributes and flash attribute to pass message between redirect and auto expired in session
        
### Springboot Unit Testing
        
        Create testing with @MockBean to just verify the API url and params:
            /web-storefront/
                com.aaxis.microservice.training.demo1.controller.RestProductControllerMockTest
                com.aaxis.microservice.training.demo1.controller.RestUserControllerTest
        Create contract testing via Spring Cloud Contract and Spring Contract Gradle plugin:
            Contracts:
                /inventory-service/
                    contracts/shouldFindInventory.groovy
                /pricing-service/
                    contracts/shouldFindPrice.groovy
            Provider:
                /inventory-service/
                    com.aaxis.microservice.training.demo1.service.ContractVerifierBase
                /pricing-service/
                    com.aaxis.microservice.training.demo1.service.ContractVerifierBase
                The non-abstract test code will be auto generated by Spring Contract Gradle Plugin at:
                    org.springframework.cloud.contract.verifier.tests.ContractVerifierTest
            Comsumer:
                /catalog-service/
                    com.aaxis.microservice.training.demo1.service.ProductControllerTest
        Contract testing guide:
            1) Provider test:
                cd to /pricing-service and /inventory-service
                2.1) Execute test and generate stub jars:
                    gradle clean build test
                2.2) Install stub jars to local maven repository:
                    gradle install -Dmaven.repo.local=your_local_maven_repo_dir
                    e.g.
                    gradle install -Dmaven.repo.local=/home/terrence/.m2/repository
            2) Consumer test:
                cd to /catalog-service
                Execute test:
                    gradle clean build test
            tips: By default, 
                group id is 'test.local', version is '0.1' in /inventory-service/build.gradle and /pricing-service/build.gradle
                artifactId is in /inventory-service/gradle.properties and /pricing-service/gradle.properties
                thus generated stubs are like below:
                mapping json:
                    /inventory-service/build/stubs/META-INF/test.local/inventory-service/0.1/mappings/shouldFindInventory.json
                    /pricing-service/build/stubs/META-INF/test.local/pricing-service/0.1/mappings/shouldFindPrice.json
                stub jars:
                    /inventory-service/build/libs/inventory-service-0.1-stubs.jar
                    /pricing-service/build/libs/pricing-service-0.1-stubs.jar
### Logging
        
        Add @Slf4j to class
        IDE should install Lombok plugin
        Then use static method directly in code like:
            Without params:
                log.info("hello world!");
                log.warn("hello world!");
                log.error("hello world!");
                log.debug("hello world!");
                log.trace("hello world!");
            With params:
                log.info("hello {}! Hi, {}!", "World", "Terrence");
        Config log level:
            application.yml
                logging:
                    level:
                        com:
                          aaxis:
                            microservice:
                              training:
                                demo1:
                                  controller: debug
            Then class under com.aaxis.microservice.training.demo1.controller log level is debug
### Spring Security
        
        Create package: 
            /web-storefront/
                com.aaxis.microservice.training.demo1.security
                    WebMvcConfig: handle view mapping to support jsp and mapping js/css static files
                    WebSecurityConfig: handle web url security
                    UserDetailsImpl: Implements Spring Security user detail interface to hold login user data
                    UserDetailsServiceImpl: Implements Spring Security UserDetailsService to find user by name when user do login 
                    PasswordEncoderImpl: Encode/Decode password, currently just do String equals
                    CustomAuthSuccessHandler: Handle user login success event
                        Currently not Spring OOTB error message handling logic 
                        in method org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler.clearAuthenticationAttributes
                        so can impl the AuthenticationSuccessHandler interface,
                        the extends from SimpleUrlAuthenticationSuccessHandler is uncecessary
                    CustomAuthFailureHandler: Handle user login failure event
                        Same as CustomAuthSuccessHandler, if not using Spring OOTB error message handling logic 
                        in method org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler.saveException,
                        the extends from SimpleUrlAuthenticationFailureHandler is uncecessary
                        Currently using org.springframework.web.servlet.FlashMap to hold and display error message in jsp
                        Because when login success/error, handler will do redirect instead of forward
                        (If doing forward for POST request, 405 exeption will be thrown from Spring Security by OOTB)
                        So need pass error message via different request
                        If using Spring OOTB error message handling logic, will set error message in session
                        In this way the error message scope is expand to session scope
                        This is not the best experience because in storefront once user login failure, error message will be shown always even the user had refresh the page
                        Using org.springframework.web.servlet.FlashMap to temporary save message in session, then pass to next request, then auto expired
                        Thus in storefront, user login, error message will be shown, if user refresh the page, error message will auto disappear and every time the request is different
                    CustomLogoutSuccessHandler: Handle user logout event
        
### Spring Data JPA - MySQL
        
        Add methods to:
            /catalog-service/
                com.aaxis.microservice.training.demo1.dao.ProductDao
        Using Spring JPA query creation mechanism
            Page<Product> findByIdContainingAndNameContaining(String id, String name, Pageable var1);
            
        Using @Query annotation to execute raw SQL
        
        If SQL is simple, can use Pageable as param and pass to methods, then can get Page<T> as output, like:
            @Query(value = "SELECT * FROM product where category_id = :categoryId order by ?#{#pageable}", countQuery = "SELECT count(1) FROM product", nativeQuery = true)
            Page<Product> findProductsByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);
        "?#{#pageable}" is for "Pageable", "countQuery" is for "Page"
        
        If SQL is not simple, pass the Pageable param will get wrong SQL syntax by Spring OOTB, this is what I had met
        
### Spring Data Redis
        
        Create abstract class in /common-module/com.aaxis.microservice.training.demo1.service.RedisCache
        Using StringRedisTemplate to handle redis action
        
        Get data from DB logic will be implemented in methods: 
            /inventory-service/
                com.aaxis.microservice.training.demo1.service.InventoryCache.getById
            /pricing-service/
                com.aaxis.microservice.training.demo1.service.ItemPriceCache.getById
        
### Springboot Application Performance Tuning
        
        1) MySQL side:
            Raw query time cost: 2300ms
                Reason: 
                1. By default strategy, mysql using index of category_id: FK1mtsbur82frn64de7balymq9s
                2. The SQL limit offset is larger when using 'where', 'order by' together, the query is slower
            Improvement step by step:
                1. Using PRIMARY_KEY as index:
                SELECT ... FROM product USE INDEX (`PRIMARY`) ...
                And I also test using 'priority' index, 'category_priority' union index, seems the same effort as using `PRIMARY` index
                So I did not add any new index to project table, just using `PRIMARY` index
                And using 'category_priority' union index, the cost time even increase to 5500ms
                2. Using sub query to query all id via indexing coverage, then inner join other fields via ids
                    SELECT id FROM product ...
                4. Partition: the latest MySQL version 8.0.11 did not support partition, ignore this step(refer to https://dev.mysql.com/doc/refman/8.0/en/partitioning.html)
                5. DB engine: MyISAM reading performance is higher than InnoDB, but did not support transaction
                In this case using MyISAM is better.
                In this case the hibernate DDL auto created table using MyISAM as engine by OOTB, so no more actions needed.
        2) Code level:
                1. To support sub query at code level, because of 'ORDER BY' keywords cannot dynamic passed by @Query annotaion
                So using javax.persistence.EntityManager to create and execute raw query:
                    /catalog-service/
                        com.aaxis.microservice.training.demo1.service.ProductService.queryProductByRawSQL
                2. When offset is large, and order by is not empty
                Using half-offset algorithm to decreate offset less than half, and query by reverse order
                As far as possible to pass the smaller offset to SQL:
                    /catalog-service/
                        com.aaxis.microservice.training.demo1.service.ProductService.findProductsInPLP
        3) Final results:
                Time cost:
                ========================
                        Legacy -> Using PRIMARY_KEY as index
                        2300ms -> 890ms
                ========================
    Using PRIMARY_KEY as index -> Using sub query and half-offset algorithm
                          890ms-> 760ms
                ========================
           discovery, catalog, storefront UP only: 650ms (storefront using hystrix to call price, inventory)
                With price, inventory UP together: 950ms
                With Redis cache price, inventory: 880ms
                ========================
        4) Others not implements thinks:
           1. If primary id is integer type, storefront can record the last search max id, then next page search can start from this max id to avoid filesort(full table scan)
           2. If 'ORDER BY' values is not unique, the thinking#1 may cause data inconsistencies between each page search in mysql because mysql will get data randomly if order by key is the same 
## Optional items
### Decoupling
      Seperate one app structure into multiple app structure:
        /common-module/
        /discovery-service/
        /catalog-service/
        /pricing-service/
        /inventory-service/
        /web-storefront/
        
      Remove some useless routing methods from com.aaxis.microservice.training.demo1.controller.UserController
      Routing logic handle by
        /web-storefront/
            com.aaxis.microservice.training.demo1.security.WebSecurityConfig
            com.aaxis.microservice.training.demo1.security.WebMvcConfig
### Optimize Data Cache Repository
        1) Fallback/Circle-Breaker:
            Add HystrixFactory to each Feign client:
            /common-module/
                com.aaxis.microservice.training.demo1.service.CatalogFeignClient.CatalogHystrixFactory
                com.aaxis.microservice.training.demo1.service.InventoryFeignClient.InventoryHystrixFactory
                com.aaxis.microservice.training.demo1.service.PricingFeignClient.PricingHystrixFactory
        2) Others: TODO
### Spring Cloud
        1) Spring Cloud Service Discovery
            Create new module /discovery-service/ and setup Eureka to do servide discovery/registry
        2) Spring Cloud Zuul/Feign/Ribbon/Hystrix
            Feign/Ribbon/Hystri
                Create Feign client for each service:
                    /common-module/
                        com.aaxis.microservice.training.demo1.service.CatalogFeignClient
                        com.aaxis.microservice.training.demo1.service.InventoryFeignClient
                        com.aaxis.microservice.training.demo1.service.PricingFeignClient
                Change all RestTemplate in code to FeignClient to do API calls between service modules
            Zuul: TODO
        2) Others: TODO
### Spring Security
        TODO
### ElasticSearch

        Using @ConfigurationProperties to get config from application.yml
        Create:
            /catalog-service/
                com.aaxis.microservice.training.demo1.domain.ESProduct
                com.aaxis.microservice.training.demo1.dao.ESProductDao
                com.aaxis.microservice.training.demo1.service.ESIndexService
                com.aaxis.microservice.training.demo1.service.ESProductIndexService
                com.aaxis.microservice.training.demo1.controller.ESProductController
        Create methods to use es when search product:
            /catalog-service/
                com.aaxis.microservice.training.demo1.service.ProductService.searchProducts_es

        Improvements:
            1) When pagination is large:
                    QueryPhaseExecutionException[Result window is too large, from + size must be less than or equal to: [10000]
            Solution: elasticsearch should be used to search context, not doing deep pagination in memory because elasticsearch will load all data into memory when use pagination
            2) When order by String type of field(like 'id'):
                java.lang.IllegalArgumentException: Fielddata is disabled on text fields by default. Set fielddata=true on [id] in order to...
            Solution: elasticsearch should not be used to sort String type of field in memory    
### ELK
        TODO
### Other Optimization Suggestion
    1) Infrastructure:
        1. Using Gradle/YAML instead of Maven/Properties to get modern development experience, and move with the times
        2. Using Docker/Docker-Compose to avoid dev env difference and faster env setup
    2) My did works but not presents in above documents:
        Code:
            1. Rename field names and method names to fix some typo
            2. Add @Column(length = 40) to all domain @Id field to decrease the default index key length(255), otherwise the auto create tables will fail in mysql(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Specified key was too long; max key length is 1000 bytes)
            3. Rename all API url to /api/** to support front-end separation in the furture
            4. Change "break" to "continue" when initData for product if "checkProductExistBeforeAdding" is "true"
            5. Add new query to get max product id when initData in Product service to support initData resume from break-point 
            6. Refactor all env property name to static String
            7. Change all 'forward' action to 'redirect' to keep request stateless
            8. (deperated)Rename some class variable follow ATG project, use 'm' as prefix
            NOTICE: this rule is not suitable with lombok annotaions @Getter/@Setter
            9. lombok annotations:  @Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor annotaion, or using @Data can more simplify annotations above
            Important:
                DO NOT use @Data with @Entity together, will cause OOM by override the hashCode method by @Data when connect to mysql
                NO NOT use lombok annotations with @Entity/rest API, may cause many problem, such as infinite-recursion when being parsed json then cause OOM
                NO NOT use @Getter/@Setter with variables starts with 'm' as prefix
                @Many-to-one may also cause infinite-recursion when being parsed json
        CLI building scripts:
            1. docker-compose.yml, startAll.sh, stopAll.sh, start.sh under each modules, using docker-compose
            These scripts were created by me from another project https://stash.aaxisgroup.net/projects/ATH/repos/springcloud-verification/browse
            But these are just prototypes, because I'm new to Gradle and docker-compose, still need lots of effort for improvements
            Currently, these scripts just can 'work', and low efficiency. Reasons will described below. 
        Others:
            1. Update exist guide in this readme to support the multiple app structure
    2) My things about improvements that not implements in this practice:
        MOST IMPORTANT OF ALL: EVERY PART in this Microservice is easy in, difficult out, Spring or other utils(like elasticsearch) is high encapsulation, if want to reach the production level, still need lots of work to deepin each section
        1. Front-end separation, using node/react as front-end, micro services shoud only provide REST services
        2. REST services module should level up to HATEOAS(https://en.wikipedia.org/wiki/HATEOAS)
        3. This method call is necessary and should not exist:
            /common-module/
                com.aaxis.microservice.training.demo1.service.RedisCache.getClazz
            Should init the Class when RedisCache class is init with Spring @Autowired Spring init bean factories
        4. Redis should save values as List/Set to save memory, not key/value pairs
        5. CLI building scripts improvements: 
            startAll.sh
                Not recreate docker images every time, should onln create docker image once
                Then mount changed app.jar to the exist docker container to save startup time
                Using docmer-compose is not recommaned by Docker offical doc, using docker-compose version 3 instead of 2, and using docker swarm is recommaned.
                Details refer to https://docs.docker.com/compose/compose-file/#resources
        6. Gradle dependence improments, the current gralde building file version is copied from https://stash.aaxisgroup.net/projects/ATH/repos/springcloud-verification/browse
        And simplify by me from 3 files to 2 files: build.gradle and settings.gradle, and the structure in build.gradle is still not good:
            0) currently structure not support complie with specific version
            1) Variable definition of libs is not good, because of not every module need some of the dependence, like libs.springcloud, need more decoupling
            2) Scope in libs is different, some of them are complie but some are runtime, still, need more decoupling
            3) My advice is not using this ext.libs definition structure any more, each subproject should have different dependence, even this seems redundant in build.gradle, but can avoid redundancy in generated jar file
            4) Less gradle file is better. Finally we just need only one gradle file, not 2, its unnecessary and will have bad development experience
        7. Batch indexing for elasticsearch should support partical indexing and resume from break-point
        Should get price and stock from FeignClient in method:
            /catalog-service/
                com.aaxis.microservice.training.demo1.service.ESProductIndexService.queryRecords
        8. Redundant code about search product and search product using elasticsearch
        9. Redundant config in application.yml of each module