# Accomodo
Rule-based system for travel accommodation recommendation.

# Functionalities
- *User Roles*: Supports administrators, accommodation providers, and travelers with specific functionalities for each.
- *Travelers*: Search accommodations by location, price, and ratings, book and cancel reservations, rate accommodations, and view travel reports.
- *Accommodation Providers*: Manage listings, set prices and discounts, handle booking requests, and view earnings reports.
- *Administrators*: Register providers, manage users, and access detailed reports.
- *Personalized Recommendations*: Uses user history and preferences to suggest accommodations.
- *User Levels*: Bronze, Silver, and Gold levels based on booking history, offering various discounts.
- *Behavior Flags*: Identifies and flags irresponsible or malicious users to maintain platform integrity.
- *Notifications*: Alerts users to discounts, new listings in preferred locations, and booking status updates.

# Tech stack
- Frontend: Angular
- Backend: SpringBoot
- Rule Engine: Drools

# Running the project

1. **Setting up the database**
Run the following command to set up PostgreSQL database:
```
docker run --name sbnz-postgres -e POSTGRES_PASSWORD=12345678 -p 5434:5432 -d postgres
```

Copy the connection string to: `application.properties`

*Optionally: Insert baseline data to db by running the commands from data.sql in your DB Manager of choice.

2. **GoogleMaps API key**

Accomodo uses GoogleMaps to handle accommodation location. Make sure to provide an API key for GoogleMaps services inside `accomodo\sbnz-integracija-projekta\service\src\main\java\com\ftn\sbnz\service\services\GoogleMapsService.java`.

3. **SendGrid API key**

Accomodo uses SendGrid services to send email notification to users.
Make sure to provide API key and template ids to be able to send emails, or use the app without this feature.

4. **Run SpringBoot application**

5. **Run Angular application**
```
npm install --force
ng serve
```