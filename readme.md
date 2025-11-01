# Output screenshot:
<img width="475" height="362" alt="Screenshot 2025-10-11 222718" src="https://github.com/user-attachments/assets/5669ebbd-85a3-4549-acde-dec62f6a2b53" />
<img width="542" height="481" alt="Screenshot 2025-10-11 222749" src="https://github.com/user-attachments/assets/71e4148f-eda6-4d47-a336-73f8dc0b0224" />
<img width="478" height="290" alt="Screenshot 2025-10-11 222815" src="https://github.com/user-attachments/assets/770fd589-e9bd-49b7-86bd-005ea9015d35" />
<img width="979" height="739" alt="Screenshot 2025-10-11 222946" src="https://github.com/user-attachments/assets/778ed458-586e-4906-a963-9152d490c9e5" />
<img width="978" height="731" alt="Screenshot 2025-10-11 223008" src="https://github.com/user-attachments/assets/cfbb0c5b-201f-4a27-8530-c447be9f3b08" />
<img width="726" height="613" alt="Screenshot 2025-10-11 223657" src="https://github.com/user-attachments/assets/94e66381-4a51-40f0-a569-d7273df335a0" />



 # 1. Java Program Compilation & Execution

# Compile Java Program:

javac -cp ".;lib/mysql-connector-j-9.4.0/mysql-connector-j-9.4.0.jar" src\model\*.java src\dao\*.java src\gui\*.java src\main\*.java


Purpose: Program ko compile karne ke liye. Code me changes karne ke baad is command ka use karein.

# Run Java Application:

java -cp ".;lib/mysql-connector-j-9.4.0/mysql-connector-j-9.4.0.jar;src" main.Main


Purpose: Application ko run karne ke liye.

# 2. MySQL Commands for Voting System

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
