/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;
//------------------------------------------------------------------------------

import java.util.Objects;

/**
 *
 * @author Nick Dalton
 */
public class NHSPatient
{
    protected String fullName;
    protected String NHSNorthPatientID1996;
    protected String address;
    protected int birthYear;
    protected boolean isAlive = true ; 

    /**
     * 
     * @param thefullName
     * @param theNHSPatientID2017
     * @param theaddress 
     */
    public  NHSPatient(String thefullName ,String theNHSPatientID2017 , 
            String theaddress )
    {
        setFullName( thefullName ); 
        setNHSNorthPatientID1996(  theNHSPatientID2017 ); 
        setAddress( theaddress ); 
    }
    /**
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    protected void setFullName(String fullName)
    {
        // decryped. 
        this.fullName = fullName;
    }

    /**
     * @return the NHSNorthPatientID1996
     */
    public String getNHSNorthPatientID1996()
    {
        return NHSNorthPatientID1996;
    }

    /**
     * @param NHSNorthPatientID1996 the NHSNorthPatientID1996 to set
     */
    public void setNHSNorthPatientID1996(String NHSNorthPatientID1996)
    {
        this.NHSNorthPatientID1996 = NHSNorthPatientID1996;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address the address to set
     */
    protected void setAddress(String address)
    {
        this.address = address;
    }
      /**
     * @return the birthYear
     */
    public int getBirthYear()
    {
        return birthYear;
    }

    /**
     * @param birthYear the birthYear to set
     */
    public void setBirthYear(int birthYear)
    {
        this.birthYear = birthYear;
    }
    /**
     * @return the isAlive
     */
    public boolean isIsAlive()
    {
        return isAlive;
    }

    /**
     * @param isAlive the isAlive to set
     */
    public void setIsAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }

    @Override
    public String toString()
    {
        return "" + fullName + "|" + NHSNorthPatientID1996 + "|" + address + 
                        "|" + birthYear + "|" + isIsAlive() ;
    }
    /**
     * 
     * @return int which is hashCode of this person 
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.fullName);
        hash = 97 * hash + Objects.hashCode(this.NHSNorthPatientID1996);
        return hash;
    }
    /**
     * Tests to see if this person is the same as another person 
     * USes only  ID  - people change names. 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final NHSPatient other = (NHSPatient) obj;
       
        if (!Objects.equals(this.NHSNorthPatientID1996, other.NHSNorthPatientID1996))
        {
            return false;
        }
        return true;
    }
    
}
