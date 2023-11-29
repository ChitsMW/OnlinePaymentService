# OnlinePaymentService
Pair partnering capstone project. We were tasked with writing a RESTful API server and command-line application for an online payment service which transfers "TE buck" between friends.

1. [COMPLETE] As a user of the system, I need to be able to register myself with a username and password.
A new registered user starts with an initial balance of 1,000 TE Bucks.
The ability to register has been provided in your starter code.

2. [COMPLETE] As a user of the system, I need to be able to log in using my registered username and password.
Logging in returns an Authentication Token. I need to include this token with all my subsequent interactions with the system outside of registering and logging in.
The ability to log in has been provided in your starter code.

3. As an authenticated user of the system, I need to be able to see my Account Balance.
Caleigh insert this into git code
Caleigh think about authentication step

4. As an authenticated user of the system, I need to be able to send a transfer of a specific amount of TE Bucks to a registered user.
I should be able to choose from a list of users to send TE Bucks to.I must not be allowed to send money to myself.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
The receiver's account balance is increased by the amount of the transfer.
The sender's account balance is decreased by the amount of the transfer.
I can't send more TE Bucks than I have in my account.
I can't send a zero or negative amount.
A Sending Transfer has an initial status of Approved.
Caleigh and Chits work on this separately and then check

5. As an authenticated user of the system, I need to be able to see transfers I have sent or received.

6. As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.

7. As an authenticated user of the system, I need to be able to request a transfer of a specific amount of TE Bucks from another registered user.
I should be able to choose from a list of users to request TE Bucks from.
I must not be allowed to request money from myself.
I can't request a zero or negative amount.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
A Request Transfer has an initial status of Pending.
No account balance changes until the request is approved.
The transfer request should appear in both users' list of transfers (use case #5).
8. As an authenticated user of the system, I need to be able to see my Pending transfers.
9. As an authenticated user of the system, I need to be able to either approve or reject a Request Transfer.
I can't "approve" a given Request Transfer for more TE Bucks than I have in my account.
The Request Transfer status is Approved if I approve, or Rejected if I reject the request.
If the transfer is approved, the requester's account balance is increased by the amount of the request.
If the transfer is approved, the requestee's account balance is decreased by the amount of the request.
If the transfer is rejected, no account balance changes.

