/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

import java.util.*;


/**
 * Class MyKidneyRegister - is the final version. 
 * @author  Svetoslav Ivanov W17004799
 */
public class MyKidneyRegister extends VirtualKidneyPatientRegister
{
    TreeMap<String, KidneyPatient> registerTable;
    TreeMap<String, KidneyDonor> donorRegisterTree;
    TreeMap<Integer, LinkedList<KidneyDonor>> donorByBloodT;
    TreeMap<String, DonorsToPatientPair> pairByDonarLink;
    TreeMap<String, DonorsToPatientPair> pairByPatientLink;
    List<DonorsToPatientPair> dupDonorPairs;
    SortedMap<String, KidneyDonor> donorsSortedByBloodT;

    
   
    List<KidneyDonor>   donorRegister ;
    List<DonorsToPatientPair> linkTable;
   
    
    public MyKidneyRegister()
    { 
        reset(); 
    }
    //--------------------------------------------------------------------------
    void reset()
     { 

         registerTable = new TreeMap<>();
         donorRegisterTree = new TreeMap<>();
         donorByBloodT = new TreeMap<>();
         pairByDonarLink = new TreeMap<>();
         pairByPatientLink = new TreeMap<>();
         

         dupDonorPairs = new LinkedList<DonorsToPatientPair>();

        
        donorRegister = new LinkedList<KidneyDonor>();
        linkTable     = new LinkedList<DonorsToPatientPair>();
     }
   
    //--------------------------------------------------------------------------
    /**
     * addKindeyPatient to the register
     * 
     */
    @Override
    public void addKindeyPatient( KidneyPatient patient )
    {
        assert registerTable != null ;

         registerTable.put(patient.getNHSNorthPatientID1996(), patient);
       
    }
   //---------------------------------------------------------------------------
    /**
     * This will add a Donor
     * @param Patient 
     */
    
    @Override
    public boolean addDonor( String patient ,  KidneyDonor donar )
    {
      
        assert patient != null : "No null patient";
        
        DonorsToPatientPair pair = getPairForPatientID(patient); 

        String donarId = donar.getNHSNorthPatientID1996();
        if( pair == null) 
        {
            pair = new DonorsToPatientPair(patient , donarId );
           

            pairByPatientLink.put(patient, pair);
            donorRegisterTree.put(donarId, donar);

            if(pairByDonarLink.containsKey(donarId)) {
                dupDonorPairs.add(pairByDonarLink.get(donarId));
                dupDonorPairs.add(pair);
                dupDonorPairs.add(pairByDonarLink.get(donarId));
                dupDonorPairs.add(pair);
                
            } else {
                pairByDonarLink.put(donarId, pair);
                pairByPatientLink.put(patient, pair);
                
                
            }
            
                if(donorByBloodT.containsKey(donar.bloodType)) {
                    donorByBloodT.get(donar.bloodType).add(donar);
                } else {
                    LinkedList<KidneyDonor> newDonarList = new LinkedList<KidneyDonor>();
                    newDonarList.add(donar);
                    donorByBloodT.put(donar.bloodType, newDonarList);
                }
        }


        if(registerContains(patient) == false)
        { 
            System.out.printf(" WARNING Target Patient NOT REGISTERD %s ", patient );
            return false ;
        } 
        return true ; 
    }
    //--------------------------------------------------------------------------
    /**
     * return a internal pair for a given   patientNHSID.  
     * @param patientNHSID 
     */
    protected  DonorsToPatientPair getPairForPatientID(String patientNHSID   )
    {
        for( DonorsToPatientPair pair: linkTable )
       {
           if( pair.getPaientID().equals( patientNHSID ) )
           {
              return pair;
           }
       }
        return null;
    }
    //--------------------------------------------------------------------------
    /**
     * return a internal pair for a given   donorPatientNHSID. 
     * can return null if not available. 
     * @param patientNHSID 
     */
    protected  DonorsToPatientPair getPairForDonor(String donorPatientNHSID)
    {
        if(pairByDonarLink.containsKey(donorPatientNHSID)){
            return pairByDonarLink.get(donorPatientNHSID);
        } else {
            return null;
        }

    }
    //--------------------------------------------------------------------------
     /**
      * Return if the register has this sufferer with this NHSpatientID
      * @param patientID -
      * @return 
      */       
    @Override
    public boolean registerContains( String NHSpatientID )
    {
        return registerTable.containsKey(NHSpatientID); 
    }
    
          
    
    //--------------------------------------------------------------------------
    /**
     * getFirstRecipientForDonor gets the first KidneyPatient for this    
     * @param donor
     * @return KidneyPatient
     */
    @Override
    public KidneyPatient getFirstRecipientForDonor( KidneyDonor donor )
    {
        assert donor != null: "getFirstPaitentForDonor:: no donor";

        DonorsToPatientPair pair = getPairForDonor(donor.getNHSNorthPatientID1996());
        if(registerTable.containsKey(pair.getPaientID())) {
            return registerTable.get(pair.getPaientID());
        } else {
            return null;
        }

    }
    //--------------------------------------------------------------------------
    /**
     * Get getDonorForRecipient for a given KidneyPatient return matching KidneyDonor.
     * @param p:KidneyPatient
     * @return  KidneyDonor or null
     */
    @Override
    public KidneyDonor getDonorForRecipient( KidneyPatient p   ) 
    {


        if(pairByPatientLink.containsKey(p.getNHSNorthPatientID1996())){
            return donorRegisterTree.get(pairByPatientLink.get(p.getNHSNorthPatientID1996()).getDonorID());
        } else {
            return null;
        }

       
    }
    //--------------------------------------------------------------------------
    /**
     * get a list of Donors with this blood type.
     * @param type
     * @return 
     */
    // TODO
    @Override
    public List<KidneyDonor> getDonorsWithBloodType( int type )
    {

       List<KidneyDonor> results  = new LinkedList<KidneyDonor>(); 
       if(donorByBloodT.containsKey(type)){
           results = donorByBloodT.get(type);
       }

        return results; 
    }
    //--------------------------------------------------------------------------
    @Override
    public String getDonorsAndTheirPaitentsForBloodType( int type )
    {
        List<KidneyDonor> results  = getDonorsWithBloodType(type);

         StringBuilder s = new StringBuilder(); 
         s.append("Donor -> recipient\n");
         
        for( KidneyDonor d : results )
        {
            s.append(d.toString());
            s.append(" -> ");
            KidneyPatient p = getFirstRecipientForDonor( d );
            s.append(p.toString());
            s.append("\n");
        }
        return s.toString();
    }
    //--------------------------------------------------------------------------
    /**
     *  For a given NHSID1996 return the instance of KidneyDonor 
     * @param donorID
     * @return 
     */
    @Override
    public KidneyDonor getDonorForID( String donorID  ) 
    {
        
        return  (KidneyDonor) donorRegister;
    } 
    
    
    //--------------------------------------------------------------------------
    /**
     *  given a patientNHSID get the KidneyPatient instance 
     * @param patientNHSID
     * @return KidneyPatient  - null if not found 
     */
    @Override
    public KidneyPatient  getPatientforID( String patientNHSID ) {
        if (registerTable.containsKey(patientNHSID)) {
            return registerTable.get(patientNHSID);
        } else {
            return null;
        }

    }
    //--------------------------------------------------------------------------
    /*** 
     *  how many Patients are there ? 
     * @return - number of Patients
     */
    @Override
    public int countPatients()
    { 
        return registerTable.size();
    }
    //--------------------------------------------------------------------------
    /**
     * List out all Patient and their donors returns as a string.
     * @return 
     */
    @Override
    public String listAllPairs()
    { 
        StringBuilder s = new StringBuilder(); 
        for( Map.Entry<String, KidneyPatient> entry: registerTable.entrySet())
        {
            KidneyPatient p = entry.getValue();
            s.append(p.toString()); 
            s.append("->"); 
            s.append(getDonorForRecipient(p).toString()); 
            s.append("\n");
        }
        return s.toString();
    }
    //--------------------------------------------------------------------------
    /**
     * A donor is not allowed  be a Donor for two diffrent patients. This is
     * a reporting function to check. 
     * look for any donor who is in the database twice . 
     * @return 
     */
    @Override
    public List<DonorsToPatientPair> listDuplicateDonors()
    {
        return dupDonorPairs;

    }
    //--------------------------------------------------------------------------
    /**
     *  tests that all donors are not kidney Patients 
     * @return 
     */
     List<DonorsToPatientPair>  testForDonarPureity()
    { 
        List<DonorsToPatientPair> results = new LinkedList<>();

        for (Map.Entry donorPair : pairByDonarLink.entrySet()){

            if(registerTable.containsKey(donorPair.getKey())){
                String printStr = String.format("Donor who Patient %s\n ", ((DonorsToPatientPair)donorPair.getValue()).getDonorID());
                System.out.printf(printStr);
            }
        }


        return results ; //@@@ TODO 
    }

    //-----------------------------------------------------------------------------
}// END OF CLASS. 
