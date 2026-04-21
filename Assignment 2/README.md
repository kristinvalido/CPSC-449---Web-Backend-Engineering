# CPSC-449---Web-Backend-Engineering
Coding assignment from my Web Backend Engineering class -- mainly using Java.
The following folder only contains the files I edited for the assignment.

**ASSIGNMNET 2:** 
1. Add the DELETE endpoint (BookController.java)
   
2. Protect the endpoint in SecurityConfig.java
   
3. Register an ADMIN user
   <img width="1731" height="903" alt="Screenshot 2026-04-20 205703" src="https://github.com/user-attachments/assets/6a6494f5-fe33-463b-8aec-887648658246" />
   <img width="1710" height="881" alt="Screenshot 2026-04-20 205801" src="https://github.com/user-attachments/assets/7d3bece2-7c9e-4b5b-8aff-320653f9b3d3" />
Successsfully created an admin and user

<img width="1903" height="1034" alt="Screenshot 2026-04-20 214158" src="https://github.com/user-attachments/assets/0435c97f-a7d6-44fb-889d-419127c258f6" />
Sucessfully creating a book and getting Id

4. Test Scenario 1 — ADMIN can delete (must pass)
   <img width="1735" height="998" alt="Screenshot 2026-04-20 231256" src="https://github.com/user-attachments/assets/240a4b44-6053-4604-9458-b3877c013676" />
Successfully logged in and got token as admin
   <img width="1712" height="962" alt="Screenshot 2026-04-20 214832" src="https://github.com/user-attachments/assets/e9fc36a8-244e-4526-bef1-ca588cd7ea2d" />
Admin Successfully deleting book, used header to apply token for authorization.

5. Test Scenario 2 — USER cannot delete (must pass)
   <img width="1755" height="962" alt="Screenshot 2026-04-20 211907" src="https://github.com/user-attachments/assets/2b1bad6c-d6e4-4ccd-8654-d44b0ab0ee12" />
Logging in and acquiring token for user
   <img width="1779" height="944" alt="Screenshot 2026-04-20 214916" src="https://github.com/user-attachments/assets/f8b71cc6-fae9-44d9-88f9-09463697380e" />
   User denied access to delete, applied token in header.

