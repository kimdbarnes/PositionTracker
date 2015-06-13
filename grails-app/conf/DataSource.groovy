dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
       dataSource {
	     pooled = true
	     driverClassName = "com.mysql.jdbc.Driver"
	     username = "positionUser"
	     password = "Iluvsoftware!"
	     dbCreate = "update"
	     url = "jdbc:mysql://localhost/positionTracker"
             logSql = false
       }
/*
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            //dbCreate = "create" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:file:devDB"
        }
*/
    }
    test {
       dataSource {
	     pooled = true
	     driverClassName = "com.mysql.jdbc.Driver"
	     username = "positionUser"
	     password = "Iluvsoftware!"
	     dbCreate = "update"
	     url = "jdbc:mysql://localhost/positionTracker"
        }
    }
    production {
       dataSource {
	     pooled = true
	     driverClassName = "com.mysql.jdbc.Driver"
	     username = "positiontracker"
	     password = "positiontracker5232"
	     dbCreate = "update"
	     url = "jdbc:mysql://positiontracker.kgbinternet.com:3307/positiontracker"
          properties 
          { 
            maxActive = 50 
            maxIdle = 25 
            minIdle = 1 
            initialSize = 1 
            minEvictableIdleTimeMillis = 60000 
            timeBetweenEvictionRunsMillis = 60000 
            numTestsPerEvictionRun = 3 
            maxWait = 10000 

            testOnBorrow = true 
            testWhileIdle = true 
            testOnReturn = false 

            validationQuery = "SELECT 1" 
         } 
       }
    }
}
