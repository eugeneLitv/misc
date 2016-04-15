import java.util.UUID;

public enum ListOfClients {
  CLIENT1("id", "name1", "active_account", "initial_overdraft"/*,
       ACCOUNT ("id", "CHECKING", "balance", "overdraft"),
           {"id", "SAVING", "balance"},
           {"id", "CHECKINGtype", "balance", "overdraft"},
           {"id", "SAVING", "balance"}
       }*/
     ),
  CLIENT2("id", "name1", "active_account", "initial_overdraft"/*,
       {
           {"id", "CHECKING", "balance", "overdraft"},
           {"id", "SAVING", "balance"},
           {"id", "CHECKINGtype", "balance", "overdraft"},
           {"id", "SAVING", "balance"}
       }*/
     );
  private final String id;
  private final String name;
  private final String activeAccount;
  private final String initialOverdraft;

  private ListOfClients(String id, String name, String activeAccount, String initialOverdraft) {
    this.id = id;
    this.name = name;
    this.activeAccount = activeAccount;
    this.initialOverdraft = initialOverdraft;
  }

  public static void main(String args[]) {
    for (ListOfClients c : ListOfClients.values()) {
      UUID u = UUID.randomUUID();
      System.out.println("UUID: " + u.toString());
      System.out.printf("id: %s name: %s activeAccount: %s initialOverdraft: %s\n",
                         c.id, c.name, c.activeAccount, c.initialOverdraft
                        );
    }
  }
}
