package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    private final UserService userService = new UserService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {

        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        //call account service here

        // TODO Auto-generated method stub
        int userId = currentUser.getUser().getId();
        accountService.setAuthToken(currentUser.getToken());
        Account account = accountService.getAccountByUserId(userId);
        System.out.println("Your current balance is: $" + account.getBalance());

//        System.out.println("Your current balance is: Testing");
//        int accountId =2001;
//        Account account = accountService.getAccountById(accountId);
//        System.out.println("Your current balance is: $" + account.getBalance());
//		int userId = currentUser.getUser().getId();
//        System.out.println("Your user_id s : " + userId);

//
//        int userId = currentUser.getUser().getId();
//        Account account = accountService.getAccountByUserId(userId);
//        System.out.println("Your current balance is: $" + account.getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub

        consoleService.printTransferListHeader();
        int userId = currentUser.getUser().getId();
        transferService.setAuthToken(currentUser.getToken());
        Transfer[] transferHistory = transferService.listTransfersByUserId(userId);
        try {
            for (Transfer t : transferHistory) {
                if (t.getTransfer_type_id() == 1) {
                    System.out.println(t.getTransfer_id() + "From: " + t.getAccount_from() + " $" + t.getAmount());
                    //TODO : create method in User for getUserByAccountId to populate the "from" and "to" fields here and below
                } else if (t.getTransfer_type_id() == 2) {
                    System.out.println(t.getTransfer_id() + "To: " + t.getAccount_from() + " $" + t.getAmount());
                }
            }
        } catch (Exception e) {
            System.out.println("No transfer history to display.");
        }

        System.out.println("-----------------------------------");
        int menuSelection = -1;
        menuSelection = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
        if (menuSelection == 0) {
            mainMenu();
        } else if (menuSelection != 0) {
            for (Transfer t : transferHistory) {
                if (menuSelection == t.getTransfer_id()) {
                    consoleService.printTransferDetailsHeader();
                    System.out.println("Id: " + t.getTransfer_id());
                    if (t.getTransfer_type_id() == 1) {
                        System.out.println("From: " + t.getAccount_from());
                        System.out.println("Type: Request");
                    } else if (t.getTransfer_type_id() == 2) {
                        System.out.println("To: " + t.getAccount_to());
                        System.out.println("Type: Send");
                    }
                    if (t.getTransfer_status_id() == 1) {
                        System.out.println("Status: Pending");
                    } else if (t.getTransfer_status_id() == 2) {
                        System.out.println("Status: Approved");
                    } else if (t.getTransfer_status_id() == 3) {
                        System.out.println("Status: Rejected");
                    }
                    System.out.println("Amount: $" + t.getAmount());
                }
            }
        } else {
            System.out.println("The transfer number you have entered in invalid.");
        }

//        System.out.println("Your transfer history is: Testing");
//
//        int transferId = 5;
//        Transfer transfer = transferService.getTransferById(transferId);
//        System.out.println("This is your transfer history: " + transfer.getTransfer_id());
//        System.out.println("This is your transfer history: " + transfer.getTransfer_status_id());
//
//
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub

        accountService.setAuthToken(currentUser.getToken());
        User[] users = userService.getUsers();
        for (User u : users) {
            if (u.getId() != currentUser.getUser().getId()) {
                System.out.println(u.getId() + "     " + u.getUsername());
            }
        }

        System.out.println();
        int menuSelection = consoleService.promptForInt("Please enter the user ID you wish you send money to (0 to cancel): ");
        if (menuSelection == 0) {
            mainMenu();
        } else if (menuSelection != 0) {
            for (User u : users) {
                if (u.getId() == menuSelection) {
                    BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount of money you'd like to send: ");
                    transferService.sendMoney(currentUser.getUser().getId(), u.getId(), amount);
                } else {
                    System.out.println("The user ID number you have entered in invalid.");
                }
            }
        }


    }

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
