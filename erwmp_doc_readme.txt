##### **Containerizing the Application (Docker)**



* **Architecture**



Frontend (React)

     |

Backend (Spring Boot)

     |

Database (MySQL)





* **Docker will run 3 containers**.



docker-compose

      |

-----------------------------

| frontend | backend | MySQL |

-----------------------------





##### **CI/CD Pipeline (Automatic Build \& Deployment)**



* **Before CI/CD**:



Developer builds manually.



Code → Build → Test → Deploy



Manual process = slow + mistakes.



* **With CI/CD:**



Developer pushes code

      ↓

Automatic build

      ↓

Automatic tests

      ↓

Docker image created

      ↓

Ready for deployment





* **Pipeline workflow:**



Developer pushes code

        ↓

GitHub Actions triggers

        ↓

Code checkout

        ↓

Build Java project

        ↓

    Run tests

       ↓

Build Docker image

       ↓

Push to Docker Hub





#### **Final Production Architecture**



Internet Users

     ↓

   NGINX

     ↓ 

Spring Boot API

     ↓    

   MySQL







#### **Where Cache Fits**

A cache is like a temporary storage for your data so that your application doesn’t have to go back to the database every time.

* **Architecture becomes:**


   Client
 
     │

   Backend

     │

┌─────────────┐

│   Redis     │

└─────────────┘

     │
 
  Database





* **Cache Flow**
  

Request

   ↓

Check Redis

   ↓

If cache exists → return data

    ↓

If not

    ↓

Query Database

    ↓

Save to Redis (10 min TTL)

    ↓

Return response













