package com.im.bank.Model;

import com.example.im.bank.Model.Account;
import com.example.im.bank.Model.Payee;
import com.example.im.bank.Model.Transaction;

import java.util.ArrayList;

/**
 * Created by mikebanks on 2017-12-05.
 */
public class Profile {

    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private ArrayList<com.example.im.bank.Model.Account> accounts;
    private ArrayList<com.example.im.bank.Model.Payee> payees;
    private long dbId;

    public Profile (String firstName, String lastName, String phone, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        accounts = new ArrayList<>();
        payees = new ArrayList<>();
    }

    public Profile (String firstName, String lastName, String phone, String username, String password, long dbId) {
        this(firstName, lastName, phone, username, password);
        this.dbId = dbId;
    }

    /**
     * getters used to access the private fields of the profile
     */
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhone() {
        return phone;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<com.example.im.bank.Model.Account> getAccounts() { return accounts; }
    public ArrayList<com.example.im.bank.Model.Payee> getPayees() { return payees; }
    public long getDbId() { return dbId; }
    public void setDbId(long dbId) { this.dbId = dbId; }

    public void addAccount(String accountName, double accountBalance) {
        String accNo = "A" + (accounts.size() + 1);
        com.example.im.bank.Model.Account account = new com.example.im.bank.Model.Account(accountName, accNo, accountBalance);
        accounts.add(account);
    }
    public void setAccountsFromDB(ArrayList<com.example.im.bank.Model.Account> accounts) {
        this.accounts = accounts;
    }

    public void addTransferTransaction(com.example.im.bank.Model.Account sendingAcc, Account receivingAcc, double transferAmount) {

        sendingAcc.setAccountBalance(sendingAcc.getAccountBalance() - transferAmount);
        receivingAcc.setAccountBalance(receivingAcc.getAccountBalance() + transferAmount);

        int sendingAccTransferCount = 0;
        int receivingAccTransferCount = 0;
        for (int i = 0; i < sendingAcc.getTransactions().size(); i ++) {
            if (sendingAcc.getTransactions().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                sendingAccTransferCount++;
            }
        }
        for (int i = 0; i < receivingAcc.getTransactions().size(); i++) {
            if (receivingAcc.getTransactions().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                receivingAccTransferCount++;
            }
        }

        sendingAcc.getTransactions().add(new Transaction("T" + (sendingAcc.getTransactions().size() + 1) + "-T" + (sendingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        receivingAcc.getTransactions().add(new Transaction("T" + (receivingAcc.getTransactions().size() + 1) + "-T" + (receivingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }

    public void addPayee(String payeeName) {
        String payeeID = "P" + (payees.size() + 1);
        com.example.im.bank.Model.Payee payee = new com.example.im.bank.Model.Payee(payeeID, payeeName);
        payees.add(payee);
    }

    public void setPayeesFromDB(ArrayList<Payee> payees) {
        this.payees = payees;
    }
}
