1. Java Program Compilation & Execution

Compile Java Program:

javac -cp ".;lib/mysql-connector-j-9.4.0/mysql-connector-j-9.4.0.jar" src\model\*.java src\dao\*.java src\gui\*.java src\main\*.java


Purpose: Program ko compile karne ke liye. Code me changes karne ke baad is command ka use karein.

Run Java Application:

java -cp ".;lib/mysql-connector-j-9.4.0/mysql-connector-j-9.4.0.jar;src" main.Main


Purpose: Application ko run karne ke liye.

2. MySQL Commands for Voting System

Fetch Candidates Information:

USE voting_system;
SELECT * FROM candidates;


Purpose: Candidates ka information MySQL se lene ke liye.

Fetch Voter Login Requests:

USE voting_system;
SELECT * FROM voters;


Purpose: Database me login requests dekhne ke liye.

Verify a Voter:

USE voting_system;
UPDATE voters SET is_verified = TRUE WHERE email = 'test@gmail.com';
SELECT * FROM voters;


Purpose: Voter ko verify karne ke liye.

Check Verification Status of a Voter:

SELECT * FROM voters WHERE email = 'aman@gmail.com';


Purpose: Verify check karne ke liye.