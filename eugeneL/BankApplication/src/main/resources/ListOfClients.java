import java.util.UUID;

public enum ListOfClients {
  CLIENT1("0f02f640-0998-48af-81eb-062f7c3ed9da", "FOOBAR", "d78ac23b-f2f8-4f8c-9211-7fd59a011dd8", 100f)
         {
         },
  CLIENT2("7f404d02-a80b-4b1c-ad36-23c94c6196c1", "ACME", "2b68c8b6-e12d-44a7-aec8-10bb1f81baec", 120f)
         {
         };
  private final UUID   id;
  private final String name;
  private final UUID activeAccount;
  private final float initialOverdraft;

  private ListOfClients(String id, String name, String activeAccount, float initialOverdraft) {
    this.id = UUID.fromString(id);
    this.name = name;
    this.activeAccount = UUID.fromString(activeAccount);
    this.initialOverdraft = initialOverdraft;
  }

  public static void main(String args[]) {
    for (ListOfClients c : ListOfClients.values()) {
      //UUID u = UUID.randomUUID();
      //System.out.println("UUID: " + u.toString());
      System.out.printf("id: %s name: %6s aA: %s initO: %s\n",
                         c.id.toString(), c.name, c.activeAccount.toString(), c.initialOverdraft
                        );
    }
  }
}
