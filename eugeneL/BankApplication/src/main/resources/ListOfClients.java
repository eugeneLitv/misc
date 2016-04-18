import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public enum ListOfClients {
  CLIENT1("0f02f640-0998-48af-81eb-062f7c3ed9da", "FOOBAR", 1, 100f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("6888db0f-c3a6-4e15-90c1-6f9252151ddc", 2800f, 500f)
             , new AccountS("47d3b7d1-1b3f-4bd2-8575-b795785826bc", 4300f)
             , new AccountS("1aa0d6df-6664-4485-aa78-cd0467599aa3", 8800f)
         ))),
  CLIENT2("7f404d02-a80b-4b1c-ad36-23c94c6196c1", "ACME", 1, 120f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("83347bff-7b1b-48d9-9267-c394a61924c5", 200f, 100f)
             , new AccountS("3022159a-300c-4d5e-9d9a-2b5774b640a7", 400f)
             , new AccountC("8037e6f4-a2d5-4700-abda-509a5068e828", 300f, 50)
         ))),
  CLIENT3("464f3093-d30f-44af-891c-a29cfc499a55", "FOO",         1, 250f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("1b323e06-c717-4e8c-86f6-79cbbd48d7e0", 300f, 50)
             , new AccountS("03ef63c7-a4d4-4e61-bc2e-43baf4011960", 80f)
             , new AccountS("f7268f95-18ad-43e1-8c88-20f5287a9970", 60f)
             , new AccountC("b54accc8-22b6-44f7-8135-1d3234533fee", 30f, 20)
         ))),
  CLIENT4("325e4271-0762-4126-852a-c40673718da4", "BAR",         2, 300f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("af52d6ca-ce96-44a8-9774-9e4ca9af7d1f", 300f, 250)
             , new AccountC("4e553e86-48fe-4315-be94-083d7c906817", 200f, 350)
             , new AccountC("ee3cf6ed-d096-4004-8351-1ee80a38a515", 800f, 500)
         ))),
  CLIENT5("91c81019-0ce7-4f54-8c8e-fe3edd553747", "The Best",    3, 500f
         , new ArrayList<Account>(Arrays.asList(
               new AccountS("58a5039d-0271-4860-af6a-79f0d05a700d", 1300f)
             , new AccountS("33a1705d-9e3d-472e-8f17-4f6c667a615c", 740f)
             , new AccountS("faf9124f-a663-404c-b1a3-d9253f91ee66", 581f)
             , new AccountS("90a25187-a8ca-4ce5-8dad-7ba5a4a8ddbc", 492f)
             , new AccountS("6cfb1ed5-af41-40c2-a291-4546e69e2d1c", 839f)
         ))),
  CLIENT6("226df2cd-1f2c-45d4-9568-22ce7e051885", "Pluto",       1, 400f
         , new ArrayList<Account>(Arrays.asList(
               new AccountC("bb587925-b743-4f04-94b5-d23dcab54e58", 300f, 450)
             , new AccountS("d31dd393-b2c7-4605-b5b1-af696d2c43c2", 5000f)
             , new AccountC("59e0892c-0f55-4bb9-97ab-5b45b8262d1d", 330f, 250)
             , new AccountS("50431c5c-87cd-4a87-9a95-abcb0f13bf62", 721f)
         ))),
  CLIENT7("b76087d9-15eb-4208-ba02-e6ca07ab8ada", "Eris",        3, 200f
         , new ArrayList<Account>(Arrays.asList(
               new AccountS("295cf53c-2d7d-41a7-b16b-b831b8161e4d", 525f)
             , new AccountC("6b9f7bd6-9331-426b-897e-cd0008d9b640", 921f, 200)
             , new AccountC("d3c0b385-3f5d-43ad-b0ca-0912dfb59393", 385f, 300)
             , new AccountS("3aa5f527-2f43-4f34-8172-3eb8a6ac89b8", 2500f)
         ))),
  CLIENT8("c3a7ed8a-e7de-44c8-980b-a09efdd91828", "Neptun",       3, 120f
         , null
         ),
  CLIENT9("3166015a-99c0-4124-b829-b1a581b94601", "Bugs Bunny",  1, 230f
         , new ArrayList<Account>(Arrays.asList(
               new AccountS("7b1a4cb0-55a3-49b5-b73e-ba82b4ccfb22", 600f)
             , new AccountC("ab0e70c1-579c-4a0e-a7ad-8f2deafcfa4c", 350f, 100)
             , new AccountS("40d7ac6c-541b-4b21-91f9-360240ca3c6c", 710f)
             , new AccountS("1df0b377-5e18-466c-ae28-4be1c8212097", 1234f)
         )))
         ;

  static enum AccountTypes {CHECKING, SAVING;}

  static interface Account {
    UUID getId();
    void printReport();
  }

  static class AccountC implements Account {
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

  static class AccountS implements Account {
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

  private final UUID          id;
  private final String        name;
  private final UUID          activeAccount;
  private       float         initialOverdraft;
  private final ArrayList<Account> ListOfAccount;

  private ListOfClients(String id, String name, int aIndex, float over, ArrayList<Account> accs)
  {
    this.id               = UUID.fromString(id);
    this.name             = name;
    this.activeAccount    = accs != null ? accs.get(aIndex).getId() : null;
    this.initialOverdraft = over;
    this.ListOfAccount    = accs;
  }

  public static void main(String args[]) {

    //Gson gson = new Gson();
    Gson gson = new GsonBuilder().serializeNulls().create();
    String json;
    for (ListOfClients c : ListOfClients.values()) {
      //UUID u = UUID.randomUUID();
      //System.out.println("UUID: " + u.toString());

      System.out.printf("id: %s name: %10s aA: %s initO: %.2f\n",
                         c.id, c.name, c.activeAccount, c.initialOverdraft
                        );

      if (c.ListOfAccount != null) {
        for (Account a : c.ListOfAccount) {
          json = gson.toJson(a);
          System.out.printf("JSON: %s\n", json);
          //a.printReport();
        }
      }
    }
    //String json = gson.toJson(ListOfClients.class);
    //System.out.println(json);
    // Generate UUIDs
    /*for (int i = 0; i < 40; i++) { System.out.println(UUID.randomUUID()); }*/
  }
}
