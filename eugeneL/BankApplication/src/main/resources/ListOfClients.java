import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

enum AccountTypes {CHECKING, SAVING;}

interface Account {
  UUID getId();
  void printReport();
}

class AccountC implements Account {
  private final UUID         id;
  private final AccountTypes type = AccountTypes.CHECKING;
  private       float        balance;
  private       float        overdraft;

  public AccountC(String id, float balance, float overdraft) {
    this.id        = UUID.fromString(id);
    this.balance   = balance;
    this.overdraft = overdraft;
  }
  public UUID getId() {return id;}
  public void printReport() {
    System.out.printf("     accid: %s type: %8s bal: %6.2f, over: %4.2f\n", id, type, balance, overdraft);
  }
}

class AccountS implements Account {
  private final UUID         id;
  private final AccountTypes type = AccountTypes.SAVING;
  private       float        balance;

  public AccountS(String id, float balance) {
    this.id        = UUID.fromString(id);
    this.balance   = balance;
  }
  public UUID getId() {return id;}
  public void printReport() {
    System.out.printf("     accid: %s type: %8s bal: %6.2f\n", id, type, balance);
  }
}

public enum ListOfClients {
  CLIENT1("0f02f640-0998-48af-81eb-062f7c3ed9da", "FOOBAR", 1, 100f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("6888db0f-c3a6-4e15-90c1-6f9252151ddc", 200f, 100f)
             , new AccountS("47d3b7d1-1b3f-4bd2-8575-b795785826bc", 400f)
           )))
         ,
  CLIENT2("7f404d02-a80b-4b1c-ad36-23c94c6196c1", "ACME", 1, 120f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("83347bff-7b1b-48d9-9267-c394a61924c5", 200f, 100f)
             , new AccountS("3022159a-300c-4d5e-9d9a-2b5774b640a7", 400f)
           )))
         ;
  private final UUID          id;
  private final String        name;
  private final UUID          activeAccount;
  private       float         initialOverdraft;
  private final ArrayList<Account> ListOfAccount;

  private ListOfClients(String id, String name, int aIndex, float over, ArrayList<Account> accs)
  {
    this.id               = UUID.fromString(id);
    this.name             = name;
    this.activeAccount    = accs.get(aIndex).getId();
    this.initialOverdraft = over;
    this.ListOfAccount    = accs;
  }

  public static void main(String args[]) {
    for (ListOfClients c : ListOfClients.values()) {
      //UUID u = UUID.randomUUID();
      //System.out.println("UUID: " + u.toString());
      System.out.printf("id: %s name: %6s aA: %s initO: %.2f\n",
                         c.id, c.name, c.activeAccount, c.initialOverdraft
                        );

      c.ListOfAccount.forEach(Account::printReport);
    }
    // Generate UUIDs
    for (int i = 0; i < 10; i++) {
      System.out.println(UUID.randomUUID());
    }
  }
}
