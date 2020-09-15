/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

import java.util.List;

/**
 *
 * @author Nick Dalton
 */
public abstract class  VirtualKidneyPatientRegister
{


   public VirtualKidneyPatientRegister()
   { 
       reset(); 
   }
    /**
  *  message to instance to reset it's self - used for repreated testing. 
  *  afterwards countPatients should return 0 ; 
  * 
  */
   abstract void reset(); 
   
    //---------------------------------------------------------------------------
    /**
     *
     * @param Patient
     */
   abstract  boolean addDonor(String patient, KidneyDonor donar)  ; 

    //--------------------------------------------------------------------------
    /**
     * addKindeyPatient to the register
     * @param format KidneyPatient p
     */
   abstract  void addKindeyPatient(KidneyPatient p);

    //--------------------------------------------------------------------------
    /***
     *  how many Patients are there ?
     * @return - number of Patients
     */
   abstract int countPatients();

    //--------------------------------------------------------------------------
    /**
     *  For a given NHSID1996 return the instance of KidneyDonor
     * @param donorID
     * @return
     */
   abstract KidneyDonor getDonorForID(String donorID);

    //--------------------------------------------------------------------------
    /**
     * Get getDonorForRecipient for a given KidneyPatient return matching KidneyDonor.
     * @param p:KidneyPatient
     * @return  KidneyDonor or null
     */
   abstract KidneyDonor getDonorForRecipient(KidneyPatient p);

    //--------------------------------------------------------------------------
    abstract String getDonorsAndTheirPaitentsForBloodType(int type);

    //--------------------------------------------------------------------------
    /**
     * get a list of Donors with this blood type.
     * @param type
     * @return
     */
   abstract  List<KidneyDonor> getDonorsWithBloodType(int type);

    //--------------------------------------------------------------------------
    /**
     * getFirstRecipientForDonor gets the first KidneyPatient for this
     * @param donor
     * @return KidneyPatient
     */
   abstract KidneyPatient getFirstRecipientForDonor(KidneyDonor donor);

    //--------------------------------------------------------------------------
    /**
     *  given a patientNHSID get the KidneyPatient instance
     * @param patientNHSID
     * @return KidneyPatient  - null if not found
     */
   abstract KidneyPatient getPatientforID(String patientNHSID);

    //--------------------------------------------------------------------------
    /**
     * List out all Patient and their donors returns as a string.
     * @return
     */
   abstract String listAllPairs();

    //--------------------------------------------------------------------------
    /**
     * A donor is not allowed  be a Donor for two diffrent patients. This is
     * a reporting function to check.
     * look for any donor who is in the database twice .
     * @return
     */
   abstract List<DonorsToPatientPair> listDuplicateDonors();

    //--------------------------------------------------------------------------
    /**
     * Return if the register has this sufferer with this NHSpatientID
     * @param patientID -
     * @return
     */
   abstract boolean registerContains(String NHSpatientID);
    //--------------------------------------------------------------------------
    /**
     * testForDonarPureity
     * @return 
     */
    abstract List<DonorsToPatientPair> testForDonarPureity();
           
    
}
