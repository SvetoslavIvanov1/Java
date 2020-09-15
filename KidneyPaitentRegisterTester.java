/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

import java.util.List;

/**
 * https://arainbloodbank.wordpress.com/faqs/ 
 * @author Nick Dalton
 */
public class KidneyPaitentRegisterTester<T extends VirtualKidneyPatientRegister >  
{
    //--------------------------------------------------------------------------
    /**
     *  runSpeedTests 
     */
    public static void runSpeedTests( )
    { 
      // CHANGE TO YOUR CLASS 
      MyKidneyRegister px  =  new MyKidneyRegister(); 
      runSpeedTests( px ) ;
    }
    /**
     * convenience   function - 
     * @return 
     */
    public static boolean runInternalChecks( )
    {
        //@@@ TODO  CHANGE TO YOUR CLASS 
         VirtualKidneyPatientRegister register = new MyKidneyRegister(); 
         return  runInternalChecks( register); 
    }
    /**
     * 
     * @param register
     * @return true if internal checks work.
     */
    public static boolean runInternalChecks( VirtualKidneyPatientRegister  register)
    { 
        boolean result = false   ; 
        try
        { 
            System.out.println("runInternalChecks::MAKING NEW REGISTER");
            String p1ID = "NHS111,2222"; 
            String donorID = "NHS999,333"; 

            KidneyPatient p = new KidneyPatient("Adam Adams", p1ID, "ADDRESS-EMPTY",0,3); 
            register.addKindeyPatient( p ); 
            assert register.registerContains(p1ID )== true : "register.registerContains failed";
            assert register.countPatients() == 1  : "runInternalChecks::register.countPatients() failed"; 

            KidneyDonor donor = new KidneyDonor("EVE Adams", donorID, "ADDRESS-EMPTY2" , 1, 2 );
            assert donor != null : "no donor"; 
            boolean b  = register.addDonor(p1ID, donor);

            assert  register.getDonorForRecipient(p)== donor: "runInternalChecks::getDonorForPaitent failed "; 
            System.out.println("getDonorForPaitent passed");
            assert  register.getFirstRecipientForDonor(donor)== p:"runInternalChecks::getPaitentForDonor"; 
            System.out.println("getFirstPaitentForDonor passed");
            String  allPairs = register.listAllPairs(); 
            System.out.println("'" + allPairs + "'"); 
            assert  allPairs.trim().equals("Adam Adams|NHS111,2222|ADDRESS-EMPTY|0|true|0|3->EVE Adams|NHS999,333|ADDRESS-EMPTY2|0|true|1|2")
                         : "All pairs note equal "+ allPairs ; 
            System.out.println("allPairs passed");

            assert register.countPatients()==1 :"register.countPatients "; 
            System.out.println( "register.countPatients passed");
            List<KidneyDonor> allDonors =  register.getDonorsWithBloodType( 1 );
            assert allDonors.size()==1  : "getDonorsWithBloodType error "; 
            assert allDonors.get(0) == donor :" getDonorsWithBloodType not found.  " ; 

            assert  register.getDonorForRecipient(p)== donor: "runInternalChecks::getDonorForPaitent failed "; 
            System.out.println("getDonorForPaitent passed\n 3.Duplicates ");

            System.out.println( register.getDonorsAndTheirPaitentsForBloodType( 1 ) );

            KidneyPatient theDuplicatePaitent = new KidneyPatient("Jene Adams","NHS999,4444", "ADDRESS-EMPTY",0,3); 

            List<DonorsToPatientPair> dups = register.listDuplicateDonors();
        // ADD DELIBERATE DUPLICATE 
            assert dups.size() == 0 : "Duplicates found where there were non.";
            register.addKindeyPatient( theDuplicatePaitent ); 
            register.addDonor(theDuplicatePaitent.getNHSNorthPatientID1996(), donor);
            dups = register.listDuplicateDonors();
            assert dups.size() == 4 : "Duplicates not found."; 
            System.out.println("Duplicates found (PASS if == 4)   " + dups.size());

            System.out.println("--faking it--");
            // makeFakeDatabase(register , 500    );
             //dups = register.listDuplicateDonors();

            System.out.println("Duplicates found   " + dups.size());
            System.out.println(dups); 

            System.out.println("3. Check testForDonarPureity    " + dups.size());
            List<DonorsToPatientPair>  results = register.testForDonarPureity();
            
            result = true ; 
            System.out.println("RunInternalChecks::PASSED");
        } catch( java.lang.AssertionError e )
        { 
            System.out.println("**ZERO MARKS - YOUR MODIFCATIONS BREAK THE CODE BASE!**");
            System.out.println("**ERROR WAS**");
            System.out.println("**"+ e + "**");
            System.err.println(e);
            result  = false ; 
        }
        return result ; 
    }
    //--------------------------------------------------------------------------
    /**
     * 
     * @param register thing to add to
     * @param size  how many. 
     */
    protected static void makeFakeDatabase(
                                    VirtualKidneyPatientRegister  register  , 
                                                                      int size )
    { 
        assert size >0 ; 
        assert register != null : "Null register not permitted .";
        for( int i = 0 ; i < size ; i++ )
        { 
          KidneyPatient s= makeRandomTestKindeyPatient() ; 
          register.addKindeyPatient( s ); 
          KidneyDonor d = makeRandomDonor();
          int k = 0 ; 
          while( d.getKidneyType()==s.getKidneyType() )
          { 
              d = makeRandomDonor();
              assert k++ < 20 : "makeFakeDatabase::loop problems"; 
          }
          
          register.addDonor(s.getNHSNorthPatientID1996(), d );
        }
    }
    //--------------------------------------------------------------------------
    /**
     * 
     * @return True if tests passed. 
     */
    public static boolean runTests()
    {
        System.out.println("MAKING NEW REGISTER");
        VirtualKidneyPatientRegister  register = new KidneyPatientRegister(); 
        makeFakeDatabase(register , 10  );
        System.out.println("NEW REGISTER DONE." + register.countPatients());
        
        System.out.printf( "Register | %s ", register.listAllPairs()); 
        
        return true  ; 
    }
    //--------------------------------------------------------------------------
   /* public static void doStuff( Class c )
    { 
        try
        { 
            VirtualKidneyPatientRegister register = (VirtualKidneyPatientRegister)c.newInstance();
            makeFakeDatabase(register , 10  );  
        } catch (InstantiationException ex)
        {
            Logger.getLogger(KidneyPaitentRegisterTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            Logger.getLogger(KidneyPaitentRegisterTester.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    */ 
    //--------------------------------------------------------------------------
    /** doStuff( VirtualKidneyPatientRegister.class );
     *  runSpeedTests 
     */
     public static void runSpeedTests( VirtualKidneyPatientRegister testRegister )
    {
       final int k = 40 ; 
       final int userSize = 2000; 
       assert testRegister != null: " testRegister cannot be null " ;
       
       BeforeVersionOfKidneyPatientRegister  registerBaseLine = new BeforeVersionOfKidneyPatientRegister();; 
    // TEST THE OLD SYSTEM.  
       long startTime =  System.nanoTime(); // Make a bunch see how long
       dice.setSeed(0xFACE);
       
       for( int c = 0 ; c < k ;c++ )
       {
        registerBaseLine.reset();
        makeFakeDatabase(registerBaseLine , userSize  );
       }
        
       long endTime =  System.nanoTime(); 
       long goldTime = endTime-startTime;
       System.out.printf("Creation Test::\n   Gold  was %,dns \n" ,(goldTime) )  ;
        
   // TEST THE NEW SYSTEM 
       startTime =  System.nanoTime();
       dice.setSeed(0xFACE);
       for( int c = 0 ; c < k ;c++ )
       { 
            testRegister.reset();  //  new KidneyPatientRegister(); 
            //System.out.println(registerToTest.getClass());
            assert  testRegister != null :"   testRegister.create() is null "; 
            makeFakeDatabase( testRegister , userSize  );
       } 
       long improved =  System.nanoTime() - startTime ; 
       double pcnt=  100.0 * ( improved /(double)goldTime);
       
       System.out.printf("Improved was %,dns\n change %.2f%% ( must be > 10%% to be diffrent) \n" ,improved,pcnt ); 
       
    //SET UP AND RUN THE DONOR DUPLICATE TIME 
       final int kNUMBEROFPEOPLE = 100;
       dice.setSeed(0xFACE);
       registerBaseLine.reset();
       makeFakeDatabase(registerBaseLine , kNUMBEROFPEOPLE  );
        
        dice.setSeed(0xFACE);
       testRegister.reset();
       makeFakeDatabase( testRegister , kNUMBEROFPEOPLE  );
       
       //testRegister.reset();
       testDonorDupliateTest(registerBaseLine , testRegister );
    // Setup and run donor pureity test. 
      // testRegister.reset();
       testTestForDonarPureity(registerBaseLine , testRegister );
         
    }
    //--------------------------------------------------------------------------
     protected static void testTestForDonarPureity( 
                                    VirtualKidneyPatientRegister goldRegister ,
                                    VirtualKidneyPatientRegister registerToTest )
     {
       final int k = 15 ; 
       assert registerToTest != null : " DonarPureity cannot be null " ;
       
       System.out.println("****Running DonarPureity tests****");
       long startTime =  System.nanoTime(); 
       dice.setSeed(0xFACE);
       List<DonorsToPatientPair> l =null;
       
       for( int c = 0 ; c < k ; c++ )
       { 
        l =   goldRegister.testForDonarPureity();
       } 
       final int goldSize = l.size();
       System.out.printf("size - %d", l.size());
   
       long goldTime =  System.nanoTime() - startTime ; 
       
       System.out.printf(" Gold was %,dns \n" ,goldTime )  ;
       startTime =  System.nanoTime();
       dice.setSeed(0xFACE);
       
       for( int c = 0 ; c < k ; c++ )
       { 
        l =   registerToTest.testForDonarPureity();
       } 
       System.out.printf("%d", l.size());
       long improved =  System.nanoTime() - startTime ; 
       System.out.printf(" NEW  was %,dns \n" , improved )  ;
       double pcnt=  100.0 * ( improved /(double)goldTime);
       assert goldSize == l.size(): " World "; 
       System.out.printf(" Change %.2f%% ( must be > 10%% to be diffrent) \n",pcnt );
     }
    //--------------------------------------------------------------------------
    /**
     * This tests both registers. Assumes they are of the same tise. 
     * @param goldRegister - the base line case 
     * @param registerToTest     - the 'improved' register to be tested. 
     */
    protected static 
       void  testDonorDupliateTest( VirtualKidneyPatientRegister goldRegister ,
                                    VirtualKidneyPatientRegister registerToTest )
    { 
       final int k = 12 ; 
       assert registerToTest != null : " registerToTest cannot be null " ;
       
       System.out.println("***\nRunning  testDonorDupliateTest" +" tests");
       long startTime =  System.nanoTime(); 
       dice.setSeed(0xFACE);
       for( int c = 0 ; c < k ; c++ )
       { 
        List<DonorsToPatientPair> l =   goldRegister.listDuplicateDonors();
       } 
   
       long goldTime =  System.nanoTime() - startTime ; 
       
       System.out.printf(" Gold was %,dns \n" ,(goldTime) )  ;
       startTime =  System.nanoTime();
       dice.setSeed(0xFACE);
       
       for( int c = 0 ; c < k ; c++ )
       { 
        List<DonorsToPatientPair> l =   registerToTest.listDuplicateDonors();
       } 
        
       long improved =  System.nanoTime() - startTime ; 
       System.out.printf(" NEW  was %,dns \n" , improved )  ;
       double pcnt=  100.0 * ( improved /(double)goldTime);
       System.out.printf(" Change %.2f%% ( must be > 10%% to be diffrent) \n" ,pcnt ); 
    }
    
    //--------------------------------------------------------------------------
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       runInternalChecks();
       runSpeedTests() ;    
       //
        
        //runTests(); 
        
        /*ArrayList<KindeyPatient> donars = new ArrayList<>(); 
        
        // TODO code application logic here
        for( int i = 0 ; i < 100 ; i++ )
        { KidneyPatient s= makeRandomTestKindeyPatient() ; 
            donars.add( s ); 
        System.out.println( s);
        }
        try
        { 
            PrintWriter writer = new PrintWriter("KidneyRegister.txt", "UTF-8");
            for( KidneyPatient p:  donars  )
            { 
                writer.println(p.toString());
                System.out.println("--" +  p.toString());
                
            }
            writer.close();
          
            BufferedReader br ;
            Path path = Paths.get("KidneyRegister.txt");
            br = Files.newBufferedReader(path); 
             {
                String line;
                 while ((line = br.readLine()) != null) 
                 {
                  System.out.printf("**** %s\n", line); 
                } 
             }  
     
            
        } catch(  FileNotFoundException |  SecurityException  |  UnsupportedEncodingException  e   )
        { 
            System.out.println("could not save "+ e );
        }
        catch( IOException e )
        { 
             System.out.println("could not save "+ e );
        }
        // make a big list of paitensts + some donars. 
        
        // if the priatice file does not exist  make it. 
*/ 
    }
    static  java.util.Random  dice = new java.util.Random(); 
    
   
    //--------------------------------------------------------------------------
    static KidneyPatient makeRandomTestKindeyPatient()
    { 
        char id = (char) ('A'+ ((char) dice.nextInt(25))); 
        String nhsid =  + (int)(2018-dice.nextInt(99))
                +   "|IXD" 
                + dice.nextInt() 
                +   id   
                + dice.nextInt(999); 
        int bloodType = dice.nextInt(7); 
        int kindeyType = dice.nextInt(12); 
        String address  = "NE" + dice.nextInt(9) + dice.nextInt(9) +" "+ 
                                dice.nextInt(9) ; 
        
        
        KidneyPatient who = new KidneyPatient( makeName() , 
                            nhsid , "ADDRESS-EMPTY" , bloodType, kindeyType ) ; 
        return who;
    }
    //--------------------------------------------------------------------------
    static KidneyDonor makeRandomDonor()
    { 
        char id = (char) ('A'+ ((char) dice.nextInt(25)));
        String nhsid =  + (int)(2018-dice.nextInt(99))
                +   "|IXD" 
                + dice.nextInt() 
                +   id   
                + dice.nextInt(999);
        int bloodType = dice.nextInt(7); 
        int kindeyType = dice.nextInt(12);
        
        KidneyDonor who = new KidneyDonor( makeName() , 
                            nhsid , "ADDRESS-EMPTY" , bloodType, kindeyType ) ;
        return who ; 
         
    }
       //--------------------------------------------------------------------------     
    static String makeRandomPerson()
    { 
        
        return makeName() 
                  + "|"
                 
                     + "|ADDRESS-EMPTY|" + "" 
                ; 
    }
    static String makeName()
    { 
        return names[ dice.nextInt(names.length)] + " " + 
                lastNames[ dice.nextInt( lastNames.length ) ]; 
    }
    
  static  String names[] =
{ 
 "Jack",
"James",
"Oliver",
"Lewis",
"Noah",
"Logan",
"Harry",
"Alexander",
"Leo",
"Charlie",
"Jacob",
"Lucas",
"Harris",
"Finlay",
"Alfie",
"Ethan",
"Mason",
"Daniel",
"Aaron",
"Max",
"Archie",
"Matthew",
"Thomas",
"Adam",
"Rory",
"Nathan",
"Joshua",
"Callum",
"Oscar",
"Brodie",
"Cameron",
"Harrison",
"William",
"Finn",
"Dylan",
"Riley",
"Liam",
"Ollie",
"Samuel",
"Jaxon",
"Connor",
"Jamie",
"Luke",
"Caleb",
"Theo",
"Andrew",
"Joseph",
"Muhammad",
"Jude",
"Ryan",
"Benjamin",
"Arran",
"Angus",
"David",
"Isaac",
"John",
"Cole",
"Hamish",
"Jackson",
"Michael",
"Robert",
"Ben",
"George",
"Kai",
"Luca",
"Murray",
"Kyle",
"Leon",
"Carter",
"Aiden",
"Blake",
"Freddie",
"Jake",
"Owen",
"Jayden",
"Cooper",
"Ruaridh",
"Fraser",
"Aidan",
"Sam",
"Blair",
"Calvin",
"Christopher",
"Reuben",
"Calum",
"Euan",
"Arthur",
"Elliot",
"Alex",
"Zac",
"Arlo",
"Cody",
"Henry",
"Lachlan",
"Robbie",
"Josh",
"Kayden",
"Conor",
"Hunter",
"Trinh",
"Windy",
"Omega",
"Haywood",
"Jesus",
"Gene",
"Altha",
"Jesica",
"Vita",
"Trinh",
"Windy”",
"Omega”",
"Haywood”",
"Jesus”",
"Gene”",
"Altha”",
"Jesica”",
"Vita",
"Machelle",
"Delora",
"Jani",
"Leola",
"Aaron",
"Brianna",
"Dulcie",
"Brittani",
"Natisha",
"Salvador",
"Cyndy",
"Suzan",
"Krystle",
"Lenore",
"Herma",
"Alejandrina",
"Jetta",
"Eugenia",
"Alyse",
"Ted",
"Marcene",
"Mitchel",
"Belinda",
"Yulanda",
"Christia",
"Lakendra",
"Flor",
"Evelin",
"Isaias",
"Joie",
"Aurora",
"Glory",
"Amparo",
"Demetrice",
"Yael",
"Jeane",
"Wendi",
"Dedra",
"Inocencia",
"Raphael",
"Rosita",
"Trinh",
"Windy",
"Omega",
"Haywood",
"Jesus",
"Gene",
"Altha",
"Jesica",
"Vita",
"Machelle",
"Delora",
"Jani",
"Leola",
"Aaron",
"Brianna",
"Dulcie",
"Brittani",
"Natisha",
"Salvador",
"Cyndy",
"Suzan",
"Krystle",
"Lenore",
"Herma",
"Alejandrina",
"Jetta",
"Eugenia",
"Alyse",
"Ted",
"Marcene",
"Mitchel",
"Belinda",
"Yulanda",
"Christia",
"Lakendra",
"Flor",
"Evelin",
"Isaias",
"Joie",
"Aurora",
"Glory",
"Amparo",
"Demetrice",
"Yael",
"Jeane",
"Wendi",
"Dedra",
"Inocencia",
"Raphael",
"Rosita"
        
} ; 
static String lastNames[] = 
{ 
  "Smith",
"Johnson",
"Williams",
"Jones",
"Brown",
"Davis",
"Miller",
"Wilson",
"Moore",
"Taylor",
"Anderson",
"Thomas",
"Jackson",
"White",
"Harris",
"Martin",
"Thompson",
"Garcia",
"Martinez",
"Robinson",
"Clark",
"Rodriguez",
"Lewis",
"Lee",
"Walker",
"Hall",
"Allen",
"Young",
"Hernandez",
"King",
"Wright",
"Lopez",
"Hill",
"Scott",
"Green",
"Adams",
"Baker",
"Gonzalez",
"Nelson",
"Carter",
"Mitchell",
"Perez",
"Roberts",
"Turner",
"Phillips",
"Campbell",
"Parker",
"Evans",
"Edwards",
"Collins",
"Stewart",
"Sanchez",
"Morris",
"Rogers",
"Reed",
"Cook",
"Morgan",
"Bell",
"Murphy",
"Bailey",
"Rivera",
"Cooper",
"Richardson",
"Cox",
"Howard",
"Ward",
"Torres",
"Peterson",
"Gray",
"Ramirez",
"James",
"Watson",
"Brooks",
"Kelly",
"Sanders",
"Price",
"Bennett",
"Wood",
"Barnes",
"Ross",
"Henderson",
"Coleman",
"Jenkins",
"Perry",
"Powell",
"Long",
"Patterson",
"Hughes",
"Flores",
"Washington",
"Butler",
"Simmons",
"Foster",
"Gonzales",
"Bryant",
"Alexander",
"Russell",
"Griffin",
"Diaz",
"Hayes",
"Myers",
"Ford",
"Hamilton",
"Graham",
"Sullivan",
"Wallace",
"Woods",
"Cole",
"West",
"Jordan",
"Owens",
"Reynolds",
"Fisher",
"Ellis",
"Harrison",
"Gibson",
"Mcdonald",
"Cruz",
"Marshall",
"Ortiz",
"Gomez",
"Murray",
"Freeman",
"Wells",
"Webb",
"Simpson",
"Stevens",
"Tucker",
"Porter",
"Hunter",
"Hicks",
"Crawford",
"Henry",
"Boyd",
"Mason",
"Morales",
"Kennedy",
"Warren",
"Dixon",
"Ramos",
"Reyes",
"Burns",
"Gordon",
"Shaw",
"Holmes",
"Rice",
"Robertson",
"Hunt",
"Black",
"Daniels",
"Palmer",
"Mills",
"Nichols",
"Grant",
"Knight",
"Ferguson",
"Rose",
"Stone",
"Hawkins",
"Dunn",
"Perkins",
"Hudson",
"Spencer",
"Gardner",
"Stephens",
"Payne",
"Pierce",
"Berry",
"Matthews",
"Arnold",
"Wagner",
"Willis",
"Ray",
"Watkins",
"Olson",
"Carroll",
"Duncan",
"Snyder",
"Hart",
"Cunningham",
"Bradley",
"Lane",
"Andrews",
"Ruiz",
"Harper",
"Fox",
"Riley",
"Armstrong",
"Carpenter",
"Weaver",
"Greene",
"Lawrence",
"Elliott",
"Chavez",
"Sims",
"Austin",
"Peters",
"Kelley",
"Franklin",
"Lawson",
"Fields",
"Gutierrez",
"Ryan",
"Schmidt",
"Carr",
"Vasquez",
"Castillo",
"Wheeler",
"Chapman",
"Oliver",
"Montgomery",
"Richards",
"Williamson",
"Johnston",
"Banks",
"Meyer",
"Bishop",
"Mccoy",
"Howell",
"Alvarez",
"Morrison",
"Hansen",
"Fernandez",
"Garza",
"Harvey",
"Little",
"Burton",
"Stanley",
"Nguyen",
"George",
"Jacobs",
"Reid", 
"SMITH",
"JOHNSON",
"WILLIAMS",
"BROWN",
"JONES",
"MILLER",
"DAVIS",
"GARCIA",
"RODRIGUEZ",
"WILSON",
"MARTINEZ",
"ANDERSON",
"TAYLOR",
"THOMAS",
"HERNANDEZ",
"MOORE",
"MARTIN",
"JACKSON",
"THOMPSON",
"WHITE",
"LOPEZ",
"LEE",
"GONZALEZ",
"HARRIS",
"CLARK",
"LEWIS",
"ROBINSON",
"WALKER",
"PEREZ",
"HALL",
"YOUNG",
"ALLEN",
"SANCHEZ",
"WRIGHT",
"KING",
"SCOTT",
"GREEN",
"BAKER",
"ADAMS",
"NELSON",
"HILL",
"RAMIREZ",
"CAMPBELL",
"MITCHELL",
"ROBERTS",
"CARTER",
"PHILLIPS",
"EVANS",
"TURNER",
"TORRES",
"PARKER",
"COLLINS",
"EDWARDS",
"STEWART",
"FLORES",
"MORRIS",
"NGUYEN",
"MURPHY",
"RIVERA",
"COOK",
"ROGERS",
"MORGAN",
"PETERSON",
"COOPER",
"REED",
"BAILEY",
"BELL",
"GOMEZ",
"KELLY",
"HOWARD",
"WARD",
"COX",
"DIAZ",
"RICHARDSON",
"WOOD",
"WATSON",
"BROOKS",
"BENNETT",
"GRAY",
"JAMES",
"REYES",
"CRUZ",
"HUGHES",
"PRICE",
"MYERS",
"LONG",
"FOSTER",
"SANDERS",
"ROSS",
"MORALES",
"POWELL",
"SULLIVAN",
"RUSSELL",
"ORTIZ",
"JENKINS",
"GUTIERREZ",
"PERRY",
"BUTLER",
"BARNES",
"FISHER",
"HENDERSON",
"COLEMAN",
"SIMMONS",
"PATTERSON",
"JORDAN",
"REYNOLDS",
"HAMILTON",
"GRAHAM",
"KIM",
"GONZALES",
"ALEXANDER",
"RAMOS",
"WALLACE",
"GRIFFIN",
"WEST",
"COLE",
"HAYES",
"CHAVEZ",
"GIBSON",
"BRYANT",
"ELLIS",
"STEVENS",
"MURRAY",
"FORD",
"MARSHALL",
"OWENS",
"MCDONALD",
"HARRISON",
"RUIZ",
"KENNEDY",
"WELLS",
"ALVAREZ",
"WOODS",
"MENDOZA",
"CASTILLO",
"OLSON",
"WEBB",
"WASHINGTON",
"TUCKER",
"FREEMAN",
"BURNS",
"HENRY",
"VASQUEZ",
"SNYDER",
"SIMPSON",
"CRAWFORD",
"JIMENEZ",
"PORTER",
"MASON",
"SHAW",
"GORDON",
"WAGNER",
"HUNTER",
"ROMERO",
"HICKS",
"DIXON",
"HUNT",
"PALMER",
"ROBERTSON",
"BLACK",
"HOLMES",
"STONE",
"MEYER",
"BOYD",
"MILLS",
"WARREN",
"FOX",
"ROSE",
"RICE",
"MORENO",
"SCHMIDT",
"PATEL",
"FERGUSON",
"NICHOLS",
"HERRERA",
"MEDINA",
"RYAN",
"FERNANDEZ",
"WEAVER",
"DANIELS",
"STEPHENS",
"GARDNER",
"PAYNE",
"KELLEY",
"DUNN",
"PIERCE",
"ARNOLD",
"TRAN",
"SPENCER",
"PETERS",
"HAWKINS",
"GRANT",
"HANSEN",
"CASTRO",
"HOFFMAN",
"HART",
"ELLIOTT",
"CUNNINGHAM",
"KNIGHT",
"BRADLEY",
"CARROLL",
"HUDSON",
"DUNCAN",
"ARMSTRONG",
"BERRY",
"ANDREWS",
"JOHNSTON",
"RAY",
"LANE",
"RILEY",
"CARPENTER",
"PERKINS",
"AGUILAR",
"SILVA",
"RICHARDS",
"WILLIS",
"MATTHEWS",
"CHAPMAN",
"LAWRENCE",
"GARZA",
"VARGAS",
"WATKINS",
"WHEELER",
"LARSON",
"CARLSON",
"HARPER",
"GEORGE",
"GREENE",
"BURKE",
"GUZMAN",
"MORRISON",
"MUNOZ",
"JACOBS",
"OBRIEN",
"LAWSON",
"FRANKLIN",
"LYNCH",
"BISHOP",
"CARR",
"SALAZAR",
"AUSTIN",
"MENDEZ",
"GILBERT",
"JENSEN",
"WILLIAMSON",
"MONTGOMERY",
"HARVEY",
"OLIVER",
"HOWELL",
"DEAN",
"HANSON",
"WEBER",
"GARRETT",
"SIMS",
"BURTON",
"FULLER",
"SOTO",
"MCCOY",
"WELCH",
"CHEN",
"SCHULTZ",
"WALTERS",
"REID",
"FIELDS",
"WALSH",
"LITTLE",
"FOWLER",
"BOWMAN",
"DAVIDSON",
"MAY",
"DAY",
"SCHNEIDER",
"NEWMAN",
"BREWER",
"LUCAS",
"HOLLAND",
"WONG",
"BANKS",
"SANTOS",
"CURTIS",
"PEARSON",
"DELGADO",
"VALDEZ",
"PENA",
"RIOS",
"DOUGLAS",
"SANDOVAL",
"BARRETT",
"HOPKINS",
"KELLER",
"GUERRERO",
"STANLEY",
"BATES",
"ALVARADO",
"BECK",
"ORTEGA",
"WADE",
"ESTRADA",
"CONTRERAS",
"BARNETT",
"CALDWELL",
"SANTIAGO",
"LAMBERT",
"POWERS",
"CHAMBERS",
"NUNEZ",
"CRAIG",
"LEONARD",
"LOWE",
"RHODES",
"BYRD",
"GREGORY",
"SHELTON",
"FRAZIER",
"BECKER",
"MALDONADO",
"FLEMING",
"VEGA",
"SUTTON",
"COHEN",
"JENNINGS",
"PARKS",
"MCDANIEL",
"WATTS",
"BARKER",
"NORRIS",
"VAUGHN",
"VAZQUEZ",
"HOLT",
"SCHWARTZ",
"STEELE",
"BENSON",
"NEAL",
"DOMINGUEZ",
"HORTON",
"TERRY",
"WOLFE",
"HALE",
"LYONS",
"GRAVES",
"HAYNES",
"MILES",
"PARK",
"WARNER",
"PADILLA",
"BUSH",
"THORNTON",
"MCCARTHY",
"MANN",
"ZIMMERMAN",
"ERICKSON",
"FLETCHER",
"MCKINNEY",
"PAGE",
"DAWSON",
"JOSEPH",
"MARQUEZ",
"REEVES",
"KLEIN",
"ESPINOZA",
"BALDWIN",
"MORAN",
"LOVE",
"ROBBINS",
"HIGGINS",
"BALL",
"CORTEZ",
"LE",
"GRIFFITH",
"BOWEN",
"SHARP",
"CUMMINGS",
"RAMSEY",
"HARDY",
"SWANSON",
"BARBER",
"ACOSTA",
"LUNA",
"CHANDLER",
"DANIEL",
"BLAIR",
"CROSS",
"SIMON",
"DENNIS",
"OCONNOR",
"QUINN",
"GROSS",
"NAVARRO",
"MOSS",
"FITZGERALD",
"DOYLE",
"MCLAUGHLIN",
"ROJAS",
"RODGERS",
"STEVENSON",
"SINGH",
"YANG",
"FIGUEROA",
"HARMON",
"NEWTON",
"PAUL",
"MANNING",
"GARNER",
"MCGEE",
"REESE",
"FRANCIS",
"BURGESS",
"ADKINS",
"GOODMAN",
"CURRY",
"BRADY",
"CHRISTENSEN",
"POTTER",
"WALTON",
"GOODWIN",
"MULLINS",
"MOLINA",
"WEBSTER",
"FISCHER",
"CAMPOS",
"AVILA",
"SHERMAN",
"TODD",
"CHANG",
"BLAKE",
"MALONE",
"WOLF",
"HODGES",
"JUAREZ",
"GILL",
"FARMER",
"HINES",
"GALLAGHER",
"DURAN",
"HUBBARD",
"CANNON",
"MIRANDA",
"WANG",
"SAUNDERS",
"TATE",
"MACK",
"HAMMOND",
"CARRILLO",
"TOWNSEND",
"WISE",
"INGRAM",
"BARTON",
"MEJIA",
"AYALA",
"SCHROEDER",
"HAMPTON",
"ROWE",
"PARSONS",
"FRANK",
"WATERS",
"STRICKLAND",
"OSBORNE",
"MAXWELL",
"CHAN",
"DELEON",
"NORMAN",
"HARRINGTON",
"CASEY",
"PATTON",
"LOGAN",
"BOWERS",
"MUELLER",
"GLOVER",
"FLOYD",
"HARTMAN",
"BUCHANAN",
"COBB",
"FRENCH",
"KRAMER",
"MCCORMICK",
"CLARKE",
"TYLER",
"GIBBS",
"MOODY",
"CONNER",
"SPARKS",
"MCGUIRE",
"LEON",
"BAUER",
"NORTON",
"POPE",
"FLYNN",
"HOGAN",
"ROBLES",
"SALINAS",
"YATES",
"LINDSEY",
"LLOYD",
"MARSH",
"MCBRIDE",
"OWEN",
"SOLIS",
"PHAM",
"LANG",
"PRATT",
"LARA",
"BROCK",
"BALLARD",
"TRUJILLO",
"SHAFFER",
"DRAKE",
"ROMAN",
"AGUIRRE",
"MORTON",
"STOKES",
"LAMB",
"PACHECO",
"PATRICK",
"COCHRAN",
"SHEPHERD",
"CAIN",
"BURNETT",
"HESS",
"LI",
"CERVANTES",
"OLSEN",
"BRIGGS",
"OCHOA",
"CABRERA",
"VELASQUEZ",
"MONTOYA",
"ROTH",
"MEYERS",
"CARDENAS",
"FUENTES",
"WEISS",
"WILKINS",
"HOOVER",
"NICHOLSON",
"UNDERWOOD",
"SHORT",
"CARSON",
"MORROW",
"COLON",
"HOLLOWAY",
"SUMMERS",
"BRYAN",
"PETERSEN",
"MCKENZIE",
"SERRANO",
"WILCOX",
"CAREY",
"CLAYTON",
"POOLE",
"CALDERON",
"GALLEGOS",
"GREER",
"RIVAS",
"GUERRA",
"DECKER",
"COLLIER",
"WALL",
"WHITAKER",
"BASS",
"FLOWERS",
"DAVENPORT",
"CONLEY",
"HOUSTON",
"HUFF",
"COPELAND",
"HOOD",
"MONROE",
"MASSEY",
"ROBERSON",
"COMBS",
"FRANCO",
"LARSEN",
"PITTMAN",
"RANDALL",
"SKINNER",
"WILKINSON",
"KIRBY",
"CAMERON",
"BRIDGES",
"ANTHONY",
"RICHARD",
"KIRK",
"BRUCE",
"SINGLETON",
"MATHIS",
"BRADFORD",
"BOONE",
"ABBOTT",
"CHARLES",
"ALLISON",
"SWEENEY",
"ATKINSON",
"HORN",
"JEFFERSON",
"ROSALES",
"YORK",
"CHRISTIAN",
"PHELPS",
"FARRELL",
"CASTANEDA",
"NASH",
"DICKERSON",
"BOND",
"WYATT",
"FOLEY",
"CHASE",
"GATES",
"VINCENT",
"MATHEWS",
"HODGE",
"GARRISON",
"TREVINO",
"VILLARREAL",
"HEATH",
"DALTON",
"VALENCIA",
"CALLAHAN",
"HENSLEY",
"ATKINS",
"HUFFMAN",
"ROY",
"BOYER",
"SHIELDS",
"LIN",
"HANCOCK",
"GRIMES",
"GLENN",
"CLINE",
"DELACRUZ",
"CAMACHO",
"DILLON",
"PARRISH",
"ONEILL",
"MELTON",
"BOOTH",
"KANE",
"BERG",
"HARRELL",
"PITTS",
"SAVAGE",
"WIGGINS",
"BRENNAN",
"SALAS",
"MARKS",
"RUSSO",
"SAWYER",
"BAXTER",
"GOLDEN",
"HUTCHINSON",
"LIU",
"WALTER",
"MCDOWELL",
"WILEY",
"RICH",
"HUMPHREY",
"JOHNS",
"KOCH",
"SUAREZ",
"HOBBS",
"BEARD",
"GILMORE",
"IBARRA",
"KEITH",
"MACIAS",
"KHAN",
"ANDRADE",
"WARE",
"STEPHENSON",
"HENSON",
"WILKERSON",
"DYER",
"MCCLURE",
"BLACKWELL",
"MERCADO",
"TANNER",
"EATON",
"CLAY",
"BARRON",
"BEASLEY",
"ONEAL",
"SMALL",
"PRESTON",
"WU",
"ZAMORA",
"MACDONALD",
"VANCE",
"SNOW",
"MCCLAIN",
"STAFFORD",
"OROZCO",
"BARRY",
"ENGLISH",
"SHANNON",
"KLINE",
"JACOBSON",
"WOODARD",
"HUANG",
"KEMP",
"MOSLEY",
"PRINCE",
"MERRITT",
"HURST",
"VILLANUEVA",
"ROACH",
"NOLAN",
"LAM",
"YODER",
"MCCULLOUGH",
"LESTER",
"SANTANA",
"VALENZUELA",
"WINTERS",
"BARRERA",
"ORR",
"LEACH",
"BERGER",
"MCKEE",
"STRONG",
"CONWAY",
"STEIN",
"WHITEHEAD",
"BULLOCK",
"ESCOBAR",
"KNOX",
"MEADOWS",
"SOLOMON",
"VELEZ",
"ODONNELL",
"KERR",
"STOUT",
"BLANKENSHIP",
"BROWNING",
"KENT",
"LOZANO",
"BARTLETT",
"PRUITT",
"BUCK",
"BARR",
"GAINES",
"DURHAM",
"GENTRY",
"MCINTYRE",
"SLOAN",
"ROCHA",
"MELENDEZ",
"HERMAN",
"SEXTON",
"MOON",
"HENDRICKS",
"RANGEL",
"STARK",
"LOWERY",
"HARDIN",
"HULL",
"SELLERS",
"ELLISON",
"CALHOUN",
"GILLESPIE",
"MORA",
"KNAPP",
"MCCALL",
"MORSE",
"DORSEY",
"WEEKS",
"NIELSEN",
"LIVINGSTON",
"LEBLANC",
"MCLEAN",
"BRADSHAW",
"GLASS",
"MIDDLETON",
"BUCKLEY",
"SCHAEFER",
"FROST",
"HOWE",
"HOUSE",
"MCINTOSH",
"HO",
"PENNINGTON",
"REILLY",
"HEBERT",
"MCFARLAND",
"HICKMAN",
"NOBLE",
"SPEARS",
"CONRAD",
"ARIAS",
"GALVAN",
"VELAZQUEZ",
"HUYNH",
"FREDERICK",
"RANDOLPH",
"CANTU",
"FITZPATRICK",
"MAHONEY",
"PECK",
"VILLA",
"MICHAEL",
"DONOVAN",
"MCCONNELL",
"WALLS",
"BOYLE",
"MAYER",
"ZUNIGA",
"GILES",
"PINEDA",
"PACE",
"HURLEY",
"MAYS",
"MCMILLAN",
"CROSBY",
"AYERS",
"CASE",
"BENTLEY",
"SHEPARD",
"EVERETT",
"PUGH",
"DAVID",
"MCMAHON",
"DUNLAP",
"BENDER",
"HAHN",
"HARDING",
"ACEVEDO",
"RAYMOND",
"BLACKBURN",
"DUFFY",
"LANDRY",
"DOUGHERTY",
"BAUTISTA",
"SHAH",
"POTTS",
"ARROYO",
"VALENTINE",
"MEZA",
"GOULD",
"VAUGHAN",
"FRY",
"RUSH",
"AVERY",
"HERRING",
"DODSON",
"CLEMENTS",
"SAMPSON",
"TAPIA",
"BEAN",
"LYNN",
"CRANE",
"FARLEY",
"CISNEROS",
"BENTON",
"ASHLEY",
"MCKAY",
"FINLEY",
"BEST",
"BLEVINS",
"FRIEDMAN",
"MOSES",
"SOSA",
"BLANCHARD",
"HUBER",
"FRYE",
"KRUEGER",
"BERNARD",
"ROSARIO",
"RUBIO",
"MULLEN",
"BENJAMIN",
"HALEY",
"CHUNG",
"MOYER",
"CHOI",
"HORNE",
"YU",
"WOODWARD",
"ALI",
"NIXON",
"HAYDEN",
"RIVERS",
"ESTES",
"MCCARTY",
"RICHMOND",
"STUART",
"MAYNARD",
"BRANDT",
"OCONNELL",
"HANNA",
"SANFORD",
"SHEPPARD",
"CHURCH",
"BURCH",
"LEVY",
"RASMUSSEN",
"COFFEY",
"PONCE",
"FAULKNER",
"DONALDSON",
"SCHMITT",
"NOVAK",
"COSTA",
"MONTES",
"BOOKER",
"CORDOVA",
"WALLER",
"ARELLANO",
"MADDOX",
"MATA",
"BONILLA",
"STANTON",
"COMPTON",
"KAUFMAN",
"DUDLEY",
"MCPHERSON",
"BELTRAN",
"DICKSON",
"MCCANN",
"VILLEGAS",
"PROCTOR",
"HESTER",
"CANTRELL",
"DAUGHERTY",
"CHERRY",
"BRAY",
"DAVILA",
"ROWLAND",
"MADDEN",
"LEVINE",
"SPENCE",
"GOOD",
"IRWIN",
"WERNER",
"KRAUSE",
"PETTY",
"WHITNEY",
"BAIRD",
"HOOPER",
"POLLARD",
"ZAVALA",
"JARVIS",
"HOLDEN",
"HENDRIX",
"HAAS",
"MCGRATH",
"BIRD",
"LUCERO",
"TERRELL",
"RIGGS",
"JOYCE",
"ROLLINS",
"MERCER",
"GALLOWAY",
"DUKE",
"ODOM",
"ANDERSEN",
"DOWNS",
"HATFIELD",
"BENITEZ",
"ARCHER",
"HUERTA",
"TRAVIS",
"MCNEIL",
"HINTON",
"ZHANG",
"HAYS",
"MAYO",
"FRITZ",
"BRANCH",
"MOONEY",
"EWING",
"RITTER",
"ESPARZA",
"FREY",
"BRAUN",
"GAY",
"RIDDLE",
"HANEY",
"KAISER",
"HOLDER",
"CHANEY",
"MCKNIGHT",
"GAMBLE",
"VANG",
"COOLEY",
"CARNEY",
"COWAN",
"FORBES",
"FERRELL",
"DAVIES",
"BARAJAS",
"SHEA",
"OSBORN",
"BRIGHT",
"CUEVAS",
"BOLTON",
"MURILLO",
"LUTZ",
"DUARTE",
"KIDD",
"KEY",
"COOKE"


} ; 

    
}
