# Java Core User Management System

User Management System using Java Core integrated with MySQL, allow authentication of Admin/Client.

## 1. System requirement
* Java JDK 21.
* MySQL Server.
* MySQL Connector/J 9.6.0.

## 2. Setup

1. **Database Initialization:**
   * Open MySQL Workbench.
   * Run SQL in `.src/database/init.sql` to create the database, tables and sample data, which contain default admin account.

2. **Database Configuration:**
   * Update `./src/database.properties` according to your database information (db.url, db.user, db.password)

3. **Library:**
   * Make sure that the library `mysql-connector-j-9.6.0.jar` is added to ./lib folder.
   * You can download the library. If you have Maven you may not need to.

4. **Run:**
   * Run `App.java`.
   * Login with the default account: `admin` / `admin123`.

## 3. Directories
* `src/models`: Object definitions of User, Admin, Client.
* `src/dao`: Data Access Object for SQL queries (CRUD).
* `src/config`: Database Connection Management.

