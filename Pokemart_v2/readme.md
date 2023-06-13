# REQUIREMENTS

## Angular

- Forms (either reactive or template driven)
- Use GET, POST, PUT, DELETE (3 or more) to communicate between frontend and backend
- Single Page Application (client side routing) with a minimum of 4 views
- Abstract common functionalities into Services
- Include an application manifest

## Spring Boot

- Use of POST to handle either x-www-form-urlencoded and/or JSON and/or multipart payload
- Making HTTP request to external RESTful API
- Parameterized routes
- Query string
- Must support more than 1 user

## Database

- Must use MySQL
- Modeling data relationship: 1 to 1, 1 to many
- Demonstrate data integrity and consistency when updating multiple tables
- Must use another database type eg. key/value, blob, graph, document

## Deployment

The application that you have developed must be publicly accessible You can deploy your application either as 2 separate deployments, frontend and backend or as a single deployment where the frontend is served from the backend. Applications can be deployed to Railway or any equivalent cloud PaaS platform like Heroku, AppEngine, etc. If Angular is deployed separately, they can be deployed to static web hosting sites or JAM platforms like Vercel, Cloudflare or serving it from your hosted web server. Note: you cannot use Github pages for hosting your application All databases must be deployed to the ‘cloud’. They can be deployed as VMs in public cloud or using managed database services

## Backend Optional Requirements

- Use web sockets (8pts)
- Integrate with any API that requires OAuth2 authentication (6pts)
- Integrate with Google calendar or Drive (6pts)
- Bots eg Telegram, Slack, (6pts)
- Firebase web notification with frontend web notification (10pts)
- Include simple AI into your application. Must be model based (not a series of if/then/else) or use 3rd party AI service (5pts)
- Using a graph databases eg. Neo4j (6pts)
- Sending email (5pts)
- Use Spring Boot security with JWT to authenticate and authorize Angular request (5pts)
- Integrate with Ethereum’s smart contract. You must also write the smart contract with Solidity (12pts)
- Ingesting and processing messages from a queue eg. Kafka, Rabbit, etc (6pts)
- Integrating with payment gateway (8pts)

## Frontend Optional Requirements

- Use any Javascript/Typescript game framework eg. Phaser3, LittleJS, Kaboom, etc. (6pts)
- Bundle Angular application as iOS or Android application with hybrid app tools like Capacitor, Cordova, NativeScript (8pts)
- Use map eg Google Map (4pts)
- Use a UI component framework - eg. ng-bootstrap, Material, PrimeNG (4pts)
- Use state management libraries like Akita, NgRx, NGSX, etc (6pts)
- Adding a service worker to precache application assets (4pts)

## Deployment Optional Requirements

- On a server (virtual machine) running on any public cloud. (4pts)
- Containerize your application and deploy into a Kubernetes cluster (10pts)
- Containerized your Angular and Spring Boot application (3pts)
- Apply a domain name and configure your application to use the domain name (3pts)
- Use Github actions for continuous build and continuous deployment to automatically build and deploy your application (6pts)
