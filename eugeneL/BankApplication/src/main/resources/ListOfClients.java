import java.util.UUID;

public enum ListOfClients {
  CLIENT1("FOOBAR", "active_account", "initial_overdraft")
         {
           enum ListOfAccounts{A1};
         },
  CLIENT2("ACME", "active_account", "initial_overdraft")
         {
           enum ListOfAccounts{A1, B2;};
         };
  private final UUID   id;
  private final String name;
  private final String activeAccount;
  private final String initialOverdraft;

  private ListOfClients(String name, String activeAccount, String initialOverdraft) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.activeAccount = activeAccount;
    this.initialOverdraft = initialOverdraft;
  }

  public static void main(String args[]) {
    for (ListOfClients c : ListOfClients.values()) {
      //UUID u = UUID.randomUUID();
      //System.out.println("UUID: " + u.toString());
      System.out.printf("id: %s name: %s activeAccount: %s initialOverdraft: %s\n",
                         c.id.toString(), c.name, c.activeAccount, c.initialOverdraft
                        );
    }
  }
}
