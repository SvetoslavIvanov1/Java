/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

import java.util.*;

/**
 *  THIS IS YOUR CODE TO CHANGE ( or sub class what ever ) 
 * @author  Dalton
 */
public class KidneyPatientRegister extends VirtualKidneyPatientRegister
{
    List<KidneyPatient> registerTable ; 
    List<KidneyDonor>   donorRegister ;  
    
    List<DonorsToPatientPair> linkTable;
   
    
    //--------------------------------------------------------------------------
    public  KidneyPatientRegister()
    { 
        super(); 
        reset(); 
       
    }
    @Override  void reset()
    {
        registerTable = new ArrayList<KidneyPatient>();

        donorRegister = new LinkedList<KidneyDonor>();
        linkTable     = new LinkedList<DonorsToPatientPair>();
    }
             
    //--------------------------------------------------------------------------
    /**
     *  This tests for duplicates is the same person here twice? 
     *  Uses a nested four loop.
     *  Returns list which may be empty if no duplicates. 
     */
    public  ArrayList<KidneyPatient> testForDuplicates()
    { 
        
        return null; // replace this code 
    }
   
    //--------------------------------------------------------------------------
    /**
     * addKindeyPatient to the register
     * @param format KidneyPatient p
     */
    public void addKindeyPatient( KidneyPatient p )
    {
        assert registerTable != null ;  
         registerTable.add( p);
    }

   //---------------------------------------------------------------------------
    /**
     * 
     * @param Patient 
     */
  @Override  public boolean addDonor( String patient ,  KidneyDonor donar )
    {
        assert patient != null : "No null patient";
        
        DonorsToPatientPair pair = getPairForPatientID(patient); 
        
        if( pair == null) // the patient can have one or more donars...
        {
            pair = new DonorsToPatientPair(patient , donar.getNHSNorthPatientID1996() ); 
            linkTable.add( pair );
        }
        donorRegister.add(donar); 
        
        if( registerContains(patient )== false )
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
    protected  DonorsToPatientPair getPairForDonor(String donorPatientNHSID   )
    {
        for( DonorsToPatientPair pair: linkTable )
        {
            if( pair.getDonorID().equals( donorPatientNHSID ) )
            {
                return pair;
            } 
        }
        return null;
    }
    //--------------------------------------------------------------------------
     /**
      * Return if the register has this sufferer with this NHSpatientID
      * @param patientID -
      * @return 
      */       
   @Override  public boolean registerContains( String NHSpatientID )
    { 
        for( KidneyPatient p: registerTable )
        {
            if( p.getNHSNorthPatientID1996().equals( NHSpatientID ) )
            {
                return true;
            } 
        }
        return false ;     
    }
    //--------------------------------------------------------------------------
    /**
     * getFirstRecipientForDonor gets the first KidneyPatient for this    
     * @param donor
     * @return KidneyPatient
     */
    @Override public KidneyPatient getFirstRecipientForDonor( KidneyDonor donor )
    {
       assert donor != null: "getFirstPaitentForDonor:: no donor";
       DonorsToPatientPair pair =  getPairForDonor( donor.getNHSNorthPatientID1996());
       KidneyPatient result = null ; 
       for( KidneyPatient p :  registerTable  )  
       { 
           if( p.getNHSNorthPatientID1996().equals(pair.getPaientID()))
           { 
               result = p ; 
           }
       }
       return result  ; 
    }
    //--------------------------------------------------------------------------
    /**
     * Get getDonorForRecipient for a given KidneyPatient return matching KidneyDonor.
     * @param p:KidneyPatient
     * @return  KidneyDonor or null
     */
    @Override  public KidneyDonor getDonorForRecipient( KidneyPatient p   ) 
    {
       DonorsToPatientPair  pair = getPairForPatientID( p.getNHSNorthPatientID1996()); 
       KidneyDonor found = null ; 
       for( KidneyDonor d : donorRegister )
       { 
            if( d.getNHSNorthPatientID1996().equals( pair.getDonorID() ) )
            {
               found = d ; 
            }
        }
        return found ; 
    }
    //--------------------------------------------------------------------------
    /**
     * get a list of Donors with this blood type.
     * @param type
     * @return 
     */
   @Override   public List<KidneyDonor> getDonorsWithBloodType( int type )
    {
       List<KidneyDonor> results  = new LinkedList<KidneyDonor>(); 
       
       for( KidneyDonor d : donorRegister )
       { 
            if( d.getBloodType()==type )
            {
              results.add( d); 
            }
        }
        return results; 
    }
    //--------------------------------------------------------------------------
   @Override  public String getDonorsAndTheirPaitentsForBloodType( int type )
    { 
         StringBuilder s = new StringBuilder(); 
         s.append("Donor -> recipient\n");
         
        for( KidneyDonor d : donorRegister )
        {
            if( d.getBloodType()==type )
            {
                s.append(d.toString()); 
                s.append(" -> ");
                KidneyPatient p = getFirstRecipientForDonor( d );
                s.append(p.toString()); 
                s.append("\n");
            }
        }
        return s.toString();
    }
    //--------------------------------------------------------------------------
    /**
     *  For a given NHSID1996 return the instance of KidneyDonor 
     * @param donorID
     * @return 
     */
   @Override  public KidneyDonor getDonorForID( String donorID  ) 
    { 
        KidneyDonor found = null ;  
        for( KidneyDonor who: donorRegister  )
        { 
            if( who.getNHSNorthPatientID1996().equals(donorID)  )
            { 
                found = who; 
            }
        }
        return found  ; 
    } 
    //--------------------------------------------------------------------------
    /**
     *  given a patientNHSID get the KidneyPatient instance 
     * @param patientNHSID
     * @return KidneyPatient  - null if not found 
     */
   @Override  public KidneyPatient  getPatientforID( String patientNHSID )
    { 
      KidneyPatient found = null ; 
      for( KidneyPatient who : this.registerTable)
      {
          if( who.getNHSNorthPatientID1996().equals(patientNHSID))
          { 
              found = who ; 
          }
      }
      return found ; 
    }
    //--------------------------------------------------------------------------
    /*** 
     *  how many Patients are there ? 
     * @return - number of Patients
     */
   @Override public int countPatients()
    { 
        return registerTable.size(); 
    }
    //--------------------------------------------------------------------------
    /**
     * List out all Patient and their donors returns as a string.
     * @return 
     */
  @Override  public String listAllPairs()
    { 
        StringBuilder s = new StringBuilder(); 
        for( KidneyPatient p: registerTable  )
        {
            s.append(p.toString()); 
            s.append("->"); 
            s.append(getDonorForRecipient(p).toString()); 
            s.append("\n");
        }
        return s.toString();
    }
    //--------------------------------------------------------------------------
    /**
     * A donor is not allowed  be a Donor for two different patients. This is
     * a reporting function to check. 
     * look for any donor who is in the database twice . 
     * @return 
     */
  @Override  public List<DonorsToPatientPair> listDuplicateDonors()
    {
        List<DonorsToPatientPair> results = new LinkedList<>();

        for (DonorsToPatientPair donor : linkTable)
        {
            for (DonorsToPatientPair other : linkTable)
            {

                KidneyDonor d = this.getDonorForID(donor.getDonorID());
                KidneyDonor o = this.getDonorForID(other.getDonorID());
                assert d != null;
                assert o != null;

                KidneyPatient patOfdonor = this.getPatientforID(donor.getPaientID());
                KidneyPatient patOfOther = this.getPatientforID(other.getPaientID());

                if (d.equals(o) && !patOfdonor.equals(patOfOther))
                {
                    results.add(donor);
                    results.add(other);
                }
                
            }// end inner loop 
            
        }
        return results;
    }
    //--------------------------------------------------------------------------
    /**
     * testForDonarPureity
     * @return 
     */
     @Override List<DonorsToPatientPair>
          testForDonarPureity()
    { 
        List<DonorsToPatientPair> results = new LinkedList<>();

        for (DonorsToPatientPair donor : linkTable)
        {
            for (DonorsToPatientPair other : linkTable)
            {
                KidneyDonor d = this.getDonorForID(donor.getDonorID());
                KidneyPatient o = this.getPatientforID(other.getPaientID());
                assert d != null;
                assert o != null;

               if(  donor.getDonorID().equals(other.getPaientID()))
               {
                   System.out.printf("Donor who Patient %s\n ", donor.getDonorID() ); 
               }
            }
        }
        return results ; 
    }
    
  
}
