/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

/**
 *
 * @author Nic Dalton
 */
public class DonorsToPatientPair
{
        protected String PaientID ; 
        protected String donorID ; 
        public DonorsToPatientPair( String paient , String donor )
        {
           PaientID  = paient ; 
           donorID = donor  ; 
        }
        /**
         * @return the PaientID
         */
        public String getPaientID()
        {
            return PaientID;
        }

        /**
         * @return the donorID
         */
        public String getDonorID()
        {
            return donorID;
        }

    @Override
    public String toString()
    {
        return "DonorsToPatientPair{" + "PaientID=" + PaientID + ", donorID=" + donorID + '}';
    }
        
}
