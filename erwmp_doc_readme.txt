##### **Containerizing the Application (Docker)**



* **Architecture**



Frontend (React)

&nbsp;     |

Backend (Spring Boot)

&nbsp;     |

Database (MySQL)





* **Docker will run 3 containers**.



docker-compose

&nbsp;    |

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

&nbsp;       ↓

Automatic build

&nbsp;       ↓

Automatic tests

&nbsp;       ↓

Docker image created

&nbsp;       ↓

Ready for deployment





* **Pipeline workflow:**



Developer pushes code

&nbsp;       ↓

GitHub Actions triggers

&nbsp;       ↓

Code checkout

&nbsp;       ↓

Build Java project

&nbsp;       ↓

Run tests

&nbsp;       ↓

Build Docker image

&nbsp;       ↓

Push to Docker Hub





#### **Final Production Architecture**



Internet Users

&nbsp;     ↓

&nbsp;  NGINX

&nbsp;     ↓ 

Spring Boot API

&nbsp;     ↓    

&nbsp;   MySQL







#### **Where Cache Fits**

A cache is like a temporary storage for your data so that your application doesn’t have to go back to the database every time.

* **Architecture becomes:**



Client

&nbsp; │

&nbsp; ▼

Backend

&nbsp; │

&nbsp;┌─────────────┐

&nbsp;│   Redis     │

&nbsp;└─────────────┘

&nbsp; │

&nbsp; ▼

Database





* **Cache Flow**
  

Request

&nbsp;  ↓

Check Redis

&nbsp;  ↓

If cache exists → return data

&nbsp;  ↓

If not

&nbsp;  ↓

Query Database

&nbsp;  ↓

Save to Redis (10 min TTL)

&nbsp;  ↓

Return response













